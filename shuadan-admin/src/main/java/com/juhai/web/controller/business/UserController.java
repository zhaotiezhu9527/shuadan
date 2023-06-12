package com.juhai.web.controller.business;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.juhai.business.domain.User;
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
        // 商品
        object.put("totalGoods", 0);
        object.put("todayUsers", 0);
        object.put("yesterdayUsers", 0);
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
        // 订单总量
        object.put("totalOrders", 0);
        object.put("todayOrders", 0);
        object.put("yesterdayOrders", 0);
        // 订单总金额
        object.put("totalOrderAmounts", 0);
        object.put("todayOrderAmounts", 0);
        object.put("yesterdayOrderAmounts", 0);
        // 用户充值
        object.put("totalDepositAmounts", 0);
        object.put("todayDepositAmounts", 0);
        object.put("yesterdayDepositAmounts", 0);
        // 用户提现
        object.put("totalWithdrawAmounts", 0);
        object.put("todayWithdrawAmounts", 0);
        object.put("yesterdayWithdrawAmounts", 0);
        // 抢单佣金
        object.put("totalCommissionAmounts", 0);
        object.put("todayCommissionAmounts", 0);
        object.put("yesterdayCommissionAmounts", 0);
        // 用户总金额
        User totalUserBalance = userService.getOne(new QueryWrapper<User>().select("sum(balance) as balance"));
        object.put("totalUserBalance", totalUserBalance.getBalance());
        return AjaxResult.success(object);
    }
}
