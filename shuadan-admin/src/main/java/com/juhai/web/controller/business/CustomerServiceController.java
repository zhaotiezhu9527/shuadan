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
import com.juhai.business.domain.CustomerService;
import com.juhai.business.service.ICustomerServiceService;
import com.juhai.common.utils.poi.ExcelUtil;
import com.juhai.common.core.page.TableDataInfo;

/**
 * 客服列表Controller
 * 
 * @author zhaotiezhu
 * @date 2023-06-12
 */
@RestController
@RequestMapping("/business/service")
public class CustomerServiceController extends BaseController
{
    @Autowired
    private ICustomerServiceService customerServiceService;

    /**
     * 查询客服列表列表
     */
    @PreAuthorize("@ss.hasPermi('business:service:list')")
    @GetMapping("/list")
    public TableDataInfo list(CustomerService customerService)
    {
        startPage();
        List<CustomerService> list = customerServiceService.selectCustomerServiceList(customerService);
        return getDataTable(list);
    }

    /**
     * 导出客服列表列表
     */
    @PreAuthorize("@ss.hasPermi('business:service:export')")
    @Log(title = "客服列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CustomerService customerService)
    {
        List<CustomerService> list = customerServiceService.selectCustomerServiceList(customerService);
        ExcelUtil<CustomerService> util = new ExcelUtil<CustomerService>(CustomerService.class);
        util.exportExcel(response, list, "客服列表数据");
    }

    /**
     * 获取客服列表详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:service:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(customerServiceService.selectCustomerServiceById(id));
    }

    /**
     * 新增客服列表
     */
    @PreAuthorize("@ss.hasPermi('business:service:add')")
    @Log(title = "客服列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CustomerService customerService)
    {
        return toAjax(customerServiceService.insertCustomerService(customerService));
    }

    /**
     * 修改客服列表
     */
    @PreAuthorize("@ss.hasPermi('business:service:edit')")
    @Log(title = "客服列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CustomerService customerService)
    {
        return toAjax(customerServiceService.updateCustomerService(customerService));
    }

    /**
     * 删除客服列表
     */
    @PreAuthorize("@ss.hasPermi('business:service:remove')")
    @Log(title = "客服列表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(customerServiceService.deleteCustomerServiceByIds(ids));
    }
}
