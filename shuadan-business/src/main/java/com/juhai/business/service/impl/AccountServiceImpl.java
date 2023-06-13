package com.juhai.business.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.juhai.business.mapper.AccountMapper;
import com.juhai.business.domain.Account;
import com.juhai.business.service.IAccountService;

/**
 * 账变列表Service业务层处理
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService
{
    @Autowired
    private AccountMapper accountMapper;

    /**
     * 查询账变列表
     * 
     * @param id 账变列表主键
     * @return 账变列表
     */
    @Override
    public Account selectAccountById(Long id)
    {
        return accountMapper.selectAccountById(id);
    }

    /**
     * 查询账变列表列表
     * 
     * @param account 账变列表
     * @return 账变列表
     */
    @Override
    public List<Account> selectAccountList(Account account)
    {
        return accountMapper.selectAccountList(account);
    }

    /**
     * 新增账变列表
     * 
     * @param account 账变列表
     * @return 结果
     */
    @Override
    public int insertAccount(Account account)
    {
        return accountMapper.insertAccount(account);
    }

    /**
     * 修改账变列表
     * 
     * @param account 账变列表
     * @return 结果
     */
    @Override
    public int updateAccount(Account account)
    {
        return accountMapper.updateAccount(account);
    }

    /**
     * 批量删除账变列表
     * 
     * @param ids 需要删除的账变列表主键
     * @return 结果
     */
    @Override
    public int deleteAccountByIds(Long[] ids)
    {
        return accountMapper.deleteAccountByIds(ids);
    }

    /**
     * 删除账变列表信息
     * 
     * @param id 账变列表主键
     * @return 结果
     */
    @Override
    public int deleteAccountById(Long id)
    {
        return accountMapper.deleteAccountById(id);
    }
}
