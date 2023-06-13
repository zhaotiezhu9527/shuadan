package com.juhai.business.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.juhai.common.annotation.Excel;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.juhai.common.core.domain.BaseEntity;

/**
 * 预派送详情列表对象 t_prepare_detail
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
@Data
@TableName(value ="t_prepare_detail")
public class PrepareDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 预派送ID */
    @Excel(name = "预派送ID")
    private Long preId;

    /** 商品ID */
    @Excel(name = "商品ID")
    private Long goodsId;

    /** 商品数量 */
    @Excel(name = "商品数量")
    private Long goodsCount;

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
    public void setPreId(Long preId) 
    {
        this.preId = preId;
    }

    public Long getPreId() 
    {
        return preId;
    }
    public void setGoodsId(Long goodsId) 
    {
        this.goodsId = goodsId;
    }

    public Long getGoodsId() 
    {
        return goodsId;
    }
    public void setGoodsCount(Long goodsCount) 
    {
        this.goodsCount = goodsCount;
    }

    public Long getGoodsCount() 
    {
        return goodsCount;
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
            .append("preId", getPreId())
            .append("goodsId", getGoodsId())
            .append("goodsCount", getGoodsCount())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .append("remark", getRemark())
            .toString();
    }
}
