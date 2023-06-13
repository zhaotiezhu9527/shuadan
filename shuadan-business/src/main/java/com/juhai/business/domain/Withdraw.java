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
import lombok.Data;
import com.juhai.common.core.domain.BaseEntity;

/**
 * 提现列表对象 t_withdraw
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
@Data
@TableName(value ="t_withdraw")
public class Withdraw extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderNo;

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    /** 昵称 */
    @Excel(name = "昵称")
    private String nickName;

    /** 提现金额 */
    @Excel(name = "提现金额")
    private BigDecimal optAmount;

    /** 手续费 */
    @Excel(name = "手续费")
    private BigDecimal feeRate;

    /** 到账金额 */
    @Excel(name = "到账金额")
    private BigDecimal realAmount;

    /** 开户行 */
    @Excel(name = "开户行")
    private String bankName;

    /** 真实姓名 */
    @Excel(name = "真实姓名")
    private String realName;

    /** 银行卡号 */
    @Excel(name = "银行卡号")
    private String bankNo;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 订单时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "订单时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date checkTime;

    /** 状态(0:未审核 1:审核通过 2:拒绝) */
    @Excel(name = "状态(0:未审核 1:审核通过 2:拒绝)")
    private Long status;

    /** 上级代理 */
    @Excel(name = "上级代理")
    private String userAgent;

    /** 代理数节点 */
    @Excel(name = "代理数节点")
    private String userAgentNode;

    /** 代理层级 */
    @Excel(name = "代理层级")
    private Long userAgentLevel;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOrderNo(String orderNo) 
    {
        this.orderNo = orderNo;
    }

    public String getOrderNo() 
    {
        return orderNo;
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
    public void setOptAmount(BigDecimal optAmount) 
    {
        this.optAmount = optAmount;
    }

    public BigDecimal getOptAmount() 
    {
        return optAmount;
    }
    public void setFeeRate(BigDecimal feeRate) 
    {
        this.feeRate = feeRate;
    }

    public BigDecimal getFeeRate() 
    {
        return feeRate;
    }
    public void setRealAmount(BigDecimal realAmount) 
    {
        this.realAmount = realAmount;
    }

    public BigDecimal getRealAmount() 
    {
        return realAmount;
    }
    public void setBankName(String bankName) 
    {
        this.bankName = bankName;
    }

    public String getBankName() 
    {
        return bankName;
    }
    public void setRealName(String realName) 
    {
        this.realName = realName;
    }

    public String getRealName() 
    {
        return realName;
    }
    public void setBankNo(String bankNo) 
    {
        this.bankNo = bankNo;
    }

    public String getBankNo() 
    {
        return bankNo;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setOrderTime(Date orderTime) 
    {
        this.orderTime = orderTime;
    }

    public Date getOrderTime() 
    {
        return orderTime;
    }
    public void setCheckTime(Date checkTime) 
    {
        this.checkTime = checkTime;
    }

    public Date getCheckTime() 
    {
        return checkTime;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
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
            .append("orderNo", getOrderNo())
            .append("userName", getUserName())
            .append("nickName", getNickName())
            .append("optAmount", getOptAmount())
            .append("feeRate", getFeeRate())
            .append("realAmount", getRealAmount())
            .append("bankName", getBankName())
            .append("realName", getRealName())
            .append("bankNo", getBankNo())
            .append("phone", getPhone())
            .append("orderTime", getOrderTime())
            .append("checkTime", getCheckTime())
            .append("status", getStatus())
            .append("updateBy", getUpdateBy())
            .append("remark", getRemark())
            .append("userAgent", getUserAgent())
            .append("userAgentNode", getUserAgentNode())
            .append("userAgentLevel", getUserAgentLevel())
            .toString();
    }
}
