package com.juhai.web.controller.business;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.juhai.business.domain.OrderCount;
import com.juhai.business.domain.Prepare;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
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
        List<String> users = list.stream().map(Prepare::getUserName).collect(Collectors.toList());

        // 查询今日完成单数
        Date now = new Date();
        List<OrderCount> orderCounts = orderCountService.list(
                new LambdaQueryWrapper<OrderCount>()
                        .in(OrderCount::getUserName, users)
                        .eq(OrderCount::getToday, now)
        );
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
}
