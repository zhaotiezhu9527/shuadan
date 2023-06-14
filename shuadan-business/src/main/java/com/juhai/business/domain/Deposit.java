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
public class Deposit
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

    @Excel(name = "创建人")
    private String createBy;
}
