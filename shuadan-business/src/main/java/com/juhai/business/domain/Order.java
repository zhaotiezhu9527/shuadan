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
 * 订单列表对象 t_order
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
@Data
@TableName(value ="t_order")
public class Order extends BaseEntity
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

    /** 商品ID */
    @Excel(name = "商品ID")
    private Long goodsId;

    /** 商品价格 */
    @Excel(name = "商品价格")
    private BigDecimal goodsPrice;

    /** 商品数量 */
    @Excel(name = "商品数量")
    private Long goodsCount;

    /** 订单金额 */
    @Excel(name = "订单金额")
    private BigDecimal orderAmount;

    /** 佣金 */
    @Excel(name = "佣金")
    private BigDecimal commission;

    /** 佣金比例 */
    @Excel(name = "佣金比例")
    private BigDecimal commissionRate;

    /** 状态(0:待处理 1:已完成 2:冻结中) */
    @Excel(name = "状态(0:待处理 1:已完成 2:冻结中)")
    private Long status;

    /** 订单时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "订单时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date orderTime;

    /** 支付时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date payTime;

    /** 预派送ID */
    @Excel(name = "预派送ID")
    private Long preId;

    /** 预派送详情ID */
    @Excel(name = "预派送详情ID")
    private Long preDetailId;

    /** 今日第几单 */
    @Excel(name = "今日第几单")
    private Long countNum;

    /** 佣金倍数 */
    @Excel(name = "佣金倍数")
    private Long commissionMul;

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
    public void setGoodsId(Long goodsId) 
    {
        this.goodsId = goodsId;
    }

    public Long getGoodsId() 
    {
        return goodsId;
    }
    public void setGoodsPrice(BigDecimal goodsPrice) 
    {
        this.goodsPrice = goodsPrice;
    }

    public BigDecimal getGoodsPrice() 
    {
        return goodsPrice;
    }
    public void setGoodsCount(Long goodsCount) 
    {
        this.goodsCount = goodsCount;
    }

    public Long getGoodsCount() 
    {
        return goodsCount;
    }
    public void setOrderAmount(BigDecimal orderAmount) 
    {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getOrderAmount() 
    {
        return orderAmount;
    }
    public void setCommission(BigDecimal commission) 
    {
        this.commission = commission;
    }

    public BigDecimal getCommission() 
    {
        return commission;
    }
    public void setCommissionRate(BigDecimal commissionRate) 
    {
        this.commissionRate = commissionRate;
    }

    public BigDecimal getCommissionRate() 
    {
        return commissionRate;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setOrderTime(Date orderTime) 
    {
        this.orderTime = orderTime;
    }

    public Date getOrderTime() 
    {
        return orderTime;
    }
    public void setPayTime(Date payTime) 
    {
        this.payTime = payTime;
    }

    public Date getPayTime() 
    {
        return payTime;
    }
    public void setPreId(Long preId) 
    {
        this.preId = preId;
    }

    public Long getPreId() 
    {
        return preId;
    }
    public void setPreDetailId(Long preDetailId) 
    {
        this.preDetailId = preDetailId;
    }

    public Long getPreDetailId() 
    {
        return preDetailId;
    }
    public void setCountNum(Long countNum) 
    {
        this.countNum = countNum;
    }

    public Long getCountNum() 
    {
        return countNum;
    }
    public void setCommissionMul(Long commissionMul) 
    {
        this.commissionMul = commissionMul;
    }

    public Long getCommissionMul() 
    {
        return commissionMul;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderNo", getOrderNo())
            .append("userName", getUserName())
            .append("nickName", getNickName())
            .append("goodsId", getGoodsId())
            .append("goodsPrice", getGoodsPrice())
            .append("goodsCount", getGoodsCount())
            .append("orderAmount", getOrderAmount())
            .append("commission", getCommission())
            .append("commissionRate", getCommissionRate())
            .append("status", getStatus())
            .append("orderTime", getOrderTime())
            .append("payTime", getPayTime())
            .append("preId", getPreId())
            .append("preDetailId", getPreDetailId())
            .append("countNum", getCountNum())
            .append("commissionMul", getCommissionMul())
            .toString();
    }
}
