package com.juhai.business.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.juhai.common.annotation.Excel;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.juhai.common.core.domain.BaseEntity;

/**
 * 每日订单量对象 t_order_count
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
@Data
@TableName(value ="t_order_count")
public class OrderCount extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    /** 今日时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "今日时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date today;

    /** 今日订单数 */
    @Excel(name = "今日订单数")
    private Long orderCount;

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
    public void setOrderCount(Long orderCount) 
    {
        this.orderCount = orderCount;
    }

    public Long getOrderCount() 
    {
        return orderCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userName", getUserName())
            .append("today", getToday())
            .append("orderCount", getOrderCount())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
