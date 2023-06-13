package com.juhai.business.mapper;

import java.util.List;
import com.juhai.business.domain.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
/**
 * 账变列表Mapper接口
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
public interface AccountMapper extends BaseMapper<Account>
{
    /**
     * 查询账变列表
     * 
     * @param id 账变列表主键
     * @return 账变列表
     */
    public Account selectAccountById(Long id);

    /**
     * 查询账变列表列表
     * 
     * @param account 账变列表
     * @return 账变列表集合
     */
    public List<Account> selectAccountList(Account account);

    /**
     * 新增账变列表
     * 
     * @param account 账变列表
     * @return 结果
     */
    public int insertAccount(Account account);

    /**
     * 修改账变列表
     * 
     * @param account 账变列表
     * @return 结果
     */
    public int updateAccount(Account account);

    /**
     * 删除账变列表
     * 
     * @param id 账变列表主键
     * @return 结果
     */
    public int deleteAccountById(Long id);

    /**
     * 批量删除账变列表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAccountByIds(Long[] ids);
}
