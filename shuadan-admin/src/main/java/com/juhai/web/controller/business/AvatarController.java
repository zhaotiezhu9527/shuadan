package com.juhai.web.controller.business;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.xuyanwu.spring.file.storage.FileStorageService;
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
import com.juhai.business.domain.Avatar;
import com.juhai.business.service.IAvatarService;
import com.juhai.common.utils.poi.ExcelUtil;
import com.juhai.common.core.page.TableDataInfo;

/**
 * 头像列表Controller
 * 
 * @author zhaotiezhu
 * @date 2023-11-09
 */
@RestController
@RequestMapping("/business/avatar")
public class AvatarController extends BaseController
{
    @Autowired
    private IAvatarService avatarService;

    @Autowired
    private FileStorageService fileStorageService;//注入实列

    /**
     * 查询头像列表列表
     */
    @PreAuthorize("@ss.hasPermi('business:avatar:list')")
    @GetMapping("/list")
    public TableDataInfo list(Avatar avatar)
    {
        startPage();
        List<Avatar> list = avatarService.selectAvatarList(avatar);
        return getDataTable(list);
    }

    /**
     * 导出头像列表列表
     */
    @PreAuthorize("@ss.hasPermi('business:avatar:export')")
    @Log(title = "头像列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Avatar avatar)
    {
        List<Avatar> list = avatarService.selectAvatarList(avatar);
        ExcelUtil<Avatar> util = new ExcelUtil<Avatar>(Avatar.class);
        util.exportExcel(response, list, "头像列表数据");
    }

    /**
     * 获取头像列表详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:avatar:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(avatarService.selectAvatarById(id));
    }

    /**
     * 新增头像列表
     */
    @PreAuthorize("@ss.hasPermi('business:avatar:add')")
    @Log(title = "头像列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Avatar avatar)
    {

        return toAjax(avatarService.insertAvatar(avatar));
    }

    /**
     * 修改头像列表
     */
    @PreAuthorize("@ss.hasPermi('business:avatar:edit')")
    @Log(title = "头像列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Avatar avatar)
    {
        return toAjax(avatarService.updateAvatar(avatar));
    }

    /**
     * 删除头像列表
     */
    @PreAuthorize("@ss.hasPermi('business:avatar:remove')")
    @Log(title = "头像列表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(avatarService.deleteAvatarByIds(ids));
    }
}
