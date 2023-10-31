package com.juhai.business.service;

import java.util.List;
import com.juhai.business.domain.OrderCount;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * 每日订单量Service接口
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
public interface IOrderCountService extends IService<OrderCount>
{
    /**
     * 查询每日订单量
     * 
     * @param id 每日订单量主键
     * @return 每日订单量
     */
    public OrderCount selectOrderCountById(Long id);

    /**
     * 查询每日订单量列表
     * 
     * @param orderCount 每日订单量
     * @return 每日订单量集合
     */
    public List<OrderCount> selectOrderCountList(OrderCount orderCount);

    /**
     * 新增每日订单量
     * 
     * @param orderCount 每日订单量
     * @return 结果
     */
    public int insertOrderCount(OrderCount orderCount);

    /**
     * 修改每日订单量
     * 
     * @param orderCount 每日订单量
     * @return 结果
     */
    public int updateOrderCount(OrderCount orderCount);

    /**
     * 批量删除每日订单量
     * 
     * @param ids 需要删除的每日订单量主键集合
     * @return 结果
     */
    public int deleteOrderCountByIds(Long[] ids);

    /**
     * 删除每日订单量信息
     * 
     * @param id 每日订单量主键
     * @return 结果
     */
    public int deleteOrderCountById(Long id);

    public void insertOrUpdate(OrderCount report) throws Exception;
}
