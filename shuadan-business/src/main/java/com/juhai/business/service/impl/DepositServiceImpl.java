package com.juhai.business.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.juhai.business.mapper.DepositMapper;
import com.juhai.business.domain.Deposit;
import com.juhai.business.service.IDepositService;

/**
 * 充值列表Service业务层处理
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
@Service
public class DepositServiceImpl extends ServiceImpl<DepositMapper, Deposit> implements IDepositService
{
    @Autowired
    private DepositMapper depositMapper;

    /**
     * 查询充值列表
     * 
     * @param id 充值列表主键
     * @return 充值列表
     */
    @Override
    public Deposit selectDepositById(Long id)
    {
        return depositMapper.selectDepositById(id);
    }

    /**
     * 查询充值列表列表
     * 
     * @param deposit 充值列表
     * @return 充值列表
     */
    @Override
    public List<Deposit> selectDepositList(Deposit deposit)
    {
        return depositMapper.selectDepositList(deposit);
    }

    /**
     * 新增充值列表
     * 
     * @param deposit 充值列表
     * @return 结果
     */
    @Override
    public int insertDeposit(Deposit deposit)
    {
        return depositMapper.insertDeposit(deposit);
    }

    /**
     * 修改充值列表
     * 
     * @param deposit 充值列表
     * @return 结果
     */
    @Override
    public int updateDeposit(Deposit deposit)
    {
        return depositMapper.updateDeposit(deposit);
    }

    /**
     * 批量删除充值列表
     * 
     * @param ids 需要删除的充值列表主键
     * @return 结果
     */
    @Override
    public int deleteDepositByIds(Long[] ids)
    {
        return depositMapper.deleteDepositByIds(ids);
    }

    /**
     * 删除充值列表信息
     * 
     * @param id 充值列表主键
     * @return 结果
     */
    @Override
    public int deleteDepositById(Long id)
    {
        return depositMapper.deleteDepositById(id);
    }
}
