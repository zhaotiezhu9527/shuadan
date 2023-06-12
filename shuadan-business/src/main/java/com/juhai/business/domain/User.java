package com.juhai.business.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.juhai.common.annotation.Excel;
import com.juhai.common.core.domain.BaseEntity;

/**
 * 会员列表对象 t_user
 * 
 * @author zhaotiezhu
 * @date 2023-06-12
 */
@TableName(value ="t_user")
public class User extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    /** 昵称 */
    @Excel(name = "昵称")
    private String nickName;

    /** 用户余额 */
    @Excel(name = "用户余额")
    private BigDecimal balance;

    /** 冻结金额 */
    @Excel(name = "冻结金额")
    private BigDecimal freezeBalance;

    /** 登录密码 */
    @Excel(name = "登录密码")
    private String loginPwd;

    /** 支付密码 */
    @Excel(name = "支付密码")
    private String payPwd;

    /** 状态(0:正常 1:冻结) */
    @Excel(name = "状态(0:正常 1:冻结)")
    private Long status;

    /** 真实姓名 */
    @Excel(name = "真实姓名")
    private String realName;

    /** 用户手机号 */
    @Excel(name = "用户手机号")
    private String phone;

    /** 开户行 */
    @Excel(name = "开户行")
    private String bankName;

    /** 银行卡号 */
    @Excel(name = "银行卡号")
    private String bankNo;

    /** 开户行地址 */
    @Excel(name = "开户行地址")
    private String bankAddr;

    /** 信用分 */
    @Excel(name = "信用分")
    private Long creditValue;

    /** 用户等级 */
    @Excel(name = "用户等级")
    private Long levelId;

    /** 头像ID */
    @Excel(name = "头像ID")
    private Long avatarId;

    /** 邀请码 */
    @Excel(name = "邀请码")
    private String inviteCode;

    /** 上级代理 */
    @Excel(name = "上级代理")
    private String userAgent;

    /** 代理节点数 */
    @Excel(name = "代理节点数")
    private String userAgentNode;

    /** 层级数 */
    @Excel(name = "层级数")
    private Long userAgentLevel;

    /** 注册时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "注册时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date registerTime;

    /** 注册IP */
    @Excel(name = "注册IP")
    private String registerIp;

    /** 最后登录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date lastTime;

    /** 最后登录IP */
    @Excel(name = "最后登录IP")
    private String lastIp;

    /** 备注 */
    @Excel(name = "备注")
    private String remake;

    /** 是否累加订单(0:是 1:否) */
    @Excel(name = "是否累加订单(0:是 1:否)")
    private Long updateOrder;

    /** 总充值 */
    @Excel(name = "总充值")
    private BigDecimal deposit;

    /** 总提现 */
    @Excel(name = "总提现")
    private BigDecimal withdraw;

    /** 总收益 */
    @Excel(name = "总收益")
    private BigDecimal income;

    /** 总投注 */
    @Excel(name = "总投注")
    private BigDecimal bet;

    /** 推荐人数 */
    @Excel(name = "推荐人数")
    private Long inviteCount;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setNickName(String nickName) 
    {
        this.nickName = nickName;
    }

    public String getNickName() 
    {
        return nickName;
    }
    public void setBalance(BigDecimal balance) 
    {
        this.balance = balance;
    }

    public BigDecimal getBalance() 
    {
        return balance;
    }
    public void setFreezeBalance(BigDecimal freezeBalance) 
    {
        this.freezeBalance = freezeBalance;
    }

    public BigDecimal getFreezeBalance() 
    {
        return freezeBalance;
    }
    public void setLoginPwd(String loginPwd) 
    {
        this.loginPwd = loginPwd;
    }

    public String getLoginPwd() 
    {
        return loginPwd;
    }
    public void setPayPwd(String payPwd) 
    {
        this.payPwd = payPwd;
    }

    public String getPayPwd() 
    {
        return payPwd;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setRealName(String realName) 
    {
        this.realName = realName;
    }

    public String getRealName() 
    {
        return realName;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setBankName(String bankName) 
    {
        this.bankName = bankName;
    }

    public String getBankName() 
    {
        return bankName;
    }
    public void setBankNo(String bankNo) 
    {
        this.bankNo = bankNo;
    }

    public String getBankNo() 
    {
        return bankNo;
    }
    public void setBankAddr(String bankAddr) 
    {
        this.bankAddr = bankAddr;
    }

    public String getBankAddr() 
    {
        return bankAddr;
    }
    public void setCreditValue(Long creditValue) 
    {
        this.creditValue = creditValue;
    }

    public Long getCreditValue() 
    {
        return creditValue;
    }
    public void setLevelId(Long levelId) 
    {
        this.levelId = levelId;
    }

    public Long getLevelId() 
    {
        return levelId;
    }
    public void setAvatarId(Long avatarId) 
    {
        this.avatarId = avatarId;
    }

    public Long getAvatarId() 
    {
        return avatarId;
    }
    public void setInviteCode(String inviteCode) 
    {
        this.inviteCode = inviteCode;
    }

    public String getInviteCode() 
    {
        return inviteCode;
    }
    public void setUserAgent(String userAgent) 
    {
        this.userAgent = userAgent;
    }

    public String getUserAgent() 
    {
        return userAgent;
    }
    public void setUserAgentNode(String userAgentNode) 
    {
        this.userAgentNode = userAgentNode;
    }

    public String getUserAgentNode() 
    {
        return userAgentNode;
    }
    public void setUserAgentLevel(Long userAgentLevel) 
    {
        this.userAgentLevel = userAgentLevel;
    }

    public Long getUserAgentLevel() 
    {
        return userAgentLevel;
    }
    public void setRegisterTime(Date registerTime) 
    {
        this.registerTime = registerTime;
    }

    public Date getRegisterTime() 
    {
        return registerTime;
    }
    public void setRegisterIp(String registerIp) 
    {
        this.registerIp = registerIp;
    }

    public String getRegisterIp() 
    {
        return registerIp;
    }
    public void setLastTime(Date lastTime) 
    {
        this.lastTime = lastTime;
    }

    public Date getLastTime() 
    {
        return lastTime;
    }
    public void setLastIp(String lastIp) 
    {
        this.lastIp = lastIp;
    }

    public String getLastIp() 
    {
        return lastIp;
    }
    public void setRemake(String remake) 
    {
        this.remake = remake;
    }

    public String getRemake() 
    {
        return remake;
    }
    public void setUpdateOrder(Long updateOrder) 
    {
        this.updateOrder = updateOrder;
    }

    public Long getUpdateOrder() 
    {
        return updateOrder;
    }
    public void setDeposit(BigDecimal deposit) 
    {
        this.deposit = deposit;
    }

    public BigDecimal getDeposit() 
    {
        return deposit;
    }
    public void setWithdraw(BigDecimal withdraw) 
    {
        this.withdraw = withdraw;
    }

    public BigDecimal getWithdraw() 
    {
        return withdraw;
    }
    public void setIncome(BigDecimal income) 
    {
        this.income = income;
    }

    public BigDecimal getIncome() 
    {
        return income;
    }
    public void setBet(BigDecimal bet) 
    {
        this.bet = bet;
    }

    public BigDecimal getBet() 
    {
        return bet;
    }
    public void setInviteCount(Long inviteCount) 
    {
        this.inviteCount = inviteCount;
    }

    public Long getInviteCount() 
    {
        return inviteCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userName", getUserName())
            .append("nickName", getNickName())
            .append("balance", getBalance())
            .append("freezeBalance", getFreezeBalance())
            .append("loginPwd", getLoginPwd())
            .append("payPwd", getPayPwd())
            .append("status", getStatus())
            .append("realName", getRealName())
            .append("phone", getPhone())
            .append("bankName", getBankName())
            .append("bankNo", getBankNo())
            .append("bankAddr", getBankAddr())
            .append("creditValue", getCreditValue())
            .append("levelId", getLevelId())
            .append("avatarId", getAvatarId())
            .append("inviteCode", getInviteCode())
            .append("userAgent", getUserAgent())
            .append("userAgentNode", getUserAgentNode())
            .append("userAgentLevel", getUserAgentLevel())
            .append("registerTime", getRegisterTime())
            .append("registerIp", getRegisterIp())
            .append("lastTime", getLastTime())
            .append("lastIp", getLastIp())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .append("remake", getRemake())
            .append("updateOrder", getUpdateOrder())
            .append("deposit", getDeposit())
            .append("withdraw", getWithdraw())
            .append("income", getIncome())
            .append("bet", getBet())
            .append("inviteCount", getInviteCount())
            .toString();
    }
}
