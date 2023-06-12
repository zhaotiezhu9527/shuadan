package com.juhai.business.service.impl;

import java.util.List;
import com.juhai.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.juhai.business.mapper.CustomerServiceMapper;
import com.juhai.business.domain.CustomerService;
import com.juhai.business.service.ICustomerServiceService;

/**
 * 客服列表Service业务层处理
 * 
 * @author zhaotiezhu
 * @date 2023-06-12
 */
@Service
public class CustomerServiceServiceImpl implements ICustomerServiceService 
{
    @Autowired
    private CustomerServiceMapper customerServiceMapper;

    /**
     * 查询客服列表
     * 
     * @param id 客服列表主键
     * @return 客服列表
     */
    @Override
    public CustomerService selectCustomerServiceById(Long id)
    {
        return customerServiceMapper.selectCustomerServiceById(id);
    }

    /**
     * 查询客服列表列表
     * 
     * @param customerService 客服列表
     * @return 客服列表
     */
    @Override
    public List<CustomerService> selectCustomerServiceList(CustomerService customerService)
    {
        return customerServiceMapper.selectCustomerServiceList(customerService);
    }

    /**
     * 新增客服列表
     * 
     * @param customerService 客服列表
     * @return 结果
     */
    @Override
    public int insertCustomerService(CustomerService customerService)
    {
        customerService.setCreateTime(DateUtils.getNowDate());
        return customerServiceMapper.insertCustomerService(customerService);
    }

    /**
     * 修改客服列表
     * 
     * @param customerService 客服列表
     * @return 结果
     */
    @Override
    public int updateCustomerService(CustomerService customerService)
    {
        customerService.setUpdateTime(DateUtils.getNowDate());
        return customerServiceMapper.updateCustomerService(customerService);
    }

    /**
     * 批量删除客服列表
     * 
     * @param ids 需要删除的客服列表主键
     * @return 结果
     */
    @Override
    public int deleteCustomerServiceByIds(Long[] ids)
    {
        return customerServiceMapper.deleteCustomerServiceByIds(ids);
    }

    /**
     * 删除客服列表信息
     * 
     * @param id 客服列表主键
     * @return 结果
     */
    @Override
    public int deleteCustomerServiceById(Long id)
    {
        return customerServiceMapper.deleteCustomerServiceById(id);
    }
}
