package com.juhai.web.controller.business;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.juhai.business.domain.DayReport;
import com.juhai.business.domain.Goods;
import com.juhai.business.domain.User;
import com.juhai.business.service.IDayReportService;
import com.juhai.business.service.IGoodsService;
import com.juhai.business.service.IUserService;
import com.juhai.common.annotation.Anonymous;
import com.juhai.common.annotation.Log;
import com.juhai.common.core.controller.BaseController;
import com.juhai.common.core.domain.AjaxResult;
import com.juhai.common.core.page.TableDataInfo;
import com.juhai.common.enums.BusinessType;
import com.juhai.common.utils.DateUtils;
import com.juhai.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 会员列表Controller
 * 
 * @author zhaotiezhu
 * @date 2023-06-12
 */
@RestController
@RequestMapping("/business/user")
public class UserController extends BaseController
{
    @Autowired
    private IUserService userService;

    @Autowired
    private IDayReportService dayReportService;

    @Autowired
    private IGoodsService goodsService;

    /**
     * 查询会员列表列表
     */
    @PreAuthorize("@ss.hasPermi('business:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(User user)
    {
        startPage();
        List<User> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    /**
     * 导出会员列表列表
     */
    @PreAuthorize("@ss.hasPermi('business:user:export')")
    @Log(title = "会员列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, User user)
    {
        List<User> list = userService.selectUserList(user);
        ExcelUtil<User> util = new ExcelUtil<User>(User.class);
        util.exportExcel(response, list, "会员列表数据");
    }

    /**
     * 获取会员列表详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:user:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(userService.selectUserById(id));
    }

    /**
     * 新增会员列表
     */
    @PreAuthorize("@ss.hasPermi('business:user:add')")
    @Log(title = "会员列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody User user)
    {
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改会员列表
     */
    @PreAuthorize("@ss.hasPermi('business:user:edit')")
    @Log(title = "会员列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody User user)
    {
        return toAjax(userService.updateUser(user));
    }

    /**
     * 删除会员列表
     */
    @PreAuthorize("@ss.hasPermi('business:user:remove')")
    @Log(title = "会员列表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userService.deleteUserByIds(ids));
    }


    /**
     * 今日总报表
     */
    @Anonymous
    @GetMapping("/allReport")
    public AjaxResult allReport()
    {
        Date today = new Date();
        Date todayBegin = DateUtil.beginOfDay(today);
        Date todayEnd = DateUtil.endOfDay(today);

        DateTime yesterday = DateUtil.offsetDay(today, -1);
        Date yesterdayBegin = DateUtil.beginOfDay(yesterday);
        Date yesterdayEnd = DateUtil.endOfDay(yesterday);

        JSONObject object = new JSONObject();
        List<Goods> goodsList = goodsService.list();
        long todayGoods = 0;
        long yesterdayGoods = 0;
        for (Goods goods : goodsList) {
            if (DateUtil.isIn(goods.getCreateTime(), todayBegin, todayEnd)) {
                todayGoods += 1;
            }
            if (DateUtil.isIn(goods.getCreateTime(), yesterdayBegin, yesterdayEnd)) {
                yesterdayGoods += 1;
            }
        }
        // 商品
        object.put("totalGoods", goodsList.size());
        object.put("todayGoods", todayGoods);
        object.put("yesterGoods", yesterdayGoods);
        // 用户总量
        long totalUsers = userService.count();
        object.put("totalUsers", totalUsers);
        List<User> users = userService.list(new LambdaQueryWrapper<User>().between(User::getRegisterTime, todayBegin, yesterdayEnd));
        long todayUsers = 0;
        long yesterdayUsers = 0;
        for (User user : users) {
            if (DateUtil.isIn(user.getRegisterTime(), todayBegin, todayEnd)) {
                todayUsers += 1;
            }
            if (DateUtil.isIn(user.getRegisterTime(), yesterdayBegin, yesterdayEnd)) {
                yesterdayUsers += 1;
            }
        }
        object.put("todayUsers", todayUsers);
        object.put("yesterdayUsers", yesterdayUsers);

        // 今日报表
        List<DayReport> todayReports = dayReportService.list(new LambdaQueryWrapper<DayReport>().eq(DayReport::getToday, DateUtil.formatDate(today)));
        BigDecimal todayOrderAmounts = new BigDecimal(0);
        BigDecimal todayDepositAmounts = new BigDecimal(0);
        BigDecimal todayWithdrawAmounts = new BigDecimal(0);
        BigDecimal todayCommissionAmounts = new BigDecimal(0);
        for (DayReport report : todayReports) {
            todayOrderAmounts = NumberUtil.add(todayOrderAmounts, report.getBet());
            todayDepositAmounts = NumberUtil.add(todayDepositAmounts, report.getDeposit());
            todayWithdrawAmounts = NumberUtil.add(todayWithdrawAmounts, report.getWithdraw());
            todayCommissionAmounts = NumberUtil.add(todayCommissionAmounts, report.getCommission());
        }

        // 昨日报表
        List<DayReport> yesterdayReports = dayReportService.list(new LambdaQueryWrapper<DayReport>().eq(DayReport::getToday, DateUtil.formatDate(yesterday)));
        BigDecimal yesterdayOrderAmounts = new BigDecimal(0);
        BigDecimal yesterdayDepositAmounts = new BigDecimal(0);
        BigDecimal yesterdayWithdrawAmounts = new BigDecimal(0);
        BigDecimal yesterdayCommissionAmounts = new BigDecimal(0);
        for (DayReport report : yesterdayReports) {
            yesterdayOrderAmounts = NumberUtil.add(yesterdayOrderAmounts, report.getBet());
            yesterdayDepositAmounts = NumberUtil.add(yesterdayDepositAmounts, report.getDeposit());
            yesterdayWithdrawAmounts = NumberUtil.add(yesterdayWithdrawAmounts, report.getWithdraw());
            yesterdayCommissionAmounts = NumberUtil.add(yesterdayCommissionAmounts, report.getCommission());
        }

        // 所有报表
        DayReport dayReport = dayReportService.getOne(new QueryWrapper<DayReport>().select("sum(deposit) as deposit,sum(withdraw) as withdraw,sum(bet) as bet,sum(commission) as commission"));

        // 订单总量
        object.put("totalOrders", 0);
        object.put("todayOrders", 0);
        object.put("yesterdayOrders", 0);
        // 订单总金额
        object.put("totalOrderAmounts", dayReport.getBet());
        object.put("todayOrderAmounts", todayOrderAmounts);
        object.put("yesterdayOrderAmounts", yesterdayOrderAmounts);
        // 用户充值
        object.put("totalDepositAmounts", dayReport.getDeposit());
        object.put("todayDepositAmounts", todayDepositAmounts);
        object.put("yesterdayDepositAmounts", yesterdayDepositAmounts);
        // 用户提现
        object.put("totalWithdrawAmounts", dayReport.getWithdraw());
        object.put("todayWithdrawAmounts", todayWithdrawAmounts);
        object.put("yesterdayWithdrawAmounts", yesterdayWithdrawAmounts);
        // 抢单佣金
        object.put("totalCommissionAmounts", dayReport.getCommission());
        object.put("todayCommissionAmounts", todayCommissionAmounts);
        object.put("yesterdayCommissionAmounts", yesterdayCommissionAmounts);
        // 用户总金额
        User totalUserBalance = userService.getOne(new QueryWrapper<User>().select("sum(balance) as balance"));
        object.put("totalUserBalance", totalUserBalance.getBalance());
        return AjaxResult.success(object);
    }
}
