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
import com.juhai.business.domain.OrderCount;
import com.juhai.business.service.IOrderCountService;
import com.juhai.common.utils.poi.ExcelUtil;
import com.juhai.common.core.page.TableDataInfo;

/**
 * 每日订单量Controller
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
@RestController
@RequestMapping("/business/count")
public class OrderCountController extends BaseController
{
    @Autowired
    private IOrderCountService orderCountService;

    /**
     * 查询每日订单量列表
     */
    @PreAuthorize("@ss.hasPermi('business:count:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrderCount orderCount)
    {
        startPage();
        List<OrderCount> list = orderCountService.selectOrderCountList(orderCount);
        return getDataTable(list);
    }

    /**
     * 导出每日订单量列表
     */
    @PreAuthorize("@ss.hasPermi('business:count:export')")
    @Log(title = "每日订单量", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderCount orderCount)
    {
        List<OrderCount> list = orderCountService.selectOrderCountList(orderCount);
        ExcelUtil<OrderCount> util = new ExcelUtil<OrderCount>(OrderCount.class);
        util.exportExcel(response, list, "每日订单量数据");
    }

    /**
     * 获取每日订单量详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:count:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(orderCountService.selectOrderCountById(id));
    }

    /**
     * 新增每日订单量
     */
    @PreAuthorize("@ss.hasPermi('business:count:add')")
    @Log(title = "每日订单量", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderCount orderCount)
    {
        return toAjax(orderCountService.insertOrderCount(orderCount));
    }

    /**
     * 修改每日订单量
     */
    @PreAuthorize("@ss.hasPermi('business:count:edit')")
    @Log(title = "每日订单量", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderCount orderCount)
    {
        return toAjax(orderCountService.updateOrderCount(orderCount));
    }

    /**
     * 删除每日订单量
     */
    @PreAuthorize("@ss.hasPermi('business:count:remove')")
    @Log(title = "每日订单量", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(orderCountService.deleteOrderCountByIds(ids));
    }
}
