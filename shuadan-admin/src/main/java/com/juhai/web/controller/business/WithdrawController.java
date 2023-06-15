package com.juhai.web.controller.business;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.juhai.business.domain.Account;
import com.juhai.business.domain.DayReport;
import com.juhai.business.domain.User;
import com.juhai.business.service.IAccountService;
import com.juhai.business.service.IDayReportService;
import com.juhai.business.service.IUserService;
import com.juhai.web.controller.business.request.WithdrawCheckRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
import com.juhai.business.domain.Withdraw;
import com.juhai.business.service.IWithdrawService;
import com.juhai.common.utils.poi.ExcelUtil;
import com.juhai.common.core.page.TableDataInfo;

/**
 * 提现列表Controller
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
@RestController
@RequestMapping("/business/withdraw")
public class WithdrawController extends BaseController
{
    @Autowired
    private IWithdrawService withdrawService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IDayReportService dayReportService;

    @Autowired
    private IAccountService accountService;

    /**
     * 查询提现列表列表
     */
    @PreAuthorize("@ss.hasPermi('business:withdraw:list')")
    @GetMapping("/list")
    public TableDataInfo list(Withdraw withdraw)
    {
        startPage();
        List<Withdraw> list = withdrawService.selectWithdrawList(withdraw);
        return getDataTable(list);
    }

    /**
     * 导出提现列表列表
     */
    @PreAuthorize("@ss.hasPermi('business:withdraw:export')")
    @Log(title = "提现列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Withdraw withdraw)
    {
        List<Withdraw> list = withdrawService.selectWithdrawList(withdraw);
        ExcelUtil<Withdraw> util = new ExcelUtil<Withdraw>(Withdraw.class);
        util.exportExcel(response, list, "提现列表数据");
    }

    /**
     * 获取提现列表详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:withdraw:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(withdrawService.selectWithdrawById(id));
    }

    /**
     * 新增提现列表
     */
    @PreAuthorize("@ss.hasPermi('business:withdraw:add')")
    @Log(title = "提现列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Withdraw withdraw)
    {
        return toAjax(withdrawService.insertWithdraw(withdraw));
    }

    /**
     * 修改提现列表
     */
    @PreAuthorize("@ss.hasPermi('business:withdraw:edit')")
    @Log(title = "提现列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Withdraw withdraw)
    {
        return toAjax(withdrawService.updateWithdraw(withdraw));
    }

    /**
     * 删除提现列表
     */
    @PreAuthorize("@ss.hasPermi('business:withdraw:remove')")
    @Log(title = "提现列表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(withdrawService.deleteWithdrawByIds(ids));
    }

    @Transactional
    @PreAuthorize("@ss.hasPermi('business:withdraw:check')")
    @Log(title = "提现审核", businessType = BusinessType.UPDATE)
    @PostMapping("/check")
    public AjaxResult check(@RequestBody WithdrawCheckRequest request) throws Exception {

        Withdraw withdraw = withdrawService.selectWithdrawById(NumberUtils.toLong(request.getId()));
        if (withdraw.getStatus().intValue() != 0) {
            return AjaxResult.error("该订单已被审核.");
        }
        Date now = new Date();
        User user = userService.getUserByName(withdraw.getUserName());
        if (StringUtils.equals(request.getStatus(), "1")) {
            // 通过
            Withdraw temp = new Withdraw();
            temp.setId(withdraw.getId());
            temp.setStatus(1L);
            temp.setRemark(request.getRemark());
            temp.setUpdateBy(getUsername());
            temp.setCheckTime(now);
            withdrawService.updateWithdraw(temp);

            DayReport dayReport = new DayReport();
            dayReport.setUserName(user.getUserName());
            dayReport.setToday(DateUtil.formatDate(now));
            dayReport.setWithdraw(withdraw.getOptAmount());
            dayReport.setUserAgent(user.getUserAgent());
            dayReport.setUserAgentNode(user.getUserAgentNode());
            dayReport.setUserAgentLevel(user.getUserAgentLevel());
            dayReport.setCreateTime(now);
            dayReport.setUpdateTime(now);
            dayReportService.insertOrUpdate(dayReport);

            userService.update(
                    new UpdateWrapper<User>()
                            .setSql("withdraw = withdraw + " + withdraw.getOptAmount())
                            .eq("user_name", withdraw.getUserName())
            );
        } else {
            // 拒绝
            Withdraw temp = new Withdraw();
            temp.setId(withdraw.getId());
            temp.setStatus(2L);
            temp.setRemark(request.getRemark());
            temp.setUpdateBy(getUsername());
            temp.setCheckTime(now);
            withdrawService.updateWithdraw(temp);

            // 返还金额
            userService.updateUserBalance(withdraw.getUserName(), withdraw.getOptAmount());

            // 记录流水
            Account account = new Account();
            account.setUserName(withdraw.getUserName());
            account.setOptAmount(withdraw.getOptAmount());
            account.setType(1L);
            account.setOptType(1L);
            account.setOptTime(now);
            account.setUserAgent(user.getUserAgent());
            account.setRefNo(withdraw.getOrderNo());
            account.setAccountNo(IdUtil.getSnowflakeNextIdStr());
            account.setRemark("提现退还金额:" + withdraw.getOptAmount() + "元");
            accountService.insertAccount(account);
        }
        return toAjax(true);
    }
}
