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
import com.juhai.business.domain.MessageText;
import com.juhai.business.service.IMessageTextService;
import com.juhai.common.utils.poi.ExcelUtil;
import com.juhai.common.core.page.TableDataInfo;

/**
 * 文本管理Controller
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
@RestController
@RequestMapping("/business/text")
public class MessageTextController extends BaseController
{
    @Autowired
    private IMessageTextService messageTextService;

    /**
     * 查询文本管理列表
     */
    @PreAuthorize("@ss.hasPermi('business:text:list')")
    @GetMapping("/list")
    public TableDataInfo list(MessageText messageText)
    {
        startPage();
        List<MessageText> list = messageTextService.selectMessageTextList(messageText);
        return getDataTable(list);
    }

    /**
     * 导出文本管理列表
     */
    @PreAuthorize("@ss.hasPermi('business:text:export')")
    @Log(title = "文本管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MessageText messageText)
    {
        List<MessageText> list = messageTextService.selectMessageTextList(messageText);
        ExcelUtil<MessageText> util = new ExcelUtil<MessageText>(MessageText.class);
        util.exportExcel(response, list, "文本管理数据");
    }

    /**
     * 获取文本管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:text:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(messageTextService.selectMessageTextById(id));
    }

    /**
     * 新增文本管理
     */
    @PreAuthorize("@ss.hasPermi('business:text:add')")
    @Log(title = "文本管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MessageText messageText)
    {
        return toAjax(messageTextService.insertMessageText(messageText));
    }

    /**
     * 修改文本管理
     */
    @PreAuthorize("@ss.hasPermi('business:text:edit')")
    @Log(title = "文本管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MessageText messageText)
    {
        messageText.setUpdateTime(new Date());
        return toAjax(messageTextService.updateMessageText(messageText));
    }

    /**
     * 删除文本管理
     */
    @PreAuthorize("@ss.hasPermi('business:text:remove')")
    @Log(title = "文本管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(messageTextService.deleteMessageTextByIds(ids));
    }
}
