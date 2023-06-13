package com.juhai.business.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.juhai.common.annotation.Excel;
import com.juhai.common.core.domain.BaseEntity;

/**
 * 商品列表对象 t_goods
 * 
 * @author zhaotiezhu
 * @date 2023-06-12
 */

@Data
@TableName(value ="t_goods")
public class Goods
{
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String goodsName;

    /** 商品价格 */
    @Excel(name = "商品价格")
    private BigDecimal goodsPrice;

    /** 商品图片 */
    @Excel(name = "商品图片")
    private String goodsImg;

    /** 专区ID */
    @Excel(name = "专区ID")
    private Long areaId;

    /** 商店名称 */
    @Excel(name = "商店名称")
    private String shopName;

    /** 商品详情 */
    @Excel(name = "商品详情")
    private String goodsDetail;

    /** 状态(0:启用 1:禁用) */
    @Excel(name = "状态(0:启用 1:禁用)")
    private Long status;

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
    private Area area;
}
