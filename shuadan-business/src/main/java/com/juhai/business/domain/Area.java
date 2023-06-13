package com.juhai.business.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.juhai.common.annotation.Excel;
import com.juhai.common.core.domain.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品分类对象 t_area
 * 
 * @author zhaotiezhu
 * @date 2023-06-12
 */
@Data
@TableName(value ="t_area")
public class Area extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 区域名称 */
    @Excel(name = "区域名称")
    private String areaName;

    /** 区域icon */
    @Excel(name = "区域icon")
    private String areaIcon;

    /** 专区金额 */
    @Excel(name = "专区金额")
    private BigDecimal areaBalance;

    /** 等级ID */
    @Excel(name = "等级ID")
    private Long levelId;

    /** 状态(0:启用 1:停用) */
    @Excel(name = "状态(0:启用 1:停用)")
    private Long status;

    /** 排序号 */
    @Excel(name = "排序号")
    private Long pxh;

    @TableField(exist = false)
    private Level level;
}
