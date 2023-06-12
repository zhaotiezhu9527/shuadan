package com.juhai.business.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.juhai.common.annotation.Excel;
import com.juhai.common.core.domain.BaseEntity;

/**
 * 等级列表对象 t_level
 * 
 * @author zhaotiezhu
 * @date 2023-06-12
 */
public class Level extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 等级值 */
    @Excel(name = "等级值")
    private Long levelValue;

    /** 等级名称 */
    @Excel(name = "等级名称")
    private String levelName;

    /** 等级价格 */
    @Excel(name = "等级价格")
    private BigDecimal levelPrice;

    /** 等级图标 */
    @Excel(name = "等级图标")
    private String levelIcon;

    /** 每日订单量 */
    @Excel(name = "每日订单量")
    private Long dayOrderCount;

    /** 佣金比例 */
    @Excel(name = "佣金比例")
    private BigDecimal commissionRate;

    /** 每日提现次数 */
    @Excel(name = "每日提现次数")
    private Long dayWithdrawCount;

    /** 最小提现金额 */
    @Excel(name = "最小提现金额")
    private BigDecimal minWithdrawAmount;

    /** 最大提现金额 */
    @Excel(name = "最大提现金额")
    private BigDecimal maxWithdrawAmount;

    /** 提现手续费 */
    @Excel(name = "提现手续费")
    private BigDecimal withdrawFee;

    /** 提现需要完成 几笔订单才开提现 / 天 */
    @Excel(name = "提现需要完成 几笔订单才开提现 / 天")
    private Long withdrawOrderCount;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setLevelValue(Long levelValue) 
    {
        this.levelValue = levelValue;
    }

    public Long getLevelValue() 
    {
        return levelValue;
    }
    public void setLevelName(String levelName) 
    {
        this.levelName = levelName;
    }

    public String getLevelName() 
    {
        return levelName;
    }
    public void setLevelPrice(BigDecimal levelPrice) 
    {
        this.levelPrice = levelPrice;
    }

    public BigDecimal getLevelPrice() 
    {
        return levelPrice;
    }
    public void setLevelIcon(String levelIcon) 
    {
        this.levelIcon = levelIcon;
    }

    public String getLevelIcon() 
    {
        return levelIcon;
    }
    public void setDayOrderCount(Long dayOrderCount) 
    {
        this.dayOrderCount = dayOrderCount;
    }

    public Long getDayOrderCount() 
    {
        return dayOrderCount;
    }
    public void setCommissionRate(BigDecimal commissionRate) 
    {
        this.commissionRate = commissionRate;
    }

    public BigDecimal getCommissionRate() 
    {
        return commissionRate;
    }
    public void setDayWithdrawCount(Long dayWithdrawCount) 
    {
        this.dayWithdrawCount = dayWithdrawCount;
    }

    public Long getDayWithdrawCount() 
    {
        return dayWithdrawCount;
    }
    public void setMinWithdrawAmount(BigDecimal minWithdrawAmount) 
    {
        this.minWithdrawAmount = minWithdrawAmount;
    }

    public BigDecimal getMinWithdrawAmount() 
    {
        return minWithdrawAmount;
    }
    public void setMaxWithdrawAmount(BigDecimal maxWithdrawAmount) 
    {
        this.maxWithdrawAmount = maxWithdrawAmount;
    }

    public BigDecimal getMaxWithdrawAmount() 
    {
        return maxWithdrawAmount;
    }
    public void setWithdrawFee(BigDecimal withdrawFee) 
    {
        this.withdrawFee = withdrawFee;
    }

    public BigDecimal getWithdrawFee() 
    {
        return withdrawFee;
    }
    public void setWithdrawOrderCount(Long withdrawOrderCount) 
    {
        this.withdrawOrderCount = withdrawOrderCount;
    }

    public Long getWithdrawOrderCount() 
    {
        return withdrawOrderCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("levelValue", getLevelValue())
            .append("levelName", getLevelName())
            .append("levelPrice", getLevelPrice())
            .append("levelIcon", getLevelIcon())
            .append("dayOrderCount", getDayOrderCount())
            .append("commissionRate", getCommissionRate())
            .append("dayWithdrawCount", getDayWithdrawCount())
            .append("minWithdrawAmount", getMinWithdrawAmount())
            .append("maxWithdrawAmount", getMaxWithdrawAmount())
            .append("withdrawFee", getWithdrawFee())
            .append("withdrawOrderCount", getWithdrawOrderCount())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateTime", getUpdateTime())
            .append("updateBy", getUpdateBy())
            .append("remark", getRemark())
            .toString();
    }
}
