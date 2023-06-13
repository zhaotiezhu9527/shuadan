package com.juhai.business.mapper;

import java.util.List;
import com.juhai.business.domain.Deposit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
/**
 * 充值列表Mapper接口
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
public interface DepositMapper extends BaseMapper<Deposit>
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
     * 删除充值列表
     * 
     * @param id 充值列表主键
     * @return 结果
     */
    public int deleteDepositById(Long id);

    /**
     * 批量删除充值列表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDepositByIds(Long[] ids);
}
