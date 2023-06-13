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
import com.juhai.business.domain.Deposit;
import com.juhai.business.service.IDepositService;
import com.juhai.common.utils.poi.ExcelUtil;
import com.juhai.common.core.page.TableDataInfo;

/**
 * 充值列表Controller
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
@RestController
@RequestMapping("/business/deposit")
public class DepositController extends BaseController
{
    @Autowired
    private IDepositService depositService;

    /**
     * 查询充值列表列表
     */
    @PreAuthorize("@ss.hasPermi('business:deposit:list')")
    @GetMapping("/list")
    public TableDataInfo list(Deposit deposit)
    {
        startPage();
        List<Deposit> list = depositService.selectDepositList(deposit);
        return getDataTable(list);
    }

    /**
     * 导出充值列表列表
     */
    @PreAuthorize("@ss.hasPermi('business:deposit:export')")
    @Log(title = "充值列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Deposit deposit)
    {
        List<Deposit> list = depositService.selectDepositList(deposit);
        ExcelUtil<Deposit> util = new ExcelUtil<Deposit>(Deposit.class);
        util.exportExcel(response, list, "充值列表数据");
    }

    /**
     * 获取充值列表详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:deposit:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(depositService.selectDepositById(id));
    }

    /**
     * 新增充值列表
     */
    @PreAuthorize("@ss.hasPermi('business:deposit:add')")
    @Log(title = "充值列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Deposit deposit)
    {
        return toAjax(depositService.insertDeposit(deposit));
    }

    /**
     * 修改充值列表
     */
    @PreAuthorize("@ss.hasPermi('business:deposit:edit')")
    @Log(title = "充值列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Deposit deposit)
    {
        return toAjax(depositService.updateDeposit(deposit));
    }

    /**
     * 删除充值列表
     */
    @PreAuthorize("@ss.hasPermi('business:deposit:remove')")
    @Log(title = "充值列表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(depositService.deleteDepositByIds(ids));
    }
}
