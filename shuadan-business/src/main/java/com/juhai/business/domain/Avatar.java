package com.juhai.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.juhai.common.annotation.Excel;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.juhai.common.core.domain.BaseEntity;

/**
 * 头像列表对象 t_avatar
 * 
 * @author zhaotiezhu
 * @date 2023-11-09
 */
@Data
@TableName(value ="t_avatar")
public class Avatar extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 头像地址 */
    @Excel(name = "头像地址")
    private String imgUrl;

    /** 状态(0:正常 1:禁用) */
    @Excel(name = "状态(0:正常 1:禁用)")
    private Long status;

    /** 排序号 */
    @Excel(name = "排序号")
    private Long pxh;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setImgUrl(String imgUrl) 
    {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() 
    {
        return imgUrl;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setPxh(Long pxh) 
    {
        this.pxh = pxh;
    }

    public Long getPxh() 
    {
        return pxh;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("imgUrl", getImgUrl())
            .append("status", getStatus())
            .append("pxh", getPxh())
            .toString();
    }
}
