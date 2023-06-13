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
 * 充值列表对象 t_deposit
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
@Data
@TableName(value ="t_deposit")
public class Deposit extends BaseEntity
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

    /** 真实姓名 */
    @Excel(name = "真实姓名")
    private String realName;

    /** 操作金额 */
    @Excel(name = "操作金额")
    private BigDecimal optAmount;

    /** 充值方式 1:手动充值 */
    @Excel(name = "充值方式 1:手动充值")
    private Long optType;

    /** 0:未处理 1:处理完成 */
    @Excel(name = "0:未处理 1:处理完成")
    private Long status;

    /** 上级代理 */
    @Excel(name = "上级代理")
    private String userAgent;

    /** 代理树节点 */
    @Excel(name = "代理树节点")
    private String userAgentNode;

    /** 代理层级 */
    @Excel(name = "代理层级")
    private Long userAgentLevel;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

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
    public void setRealName(String realName) 
    {
        this.realName = realName;
    }

    public String getRealName() 
    {
        return realName;
    }
    public void setOptAmount(BigDecimal optAmount) 
    {
        this.optAmount = optAmount;
    }

    public BigDecimal getOptAmount() 
    {
        return optAmount;
    }
    public void setOptType(Long optType) 
    {
        this.optType = optType;
    }

    public Long getOptType() 
    {
        return optType;
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
    public void setOrderTime(Date orderTime) 
    {
        this.orderTime = orderTime;
    }

    public Date getOrderTime() 
    {
        return orderTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderNo", getOrderNo())
            .append("userName", getUserName())
            .append("realName", getRealName())
            .append("optAmount", getOptAmount())
            .append("optType", getOptType())
            .append("status", getStatus())
            .append("userAgent", getUserAgent())
            .append("userAgentNode", getUserAgentNode())
            .append("userAgentLevel", getUserAgentLevel())
            .append("orderTime", getOrderTime())
            .toString();
    }
}
