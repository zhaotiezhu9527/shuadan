package com.juhai.business.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juhai.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.juhai.business.mapper.UserMapper;
import com.juhai.business.domain.User;
import com.juhai.business.service.IUserService;

/**
 * 会员列表Service业务层处理
 * 
 * @author zhaotiezhu
 * @date 2023-06-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService
{
    @Autowired
    private UserMapper userMapper;

    /**
     * 查询会员列表
     * 
     * @param id 会员列表主键
     * @return 会员列表
     */
    @Override
    public User selectUserById(Long id)
    {
        return userMapper.selectUserById(id);
    }

    /**
     * 查询会员列表列表
     * 
     * @param user 会员列表
     * @return 会员列表
     */
    @Override
    public List<User> selectUserList(User user)
    {
        return userMapper.selectUserList(user);
    }

    /**
     * 新增会员列表
     * 
     * @param user 会员列表
     * @return 结果
     */
    @Override
    public int insertUser(User user)
    {
        user.setCreateTime(DateUtils.getNowDate());
        return userMapper.insertUser(user);
    }

    /**
     * 修改会员列表
     * 
     * @param user 会员列表
     * @return 结果
     */
    @Override
    public int updateUser(User user)
    {
        user.setUpdateTime(DateUtils.getNowDate());
        return userMapper.updateUser(user);
    }

    /**
     * 批量删除会员列表
     * 
     * @param ids 需要删除的会员列表主键
     * @return 结果
     */
    @Override
    public int deleteUserByIds(Long[] ids)
    {
        return userMapper.deleteUserByIds(ids);
    }

    /**
     * 删除会员列表信息
     * 
     * @param id 会员列表主键
     * @return 结果
     */
    @Override
    public int deleteUserById(Long id)
    {
        return userMapper.deleteUserById(id);
    }

    @Override
    public User getUserByName(String userName) {
        return getOne(new LambdaQueryWrapper<User>().eq(User::getUserName, userName));
    }

    @Override
    public void updateUserBalance(String userName, BigDecimal balance) throws Exception {
        int updateUserBalance = userMapper.updateUserBalance(userName, balance);
        if (updateUserBalance <= 0) {
            throw new Exception("修改用户余额失败.");
        }
    }

    @Override
    public void batchUpdateReport(List<User> list) throws Exception {
        int i = userMapper.batchUpdateReport(list);
        if (i <= 0) {
            throw new Exception("修改日报表失败");
        }
    }
}
