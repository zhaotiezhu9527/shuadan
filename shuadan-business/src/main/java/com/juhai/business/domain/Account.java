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
 * 账变列表对象 t_account
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
@Data
@TableName(value ="t_account")
public class Account extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 流水号 */
    @Excel(name = "流水号")
    private String accountNo;

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    /** 操作金额 */
    @Excel(name = "操作金额")
    private BigDecimal optAmount;

    /** 1:收入 2:支出 */
    @Excel(name = "1:收入 2:支出")
    private Long type;

    /** 1:充值 2:提现 3:用户接单 4:接单返佣 5:下级返佣 */
    @Excel(name = "1:充值 2:提现 3:用户接单 4:接单返佣 5:下级返佣 6:后台扣款")
    private Long optType;

    /** 上级代理 */
    @Excel(name = "上级代理")
    private String userAgent;

    /** 代理树节点 */
    @Excel(name = "代理树节点")
    private String userAgentNode;

    /** 代理层级 */
    @Excel(name = "代理层级")
    private Long userAgentLevel;

    /** 关联订单号 */
    @Excel(name = "关联订单号")
    private String refNo;

    /** 操作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date optTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setAccountNo(String accountNo) 
    {
        this.accountNo = accountNo;
    }

    public String getAccountNo() 
    {
        return accountNo;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setOptAmount(BigDecimal optAmount) 
    {
        this.optAmount = optAmount;
    }

    public BigDecimal getOptAmount() 
    {
        return optAmount;
    }
    public void setType(Long type) 
    {
        this.type = type;
    }

    public Long getType() 
    {
        return type;
    }
    public void setOptType(Long optType) 
    {
        this.optType = optType;
    }

    public Long getOptType() 
    {
        return optType;
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
    public void setRefNo(String refNo) 
    {
        this.refNo = refNo;
    }

    public String getRefNo() 
    {
        return refNo;
    }
    public void setOptTime(Date optTime) 
    {
        this.optTime = optTime;
    }

    public Date getOptTime() 
    {
        return optTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("accountNo", getAccountNo())
            .append("userName", getUserName())
            .append("optAmount", getOptAmount())
            .append("type", getType())
            .append("optType", getOptType())
            .append("userAgent", getUserAgent())
            .append("userAgentNode", getUserAgentNode())
            .append("userAgentLevel", getUserAgentLevel())
            .append("refNo", getRefNo())
            .append("optTime", getOptTime())
            .append("remark", getRemark())
            .toString();
    }
}
