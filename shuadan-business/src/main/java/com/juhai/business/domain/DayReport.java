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
 * 日报表对象 t_day_report
 * 
 * @author zhaotiezhu
 * @date 2023-06-12
 */
@TableName(value ="t_day_report")
public class DayReport
{
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date today;

    /** 充值量 */
    @Excel(name = "充值量")
    private BigDecimal deposit;

    /** 提现量 */
    @Excel(name = "提现量")
    private BigDecimal withdraw;

    /** 投注量 */
    @Excel(name = "投注量")
    private BigDecimal bet;

    /** 返佣量 */
    @Excel(name = "返佣量")
    private BigDecimal commission;

    /** 收入量 */
    @Excel(name = "收入量")
    private BigDecimal income;

    /** 上级代理 */
    @Excel(name = "上级代理")
    private String userAgent;

    /** 上级代理节点 */
    @Excel(name = "上级代理节点")
    private String userAgentNode;

    /** 上级代理层级 */
    @Excel(name = "上级代理层级")
    private Long userAgentLevel;

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
    public void setToday(Date today) 
    {
        this.today = today;
    }

    public Date getToday() 
    {
        return today;
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
    public void setBet(BigDecimal bet) 
    {
        this.bet = bet;
    }

    public BigDecimal getBet() 
    {
        return bet;
    }
    public void setCommission(BigDecimal commission) 
    {
        this.commission = commission;
    }

    public BigDecimal getCommission() 
    {
        return commission;
    }
    public void setIncome(BigDecimal income) 
    {
        this.income = income;
    }

    public BigDecimal getIncome() 
    {
        return income;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userName", getUserName())
            .append("today", getToday())
            .append("deposit", getDeposit())
            .append("withdraw", getWithdraw())
            .append("bet", getBet())
            .append("commission", getCommission())
            .append("income", getIncome())
            .append("userAgent", getUserAgent())
            .append("userAgentNode", getUserAgentNode())
            .append("userAgentLevel", getUserAgentLevel())
            .toString();
    }
}
