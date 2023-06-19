package com.juhai.web.controller.business;

import java.util.Date;
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
import com.juhai.business.domain.Notice;
import com.juhai.business.service.INoticeService;
import com.juhai.common.utils.poi.ExcelUtil;
import com.juhai.common.core.page.TableDataInfo;

/**
 * 公告管理Controller
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
@RestController
@RequestMapping("/business/notice")
public class NoticeController extends BaseController
{
    @Autowired
    private INoticeService noticeService;

    /**
     * 查询公告管理列表
     */
    @PreAuthorize("@ss.hasPermi('business:notice:list')")
    @GetMapping("/list")
    public TableDataInfo list(Notice notice)
    {
        startPage();
        List<Notice> list = noticeService.selectNoticeList(notice);
        return getDataTable(list);
    }

    /**
     * 导出公告管理列表
     */
    @PreAuthorize("@ss.hasPermi('business:notice:export')")
    @Log(title = "公告管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Notice notice)
    {
        List<Notice> list = noticeService.selectNoticeList(notice);
        ExcelUtil<Notice> util = new ExcelUtil<Notice>(Notice.class);
        util.exportExcel(response, list, "公告管理数据");
    }

    /**
     * 获取公告管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:notice:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(noticeService.selectNoticeById(id));
    }

    /**
     * 新增公告管理
     */
    @PreAuthorize("@ss.hasPermi('business:notice:add')")
    @Log(title = "公告管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Notice notice)
    {
        notice.setCreateBy(getUsername());
        notice.setCreateTime(new Date());
        return toAjax(noticeService.insertNotice(notice));
    }

    /**
     * 修改公告管理
     */
    @PreAuthorize("@ss.hasPermi('business:notice:edit')")
    @Log(title = "公告管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Notice notice)
    {
        notice.setUpdateBy(getUsername());
        notice.setUpdateTime(new Date());
        return toAjax(noticeService.updateNotice(notice));
    }

    /**
     * 删除公告管理
     */
    @PreAuthorize("@ss.hasPermi('business:notice:remove')")
    @Log(title = "公告管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(noticeService.deleteNoticeByIds(ids));
    }
}
