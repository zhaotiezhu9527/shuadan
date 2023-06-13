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
import com.juhai.business.domain.PrepareDetail;
import com.juhai.business.service.IPrepareDetailService;
import com.juhai.common.utils.poi.ExcelUtil;
import com.juhai.common.core.page.TableDataInfo;

/**
 * 预派送详情列表Controller
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
@RestController
@RequestMapping("/business/detail")
public class PrepareDetailController extends BaseController
{
    @Autowired
    private IPrepareDetailService prepareDetailService;

    /**
     * 查询预派送详情列表列表
     */
    @PreAuthorize("@ss.hasPermi('business:detail:list')")
    @GetMapping("/list")
    public TableDataInfo list(PrepareDetail prepareDetail)
    {
        startPage();
        List<PrepareDetail> list = prepareDetailService.selectPrepareDetailList(prepareDetail);
        return getDataTable(list);
    }

    /**
     * 导出预派送详情列表列表
     */
    @PreAuthorize("@ss.hasPermi('business:detail:export')")
    @Log(title = "预派送详情列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PrepareDetail prepareDetail)
    {
        List<PrepareDetail> list = prepareDetailService.selectPrepareDetailList(prepareDetail);
        ExcelUtil<PrepareDetail> util = new ExcelUtil<PrepareDetail>(PrepareDetail.class);
        util.exportExcel(response, list, "预派送详情列表数据");
    }

    /**
     * 获取预派送详情列表详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:detail:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(prepareDetailService.selectPrepareDetailById(id));
    }

    /**
     * 新增预派送详情列表
     */
    @PreAuthorize("@ss.hasPermi('business:detail:add')")
    @Log(title = "预派送详情列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PrepareDetail prepareDetail)
    {
        return toAjax(prepareDetailService.insertPrepareDetail(prepareDetail));
    }

    /**
     * 修改预派送详情列表
     */
    @PreAuthorize("@ss.hasPermi('business:detail:edit')")
    @Log(title = "预派送详情列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PrepareDetail prepareDetail)
    {
        return toAjax(prepareDetailService.updatePrepareDetail(prepareDetail));
    }

    /**
     * 删除预派送详情列表
     */
    @PreAuthorize("@ss.hasPermi('business:detail:remove')")
    @Log(title = "预派送详情列表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(prepareDetailService.deletePrepareDetailByIds(ids));
    }
}
