package com.juhai.web.controller.business;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import com.juhai.business.domain.Area;
import com.juhai.business.service.IAreaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.juhai.common.annotation.Log;
import com.juhai.common.core.controller.BaseController;
import com.juhai.common.core.domain.AjaxResult;
import com.juhai.common.enums.BusinessType;
import com.juhai.business.domain.Goods;
import com.juhai.business.service.IGoodsService;
import com.juhai.common.utils.poi.ExcelUtil;
import com.juhai.common.core.page.TableDataInfo;

/**
 * 商品列表Controller
 * 
 * @author zhaotiezhu
 * @date 2023-06-12
 */
@RestController
@RequestMapping("/business/goods")
public class GoodsController extends BaseController
{
    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IAreaService areaService;

    /**
     * 查询商品列表列表
     */
    @PreAuthorize("@ss.hasPermi('business:goods:list')")
    @GetMapping("/list")
    public TableDataInfo list(Goods goods)
    {
        startPage();
        List<Goods> list = goodsService.selectGoodsList(goods);

        if (CollUtil.isNotEmpty(list)) {
            List<Area> areas = areaService.list();
            Map<Long, Area> areaMap = CollStreamUtil.toMap(areas, Area::getId, e -> e);

            for (Goods temp : list) {
                temp.setArea(areaMap.get(temp.getAreaId()));
            }
        }

        return getDataTable(list);
    }

    /**
     * 导出商品列表列表
     */
    @PreAuthorize("@ss.hasPermi('business:goods:export')")
    @Log(title = "商品列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Goods goods)
    {
        List<Goods> list = goodsService.selectGoodsList(goods);
        ExcelUtil<Goods> util = new ExcelUtil<Goods>(Goods.class);
        util.exportExcel(response, list, "商品列表数据");
    }

    /**
     * 获取商品列表详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:goods:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        Goods goods = goodsService.selectGoodsById(id);

        List<Area> areas = areaService.list();
        Map<Long, Area> areaMap = CollStreamUtil.toMap(areas, Area::getId, e -> e);

        goods.setArea(areaMap.get(goods.getAreaId()));

        return success(goods);
    }

    /**
     * 新增商品列表
     */
    @PreAuthorize("@ss.hasPermi('business:goods:add')")
    @Log(title = "商品列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Goods goods)
    {
        return toAjax(goodsService.insertGoods(goods));
    }

    /**
     * 修改商品列表
     */
    @PreAuthorize("@ss.hasPermi('business:goods:edit')")
    @Log(title = "商品列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Goods goods)
    {
        return toAjax(goodsService.updateGoods(goods));
    }

    /**
     * 删除商品列表
     */
    @PreAuthorize("@ss.hasPermi('business:goods:remove')")
    @Log(title = "商品列表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(goodsService.deleteGoodsByIds(ids));
    }
}
