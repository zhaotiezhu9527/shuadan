package com.juhai.business.mapper;

import java.util.List;
import com.juhai.business.domain.CustomerService;

/**
 * 客服列表Mapper接口
 * 
 * @author zhaotiezhu
 * @date 2023-06-12
 */
public interface CustomerServiceMapper 
{
    /**
     * 查询客服列表
     * 
     * @param id 客服列表主键
     * @return 客服列表
     */
    public CustomerService selectCustomerServiceById(Long id);

    /**
     * 查询客服列表列表
     * 
     * @param customerService 客服列表
     * @return 客服列表集合
     */
    public List<CustomerService> selectCustomerServiceList(CustomerService customerService);

    /**
     * 新增客服列表
     * 
     * @param customerService 客服列表
     * @return 结果
     */
    public int insertCustomerService(CustomerService customerService);

    /**
     * 修改客服列表
     * 
     * @param customerService 客服列表
     * @return 结果
     */
    public int updateCustomerService(CustomerService customerService);

    /**
     * 删除客服列表
     * 
     * @param id 客服列表主键
     * @return 结果
     */
    public int deleteCustomerServiceById(Long id);

    /**
     * 批量删除客服列表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCustomerServiceByIds(Long[] ids);
}
