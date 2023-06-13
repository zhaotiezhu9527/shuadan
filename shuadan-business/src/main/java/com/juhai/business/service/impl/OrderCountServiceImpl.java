package com.juhai.business.service.impl;

import java.util.List;
import com.juhai.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juhai.business.mapper.OrderCountMapper;
import com.juhai.business.domain.OrderCount;
import com.juhai.business.service.IOrderCountService;

/**
 * 每日订单量Service业务层处理
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
@Service
public class OrderCountServiceImpl extends ServiceImpl<OrderCountMapper, OrderCount> implements IOrderCountService
{
    @Autowired
    private OrderCountMapper orderCountMapper;

    /**
     * 查询每日订单量
     * 
     * @param id 每日订单量主键
     * @return 每日订单量
     */
    @Override
    public OrderCount selectOrderCountById(Long id)
    {
        return orderCountMapper.selectOrderCountById(id);
    }

    /**
     * 查询每日订单量列表
     * 
     * @param orderCount 每日订单量
     * @return 每日订单量
     */
    @Override
    public List<OrderCount> selectOrderCountList(OrderCount orderCount)
    {
        return orderCountMapper.selectOrderCountList(orderCount);
    }

    /**
     * 新增每日订单量
     * 
     * @param orderCount 每日订单量
     * @return 结果
     */
    @Override
    public int insertOrderCount(OrderCount orderCount)
    {
        orderCount.setCreateTime(DateUtils.getNowDate());
        return orderCountMapper.insertOrderCount(orderCount);
    }

    /**
     * 修改每日订单量
     * 
     * @param orderCount 每日订单量
     * @return 结果
     */
    @Override
    public int updateOrderCount(OrderCount orderCount)
    {
        orderCount.setUpdateTime(DateUtils.getNowDate());
        return orderCountMapper.updateOrderCount(orderCount);
    }

    /**
     * 批量删除每日订单量
     * 
     * @param ids 需要删除的每日订单量主键
     * @return 结果
     */
    @Override
    public int deleteOrderCountByIds(Long[] ids)
    {
        return orderCountMapper.deleteOrderCountByIds(ids);
    }

    /**
     * 删除每日订单量信息
     * 
     * @param id 每日订单量主键
     * @return 结果
     */
    @Override
    public int deleteOrderCountById(Long id)
    {
        return orderCountMapper.deleteOrderCountById(id);
    }
}
