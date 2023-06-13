package com.juhai.business.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juhai.business.domain.User;
import org.apache.ibatis.annotations.Param;

/**
 * 会员列表Mapper接口
 * 
 * @author zhaotiezhu
 * @date 2023-06-12
 */
public interface UserMapper extends BaseMapper<User>
{
    /**
     * 查询会员列表
     * 
     * @param id 会员列表主键
     * @return 会员列表
     */
    public User selectUserById(Long id);

    /**
     * 查询会员列表列表
     * 
     * @param user 会员列表
     * @return 会员列表集合
     */
    public List<User> selectUserList(User user);

    /**
     * 新增会员列表
     * 
     * @param user 会员列表
     * @return 结果
     */
    public int insertUser(User user);

    /**
     * 修改会员列表
     * 
     * @param user 会员列表
     * @return 结果
     */
    public int updateUser(User user);

    /**
     * 删除会员列表
     * 
     * @param id 会员列表主键
     * @return 结果
     */
    public int deleteUserById(Long id);

    /**
     * 批量删除会员列表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserByIds(Long[] ids);

    /**
     * 修改用户余额
     * @param userName
     * @param balance
     * @return
     */
    int updateUserBalance(@Param("userName") String userName, @Param("balance") BigDecimal balance);


    int batchUpdateReport(List<User> list);
}
