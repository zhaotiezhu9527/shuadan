package com.juhai.web.controller.business;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.juhai.business.domain.Goods;
import com.juhai.business.domain.OrderCount;
import com.juhai.business.domain.Prepare;
import com.juhai.business.domain.User;
import com.juhai.business.service.IGoodsService;
import com.juhai.business.service.IOrderCountService;
import com.juhai.business.service.IPrepareService;
import com.juhai.business.service.IUserService;
import com.juhai.common.annotation.Log;
import com.juhai.common.core.controller.BaseController;
import com.juhai.common.core.domain.AjaxResult;
import com.juhai.common.core.page.TableDataInfo;
import com.juhai.common.enums.BusinessType;
import com.juhai.common.utils.poi.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 预派送列表Controller
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
@RestController
@RequestMapping("/business/prepare")
public class PrepareController extends BaseController
{
    @Autowired
    private IPrepareService prepareService;

    @Autowired
    private IOrderCountService orderCountService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IGoodsService goodsService;

    /**
     * 查询预派送列表列表
     */
    @PreAuthorize("@ss.hasPermi('business:prepare:list')")
    @GetMapping("/list")
    public TableDataInfo list(Prepare prepare)
    {
        startPage();
        List<Prepare> list = prepareService.selectPrepareList(prepare);
        if (CollUtil.isNotEmpty(list)) {
            List<String> users = list.stream().map(Prepare::getUserName).collect(Collectors.toList());
            List<String> preBatchs = list.stream().map(Prepare::getPreBatch).collect(Collectors.toList());

            // 查询今日完成单数
            Date now = new Date();
            List<OrderCount> orderCounts = orderCountService.list(
                    new LambdaQueryWrapper<OrderCount>()
                            .in(OrderCount::getUserName, users)
                            .eq(OrderCount::getToday, DateUtil.formatDate(now))
            );
            Map<String, OrderCount> orderCountMap = orderCounts.stream().collect(Collectors.toMap(OrderCount::getUserName, e -> e));

            // 查询商品信息
            List<Goods> goodsList = goodsService.list();
            Map<Long, Goods> goodsMap = goodsList.stream().collect(Collectors.toMap(Goods::getId, e -> e));

            // 查询批次信息
            List<Prepare> preBatchList = prepareService.list(new LambdaQueryWrapper<Prepare>().in(Prepare::getPreBatch, preBatchs));
            Map<String, BigDecimal> orderAmountMap = new HashMap<>();
            for (Prepare temp : preBatchList) {
                BigDecimal bigDecimal = orderAmountMap.getOrDefault(temp.getPreBatch(), new BigDecimal(0));
                orderAmountMap.put(temp.getPreBatch(), NumberUtil.add(bigDecimal, temp.getOrderAmount()));
            }


            for (Prepare temp : list) {
                temp.setGoods(goodsMap.get(temp.getGoodsId()));
                OrderCount orderCount = orderCountMap.get(temp.getUserName());
                temp.setFinishCount(orderCount == null ? 0 : orderCount.getOrderCount());
                temp.setBatchOrderAmount(orderAmountMap.get(temp.getPreBatch()));
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出预派送列表列表
     */
    @PreAuthorize("@ss.hasPermi('business:prepare:export')")
    @Log(title = "预派送列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Prepare prepare)
    {
        List<Prepare> list = prepareService.selectPrepareList(prepare);
        ExcelUtil<Prepare> util = new ExcelUtil<Prepare>(Prepare.class);
        util.exportExcel(response, list, "预派送列表数据");
    }

    /**
     * 获取预派送列表详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:prepare:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        Prepare prepare = prepareService.selectPrepareById(id);
        // 查询今日完成单数
        Date now = new Date();
        OrderCount orderCount = orderCountService.getOne(
                new LambdaQueryWrapper<OrderCount>()
                        .in(OrderCount::getUserName, prepare.getUserName())
                        .eq(OrderCount::getToday, now)
        );
        prepare.setFinishCount(orderCount == null ? 0 : orderCount.getOrderCount());

        prepare.setGoods(goodsService.getById(prepare.getGoodsId()));
        return success(prepare);
    }

    /**
     * 新增预派送列表
     */
    @Transactional
    @PreAuthorize("@ss.hasPermi('business:prepare:add')")
    @Log(title = "预派送列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Prepare prepare)
    {
        String batch = IdUtil.fastSimpleUUID();

        User user = userService.getUserByName(prepare.getUserName());
        if (user == null) {
            return AjaxResult.error("用户名不存在");
        }

        List<Prepare> prepares = new ArrayList<>();

        String goodsSelect = prepare.getGoodsSelect();
        String[] goodsArr = goodsSelect.split(",");

        // 获取商品
        List<Goods> goodsList = goodsService.list();
        Map<Long, Goods> goodsMap = goodsList.stream().collect(Collectors.toMap(Goods::getId, e -> e));

        BigDecimal orderAmount = new BigDecimal(0);
        for (int i = 0; i < goodsArr.length; i++) {
            String goodsStr = goodsArr[i];
            Long goodsId = NumberUtils.toLong(goodsStr.split(":")[0]);
            Long goodsCount = NumberUtils.toLong(goodsStr.split(":")[1]);
            Goods goods = goodsMap.get(goodsId);
            if (goods == null) {
                continue;
            }

            long triggerNum = prepare.getTriggerNum() + i;
            Prepare samePrepare = prepareService.getOne(
                    new LambdaQueryWrapper<Prepare>()
                            .eq(Prepare::getUserName, prepare.getUserName())
                            .eq(Prepare::getStatus, 0)
                            .eq(Prepare::getTriggerNum, triggerNum)
            );
            if (samePrepare != null) {
                return AjaxResult.error("用户第[" + triggerNum + "]单还未完成,不要重复派送");
            }

            Prepare p = new Prepare();
            p.setUserName(prepare.getUserName());
            p.setTriggerNum(triggerNum);
            p.setGoodsId(goodsId);
            p.setGoodsCount(goodsCount);
            p.setPreBatch(batch);
            p.setPromptText(StringUtils.isBlank(prepare.getPromptText()) ? "" : prepare.getPromptText());
            p.setCommissionMul(prepare.getCommissionMul());
            p.setOrderAmount(NumberUtil.mul(goods.getGoodsPrice(), goodsCount));
            p.setStatus(0L);
            p.setCreateBy(getUsername());
            p.setCreateTime(new Date());
            p.setUpdateBy(getUsername());
            p.setUpdateTime(new Date());
            p.setRemark(null);
            prepares.add(p);

            orderAmount = NumberUtil.add(orderAmount, NumberUtil.mul(goodsCount, goods.getGoodsPrice()));
        }
        prepareService.saveBatch(prepares);
        return success();
    }

    /**
     * 修改预派送列表
     */
    @PreAuthorize("@ss.hasPermi('business:prepare:edit')")
    @Log(title = "预派送列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Prepare prepare)
    {

        Goods goods = goodsService.getById(prepare.getGoodsId());

        prepare.setOrderAmount(NumberUtil.mul(goods.getGoodsPrice(), prepare.getGoodsCount()));

        return toAjax(prepareService.updatePrepare(prepare));
    }

    /**
     * 删除预派送列表
     */
    @Transactional
    @PreAuthorize("@ss.hasPermi('business:prepare:remove')")
    @Log(title = "预派送列表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {

        return toAjax(prepareService.deletePrepareByIds(ids));
    }


    @PreAuthorize("@ss.hasPermi('business:prepare:query')")
    @GetMapping(value = "/getNewOne/{userName}")
    public AjaxResult getNewOne(@PathVariable("userName") String userName)
    {

        OrderCount one = orderCountService.getOne(
                new LambdaQueryWrapper<OrderCount>()
                        .eq(OrderCount::getUserName, userName)
                        .eq(OrderCount::getToday, DateUtil.formatDate(new Date()))
        );
        return success(one == null ? 0 : one.getOrderCount());
    }
}
