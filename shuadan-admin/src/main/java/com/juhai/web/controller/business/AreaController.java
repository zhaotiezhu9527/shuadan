package com.juhai.web.controller.business;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.collection.CollStreamUtil;
import com.juhai.business.domain.Goods;
import com.juhai.business.domain.Level;
import com.juhai.business.service.ILevelService;
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
import com.juhai.business.domain.Area;
import com.juhai.business.service.IAreaService;
import com.juhai.common.utils.poi.ExcelUtil;
import com.juhai.common.core.page.TableDataInfo;

/**
 * 商品分类Controller
 * 
 * @author zhaotiezhu
 * @date 2023-06-12
 */
@RestController
@RequestMapping("/business/area")
public class AreaController extends BaseController
{
    @Autowired
    private IAreaService areaService;

    @Autowired
    private ILevelService levelService;

    /**
     * 查询商品分类列表
     */
    @PreAuthorize("@ss.hasPermi('business:area:list')")
    @GetMapping("/list")
    public TableDataInfo list(Area area)
    {
        startPage();
        List<Area> list = areaService.selectAreaList(area);

        List<Level> levels = levelService.list();
        Map<Long, Level> levelMap = CollStreamUtil.toMap(levels, Level::getId, e -> e);

        for (Area temp : list) {
            temp.setLevel(levelMap.get(temp.getLevelId()));
        }

        return getDataTable(list);
    }

    /**
     * 导出商品分类列表
     */
    @PreAuthorize("@ss.hasPermi('business:area:export')")
    @Log(title = "商品分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Area area)
    {
        List<Area> list = areaService.selectAreaList(area);
        ExcelUtil<Area> util = new ExcelUtil<Area>(Area.class);
        util.exportExcel(response, list, "商品分类数据");
    }

    /**
     * 获取商品分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:area:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        Area area = areaService.selectAreaById(id);

        List<Level> levels = levelService.list();
        Map<Long, Level> levelMap = CollStreamUtil.toMap(levels, Level::getId, e -> e);

        area.setLevel(levelMap.get(area.getLevelId()));

        return success(area);
    }

    /**
     * 新增商品分类
     */
    @PreAuthorize("@ss.hasPermi('business:area:add')")
    @Log(title = "商品分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Area area)
    {
        return toAjax(areaService.insertArea(area));
    }

    /**
     * 修改商品分类
     */
    @PreAuthorize("@ss.hasPermi('business:area:edit')")
    @Log(title = "商品分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Area area)
    {
        return toAjax(areaService.updateArea(area));
    }

    /**
     * 删除商品分类
     */
    @PreAuthorize("@ss.hasPermi('business:area:remove')")
    @Log(title = "商品分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(areaService.deleteAreaByIds(ids));
    }
}
