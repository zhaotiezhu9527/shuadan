package com.juhai.business.mapper;

import java.util.List;
import com.juhai.business.domain.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
/**
 * 订单列表Mapper接口
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
public interface OrderMapper extends BaseMapper<Order>
{
    /**
     * 查询订单列表
     * 
     * @param id 订单列表主键
     * @return 订单列表
     */
    public Order selectOrderById(Long id);

    /**
     * 查询订单列表列表
     * 
     * @param order 订单列表
     * @return 订单列表集合
     */
    public List<Order> selectOrderList(Order order);

    /**
     * 新增订单列表
     * 
     * @param order 订单列表
     * @return 结果
     */
    public int insertOrder(Order order);

    /**
     * 修改订单列表
     * 
     * @param order 订单列表
     * @return 结果
     */
    public int updateOrder(Order order);

    /**
     * 删除订单列表
     * 
     * @param id 订单列表主键
     * @return 结果
     */
    public int deleteOrderById(Long id);

    /**
     * 批量删除订单列表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderByIds(Long[] ids);
}
