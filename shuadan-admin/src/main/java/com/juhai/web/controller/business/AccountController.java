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
import com.juhai.business.domain.Account;
import com.juhai.business.service.IAccountService;
import com.juhai.common.utils.poi.ExcelUtil;
import com.juhai.common.core.page.TableDataInfo;

/**
 * 账变列表Controller
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
@RestController
@RequestMapping("/business/account")
public class AccountController extends BaseController
{
    @Autowired
    private IAccountService accountService;

    /**
     * 查询账变列表列表
     */
    @PreAuthorize("@ss.hasPermi('business:account:list')")
    @GetMapping("/list")
    public TableDataInfo list(Account account)
    {
        startPage();
        List<Account> list = accountService.selectAccountList(account);
        return getDataTable(list);
    }

    /**
     * 导出账变列表列表
     */
    @PreAuthorize("@ss.hasPermi('business:account:export')")
    @Log(title = "账变列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Account account)
    {
        List<Account> list = accountService.selectAccountList(account);
        ExcelUtil<Account> util = new ExcelUtil<Account>(Account.class);
        util.exportExcel(response, list, "账变列表数据");
    }

    /**
     * 获取账变列表详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:account:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(accountService.selectAccountById(id));
    }

    /**
     * 新增账变列表
     */
    @PreAuthorize("@ss.hasPermi('business:account:add')")
    @Log(title = "账变列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Account account)
    {
        return toAjax(accountService.insertAccount(account));
    }

    /**
     * 修改账变列表
     */
    @PreAuthorize("@ss.hasPermi('business:account:edit')")
    @Log(title = "账变列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Account account)
    {
        return toAjax(accountService.updateAccount(account));
    }

    /**
     * 删除账变列表
     */
    @PreAuthorize("@ss.hasPermi('business:account:remove')")
    @Log(title = "账变列表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(accountService.deleteAccountByIds(ids));
    }
}
