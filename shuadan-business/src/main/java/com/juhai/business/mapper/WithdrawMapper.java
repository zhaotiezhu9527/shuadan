package com.juhai.business.mapper;

import java.util.List;
import com.juhai.business.domain.Withdraw;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
/**
 * 提现列表Mapper接口
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
public interface WithdrawMapper extends BaseMapper<Withdraw>
{
    /**
     * 查询提现列表
     * 
     * @param id 提现列表主键
     * @return 提现列表
     */
    public Withdraw selectWithdrawById(Long id);

    /**
     * 查询提现列表列表
     * 
     * @param withdraw 提现列表
     * @return 提现列表集合
     */
    public List<Withdraw> selectWithdrawList(Withdraw withdraw);

    /**
     * 新增提现列表
     * 
     * @param withdraw 提现列表
     * @return 结果
     */
    public int insertWithdraw(Withdraw withdraw);

    /**
     * 修改提现列表
     * 
     * @param withdraw 提现列表
     * @return 结果
     */
    public int updateWithdraw(Withdraw withdraw);

    /**
     * 删除提现列表
     * 
     * @param id 提现列表主键
     * @return 结果
     */
    public int deleteWithdrawById(Long id);

    /**
     * 批量删除提现列表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWithdrawByIds(Long[] ids);
}
