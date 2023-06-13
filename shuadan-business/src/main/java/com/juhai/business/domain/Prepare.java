package com.juhai.business.domain;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.juhai.common.annotation.Excel;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.juhai.common.core.domain.BaseEntity;

/**
 * 预派送列表对象 t_prepare
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
@Data
@TableName(value ="t_prepare")
public class Prepare extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    /** 触发单数 */
    @Excel(name = "触发单数")
    private Long triggerNum;

    /** 提示文本 */
    @Excel(name = "提示文本")
    private String promptText;

    /** 佣金倍数 */
    @Excel(name = "佣金倍数")
    private Long commissionMul;

    /** 订单总额 */
    @Excel(name = "订单总额")
    private BigDecimal orderAmount;

    /** 状态(0:未完成 1:已完成) */
    @Excel(name = "状态(0:未完成 1:已完成)")
    private Long status;

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
    public void setTriggerNum(Long triggerNum) 
    {
        this.triggerNum = triggerNum;
    }

    public Long getTriggerNum() 
    {
        return triggerNum;
    }
    public void setPromptText(String promptText) 
    {
        this.promptText = promptText;
    }

    public String getPromptText() 
    {
        return promptText;
    }
    public void setCommissionMul(Long commissionMul) 
    {
        this.commissionMul = commissionMul;
    }

    public Long getCommissionMul() 
    {
        return commissionMul;
    }
    public void setOrderAmount(BigDecimal orderAmount) 
    {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getOrderAmount() 
    {
        return orderAmount;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userName", getUserName())
            .append("triggerNum", getTriggerNum())
            .append("promptText", getPromptText())
            .append("commissionMul", getCommissionMul())
            .append("orderAmount", getOrderAmount())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .append("remark", getRemark())
            .toString();
    }
}
