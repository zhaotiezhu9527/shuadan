package com.juhai.business.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.juhai.common.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 预派送列表对象 t_prepare
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
@Data
@TableName(value ="t_prepare")
public class Prepare
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

    /** 商品id */
    @Excel(name = "商品id")
    private Long goodsId;

    /** 商品数量 */
    @Excel(name = "商品数量")
    private Long goodsCount;

    /** 批次 */
    @Excel(name = "批次")
    private String preBatch;

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

    /** 搜索值 */
    @JsonIgnore
    @TableField(exist = false)
    private String searchValue;

    /** 创建者 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新者 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 备注 */
    private String remark;

    /** 请求参数 */
    @TableField(exist = false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params;

    @TableField(exist = false)
    private Goods goods;

    // 今日已完成单数
    @TableField(exist = false)
    private long finishCount;

    // 选择的商品 商品id:商品数量,商品id:商品数量
    @TableField(exist = false)
    private String goodsSelect;

    @TableField(exist = false)
    private BigDecimal batchOrderAmount;
}
