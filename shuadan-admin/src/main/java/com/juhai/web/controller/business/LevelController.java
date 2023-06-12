package com.juhai.web.controller.business;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.juhai.business.domain.Level;
import com.juhai.business.service.ILevelService;
import com.juhai.common.utils.poi.ExcelUtil;
import com.juhai.common.core.page.TableDataInfo;

/**
 * 等级列表Controller
 * 
 * @author zhaotiezhu
 * @date 2023-06-12
 */
@RestController
@RequestMapping("/business/level")
public class LevelController extends BaseController
{
    @Autowired
    private ILevelService levelService;

    /**
     * 查询等级列表列表
     */
    @PreAuthorize("@ss.hasPermi('business:level:list')")
    @GetMapping("/list")
    public TableDataInfo list(Level level)
    {
        startPage();
        List<Level> list = levelService.selectLevelList(level);
        return getDataTable(list);
    }

    /**
     * 导出等级列表列表
     */
    @PreAuthorize("@ss.hasPermi('business:level:export')")
    @Log(title = "等级列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Level level)
    {
        List<Level> list = levelService.selectLevelList(level);
        ExcelUtil<Level> util = new ExcelUtil<Level>(Level.class);
        util.exportExcel(response, list, "等级列表数据");
    }

    /**
     * 获取等级列表详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:level:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(levelService.selectLevelById(id));
    }

    /**
     * 新增等级列表
     */
    @PreAuthorize("@ss.hasPermi('business:level:add')")
    @Log(title = "等级列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Level level)
    {
        return toAjax(levelService.insertLevel(level));
    }

    /**
     * 修改等级列表
     */
    @PreAuthorize("@ss.hasPermi('business:level:edit')")
    @Log(title = "等级列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Level level)
    {
        return toAjax(levelService.updateLevel(level));
    }

    /**
     * 删除等级列表
     */
    @PreAuthorize("@ss.hasPermi('business:level:remove')")
    @Log(title = "等级列表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(levelService.deleteLevelByIds(ids));
    }
}
