package com.juhai.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.juhai.common.annotation.Excel;
import com.juhai.common.core.domain.BaseEntity;

/**
 * 客服列表对象 t_customer_service
 * 
 * @author zhaotiezhu
 * @date 2023-06-12
 */
public class CustomerService extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 客服名称 */
    @Excel(name = "客服名称")
    private String serviceName;

    /** 客服链接 */
    @Excel(name = "客服链接")
    private String serviceLink;

    /** 工作时间 */
    @Excel(name = "工作时间")
    private String workTime;

    /** 备注 */
    @Excel(name = "备注")
    private String remake;

    /** 状态(0:启用 1:禁用) */
    @Excel(name = "状态(0:启用 1:禁用)")
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
    public void setServiceName(String serviceName) 
    {
        this.serviceName = serviceName;
    }

    public String getServiceName() 
    {
        return serviceName;
    }
    public void setServiceLink(String serviceLink) 
    {
        this.serviceLink = serviceLink;
    }

    public String getServiceLink() 
    {
        return serviceLink;
    }
    public void setWorkTime(String workTime) 
    {
        this.workTime = workTime;
    }

    public String getWorkTime() 
    {
        return workTime;
    }
    public void setRemake(String remake) 
    {
        this.remake = remake;
    }

    public String getRemake() 
    {
        return remake;
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
            .append("serviceName", getServiceName())
            .append("serviceLink", getServiceLink())
            .append("workTime", getWorkTime())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .append("remake", getRemake())
            .append("status", getStatus())
            .append("pxh", getPxh())
            .toString();
    }
}
