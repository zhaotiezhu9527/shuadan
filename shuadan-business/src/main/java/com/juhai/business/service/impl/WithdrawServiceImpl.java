package com.juhai.business.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.juhai.business.mapper.WithdrawMapper;
import com.juhai.business.domain.Withdraw;
import com.juhai.business.service.IWithdrawService;

/**
 * 提现列表Service业务层处理
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
@Service
public class WithdrawServiceImpl extends ServiceImpl<WithdrawMapper, Withdraw> implements IWithdrawService
{
    @Autowired
    private WithdrawMapper withdrawMapper;

    /**
     * 查询提现列表
     * 
     * @param id 提现列表主键
     * @return 提现列表
     */
    @Override
    public Withdraw selectWithdrawById(Long id)
    {
        return withdrawMapper.selectWithdrawById(id);
    }

    /**
     * 查询提现列表列表
     * 
     * @param withdraw 提现列表
     * @return 提现列表
     */
    @Override
    public List<Withdraw> selectWithdrawList(Withdraw withdraw)
    {
        return withdrawMapper.selectWithdrawList(withdraw);
    }

    /**
     * 新增提现列表
     * 
     * @param withdraw 提现列表
     * @return 结果
     */
    @Override
    public int insertWithdraw(Withdraw withdraw)
    {
        return withdrawMapper.insertWithdraw(withdraw);
    }

    /**
     * 修改提现列表
     * 
     * @param withdraw 提现列表
     * @return 结果
     */
    @Override
    public int updateWithdraw(Withdraw withdraw)
    {
        return withdrawMapper.updateWithdraw(withdraw);
    }

    /**
     * 批量删除提现列表
     * 
     * @param ids 需要删除的提现列表主键
     * @return 结果
     */
    @Override
    public int deleteWithdrawByIds(Long[] ids)
    {
        return withdrawMapper.deleteWithdrawByIds(ids);
    }

    /**
     * 删除提现列表信息
     * 
     * @param id 提现列表主键
     * @return 结果
     */
    @Override
    public int deleteWithdrawById(Long id)
    {
        return withdrawMapper.deleteWithdrawById(id);
    }
}
