package com.juhai.business.service;

import java.util.List;
import com.juhai.business.domain.User;

/**
 * 会员列表Service接口
 * 
 * @author zhaotiezhu
 * @date 2023-06-12
 */
public interface IUserService 
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
     * 批量删除会员列表
     * 
     * @param ids 需要删除的会员列表主键集合
     * @return 结果
     */
    public int deleteUserByIds(Long[] ids);

    /**
     * 删除会员列表信息
     * 
     * @param id 会员列表主键
     * @return 结果
     */
    public int deleteUserById(Long id);
}
