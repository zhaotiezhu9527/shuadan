package com.juhai.business.service;

import java.util.List;
import com.juhai.business.domain.Deposit;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * 充值列表Service接口
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
public interface IDepositService extends IService<Deposit>
{
    /**
     * 查询充值列表
     * 
     * @param id 充值列表主键
     * @return 充值列表
     */
    public Deposit selectDepositById(Long id);

    /**
     * 查询充值列表列表
     * 
     * @param deposit 充值列表
     * @return 充值列表集合
     */
    public List<Deposit> selectDepositList(Deposit deposit);

    /**
     * 新增充值列表
     * 
     * @param deposit 充值列表
     * @return 结果
     */
    public int insertDeposit(Deposit deposit);

    /**
     * 修改充值列表
     * 
     * @param deposit 充值列表
     * @return 结果
     */
    public int updateDeposit(Deposit deposit);

    /**
     * 批量删除充值列表
     * 
     * @param ids 需要删除的充值列表主键集合
     * @return 结果
     */
    public int deleteDepositByIds(Long[] ids);

    /**
     * 删除充值列表信息
     * 
     * @param id 充值列表主键
     * @return 结果
     */
    public int deleteDepositById(Long id);
}
