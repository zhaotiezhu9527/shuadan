package com.juhai.web.controller.business;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.juhai.business.domain.*;
import com.juhai.business.service.*;
import com.juhai.common.annotation.Anonymous;
import com.juhai.common.annotation.Log;
import com.juhai.common.core.controller.BaseController;
import com.juhai.common.core.domain.AjaxResult;
import com.juhai.common.core.page.TableDataInfo;
import com.juhai.common.enums.BusinessType;
import com.juhai.common.utils.StringUtils;
import com.juhai.common.utils.poi.ExcelUtil;
import com.juhai.web.controller.business.request.OptUserMoneyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private IParamterService paramterService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IDepositService depositService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private IOrderCountService orderCountService;

    @Autowired
    private IPrepareService prepareService;

    /**
     * 查询会员列表列表
     */
    @PreAuthorize("@ss.hasPermi('business:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(User user)
    {
        if (user.getOnline() == 1) {
            // 获取所有在线用户key
            Set<String> keys = redisTemplate.keys("user:online:token:*");
            // 没有在线用户 直接返回
            if (CollUtil.isEmpty(keys)) {
                return getDataTable(new ArrayList<>());
            }

            List<String> onlineNames = new ArrayList<>();
            if (CollUtil.isNotEmpty(keys)) {
                for (String key : keys) {
                    onlineNames.add(key.split(":")[3]);
                }
            }
            user.getParams().put("onlineNames", onlineNames);
        }

        startPage();
        List<User> list = userService.selectUserList(user);
        if (CollUtil.isNotEmpty(list)) {
            List<String> userNames = list.stream().map(User::getUserName).collect(Collectors.toList());
            Set<String> userAgentNames = list.stream().map(User::getUserAgent).collect(Collectors.toSet());

            Date date = new Date();
            // 查询今日订单单数信息
            List<OrderCount> ordercounts = orderCountService.list(
                    new LambdaQueryWrapper<OrderCount>()
                            .eq(OrderCount::getToday, DateUtil.formatDate(date))
                            .in(OrderCount::getUserName, userNames)
            );
            Map<String, Long> ordercountMap = ordercounts.stream().collect(Collectors.toMap(OrderCount::getUserName, OrderCount::getOrderCount));
            // 查询预派送信息
            List<Prepare> prepares = prepareService.list(
                    new LambdaQueryWrapper<Prepare>()
                            .eq(Prepare::getStatus, 0)
                            .in(Prepare::getUserName, userNames)
                            .orderByAsc(Prepare::getTriggerNum)
            );
            Map<String, Long> prepareMap = new HashMap<>();
            for (Prepare prepare : prepares) {
                if (prepareMap.containsKey(prepare.getUserName())) {
                    continue;
                }
                prepareMap.put(prepare.getUserName(), prepare.getTriggerNum());
            }
            // 上级用户姓名
            List<User> agentList = userService.list(new LambdaQueryWrapper<User>().in(User::getUserName, userAgentNames));
            Map<String, User> agentRealName = agentList.stream().collect(Collectors.toMap(User::getUserName, user1 -> user1));

            for (User temp : list) {
                temp.setOrderCount(ordercountMap.getOrDefault(temp.getUserName(), 0L));
                temp.setPrepareCount(prepareMap.getOrDefault(temp.getUserName(), 0L));
                User agent = agentRealName.get(temp.getUserAgent());
                temp.setUserAgentName(agent == null ? "" : agent.getRealName());
            }
        }

        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('business:user:list')")
    @GetMapping("/node")
    public TableDataInfo node(User user)
    {
        User user1 = userService.getById(user.getId());
        user.setUserAgentLevel(user1.getUserAgentLevel() + user.getUserAgentLevel());
        user.setUserAgentNode("|" + user1.getUserName() + "|");
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
        // 校验用户名 密码 支付密码
        boolean matchUserName = ReUtil.isMatch("^[a-zA-Z0-9]{4,12}$", user.getUserName());
        if (!matchUserName) {
            return AjaxResult.error("请输入4-12位用户名");
        }
        boolean matchLoginPwd = ReUtil.isMatch("^[a-zA-Z0-9]{6,12}$", user.getLoginPwd());
        if (!matchLoginPwd) {
            return AjaxResult.error("请输入6-12位登录密码");
        }
        boolean matchPayPwd = ReUtil.isMatch("^\\d{6}$", user.getPayPwd());
        if (!matchPayPwd) {
            return AjaxResult.error("请输入6位支付密码");
        }

        if (StringUtils.isBlank(user.getUserAgent())) {
            Map<String, String> allParamByMap = paramterService.getAllParamByMap();
            user.setUserAgent(allParamByMap.get("default_agent"));
        }

        User agent = userService.getUserByName(user.getUserAgent());
        if (agent == null) {
            return AjaxResult.error("未查询到上级代理[" + user.getUserAgent() + "]");
        }

        User temp = new User();
        temp.setUserName(user.getUserName());
        temp.setNickName(user.getNickName());
        temp.setBalance(new BigDecimal("0"));
        temp.setFreezeBalance(new BigDecimal("0"));
        temp.setLoginPwd(SecureUtil.md5(user.getLoginPwd()));
        temp.setPayPwd(SecureUtil.md5(user.getPayPwd()));
        temp.setStatus(0L);
        temp.setRealName(null);
        temp.setPhone(null);
        temp.setBankName(null);
        temp.setBankNo(null);
        temp.setBankAddr(null);
        temp.setCreditValue(100L);
        temp.setLevelId(1L);
        temp.setAvatarId(1L);
        temp.setInviteCode(RandomUtil.randomNumbers(6));
        temp.setUserAgent(agent.getUserName());
        temp.setUserAgentNode(agent.getUserAgentNode() + user.getUserName() + "|");
        temp.setUserAgentLevel(agent.getUserAgentLevel() + 1);
        temp.setRegisterTime(new Date());
        temp.setRegisterIp("0.0.0.0");
        temp.setLastTime(new Date());
        temp.setLastIp("");
        temp.setRemake(null);
        temp.setUpdateOrder(0L);
        temp.setDeposit(new BigDecimal("0"));
        temp.setWithdraw(new BigDecimal("0"));
        temp.setIncome(new BigDecimal("0"));
        temp.setBet(new BigDecimal("0"));
        temp.setInviteCount(0L);
        userService.save(temp);

        userService.update(
                new UpdateWrapper<User>().lambda()
                        .setSql("invite_count = invite_count + " + 1)
                        .eq(User::getUserName, agent.getUserName())
        );
        return AjaxResult.success();
    }

    /**
     * 修改会员列表
     */
    @PreAuthorize("@ss.hasPermi('business:user:edit')")
    @Log(title = "会员列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody User user)
    {
        if (StringUtils.isNotBlank(user.getLoginPwd())) {
            boolean matchLoginPwd = ReUtil.isMatch("^[a-zA-Z0-9]{6,12}$", user.getLoginPwd());
            if (!matchLoginPwd) {
                return AjaxResult.error("请输入6-12位登录密码");
            }
            user.setLoginPwd(SecureUtil.md5(user.getLoginPwd()));
        } else {
            user.setLoginPwd(null);
        }

        if (StringUtils.isNotBlank(user.getPayPwd())) {
            boolean matchPayPwd = ReUtil.isMatch("^\\d{6}$", user.getPayPwd());
            if (!matchPayPwd) {
                return AjaxResult.error("请输入6位支付密码");
            }
            user.setPayPwd(SecureUtil.md5(user.getPayPwd()));
        } else {
            user.setPayPwd(null);
        }
        user.setBalance(null);

        return toAjax(userService.updateUser(user));
    }

    @PreAuthorize("@ss.hasPermi('business:user:optMoney')")
    @Log(title = "【重置用户余额】", businessType = BusinessType.UPDATE)
    @PostMapping("/resetBalance")
    public AjaxResult resetBalance(@RequestBody User user)
    {
        userService.update(
                new UpdateWrapper<User>().lambda()
                        .eq(User::getId, user.getId())
                        .set(User::getBalance, user.getBalance())
        );
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('business:user:logout')")
    @Log(title = "【踢下线】", businessType = BusinessType.UPDATE)
    @PostMapping("/logout")
    public AjaxResult logout(@RequestBody User user)
    {
        redisTemplate.delete("user:login:token:" + user.getUserName());
        redisTemplate.delete("user:online:token:" + user.getUserName());
        return AjaxResult.success();
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

    @Transactional
    @PreAuthorize("@ss.hasPermi('business:user:optMoney')")
    @Log(title = "【用户上下分】", businessType = BusinessType.UPDATE)
    @PostMapping("/optMoney")
    public AjaxResult optMoney(@RequestBody OptUserMoneyRequest request) throws Exception {
        User user = userService.getUserByName(request.getUserName());
        if (user == null) {
            return AjaxResult.error("用户不存在.");
        }
        Date now = new Date();
        BigDecimal money = new BigDecimal(request.getMoney());
        if (money.doubleValue() <= 0) {
            return AjaxResult.error("请正确输入金额.");
        }

        if (org.apache.commons.lang3.StringUtils.equals("1", request.getType())) {
            // 加钱
            userService.update(
                    new UpdateWrapper<User>()
                            .setSql("balance = balance + " + money + ",deposit = deposit +" + money)
                            .eq("user_name", user.getUserName())
            );

            // 充值记录
            String orderNo = IdUtil.getSnowflakeNextIdStr();
            Deposit deposit = new Deposit();
            deposit.setOrderNo(IdUtil.getSnowflakeNextIdStr());
            deposit.setUserName(user.getUserName());
            deposit.setRealName(user.getRealName());
            deposit.setOptAmount(money);
            deposit.setOptType(1L);
            deposit.setStatus(1L);
            deposit.setUserAgent(user.getUserAgent());
            deposit.setUserAgentNode(user.getUserAgentNode());
            deposit.setUserAgentLevel(user.getUserAgentLevel());
            deposit.setOrderTime(new Date());
            deposit.setCreateBy(getUsername());
            depositService.save(deposit);

            // 流水记录
            Account account = new Account();
            account.setUserName(user.getUserName());
            account.setOptAmount(money);
            account.setType(1L);
            account.setOptType(1L);
            account.setOptTime(new Date());
            account.setUserAgent(user.getUserAgent());
            account.setUserAgentNode(user.getUserAgentNode());
            account.setUserAgentLevel(user.getUserAgentLevel());
            account.setRefNo(orderNo);
            account.setAccountNo(IdUtil.getSnowflakeNextIdStr());
            account.setRemark(request.getRemark());
            accountService.save(account);

            // 记录报表
            DayReport dayReport = new DayReport();
            dayReport.setUserName(user.getUserName());
            dayReport.setToday(DateUtil.formatDate(now));
            dayReport.setDeposit(money);
            dayReport.setWithdraw(new BigDecimal("0"));
            dayReport.setBet(new BigDecimal("0"));
            dayReport.setCommission(new BigDecimal("0"));
            dayReport.setIncome(new BigDecimal("0"));
            dayReport.setUserAgent(user.getUserAgent());
            dayReport.setUserAgentNode(user.getUserAgentNode());
            dayReport.setUserAgentLevel(user.getUserAgentLevel());
            dayReport.setCreateTime(new Date());
            dayReport.setUpdateTime(new Date());
            dayReportService.insertOrUpdate(dayReport);

            return toAjax(true);
        } else if (org.apache.commons.lang3.StringUtils.equals("2", request.getType())) {
            if (money.doubleValue() > user.getBalance().doubleValue()) {
                return AjaxResult.error("用户账户余额为:" + user.getBalance() + "元");
            }
            // 加钱
            userService.updateUserBalance(user.getUserName(), money.negate());

            // 流水记录
            Account account = new Account();
            account.setUserName(user.getUserName());
            account.setOptAmount(money);
            account.setType(2L);
            account.setOptType(6L);
            account.setOptTime(now);
            account.setUserAgent(user.getUserAgent());
            account.setUserAgentNode(user.getUserAgentNode());
            account.setUserAgentLevel(user.getUserAgentLevel());
            account.setRefNo(null);
            account.setAccountNo(IdUtil.getSnowflakeNextIdStr());
            account.setRemark(request.getRemark());
            accountService.save(account);
            // 下分
            return toAjax(true);
        }
        return toAjax(false);
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
        long totalOrders = orderService.count();
        object.put("totalOrders", totalOrders);
        List<Order> orders = orderService.list(new LambdaQueryWrapper<Order>().between(Order::getOrderTime, yesterdayBegin, todayEnd));
        long todayOrders = 0;
        long yesterdayOrders = 0;
        for (Order order : orders) {
            if (DateUtil.isIn(order.getOrderTime(), todayBegin, todayEnd)) {
                todayOrders += 1;
            }
            if (DateUtil.isIn(order.getOrderTime(), yesterdayBegin, yesterdayEnd)) {
                yesterdayOrders += 1;
            }
        }
        object.put("todayOrders", todayOrders);
        object.put("yesterdayOrders", yesterdayOrders);
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
