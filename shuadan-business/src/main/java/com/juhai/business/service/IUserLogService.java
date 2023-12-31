package com.juhai.business.service;

import com.juhai.business.domain.UserLog;

import java.util.List;

/**
 * 【请填写功能名称】Service接口
 * 
 * @author zhaotiezhu
 * @date 2023-05-21
 */
public interface IUserLogService 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public UserLog selectUserLogById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param userLog 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<UserLog> selectUserLogList(UserLog userLog);

    /**
     * 新增【请填写功能名称】
     * 
     * @param userLog 【请填写功能名称】
     * @return 结果
     */
    public int insertUserLog(UserLog userLog);

    /**
     * 修改【请填写功能名称】
     * 
     * @param userLog 【请填写功能名称】
     * @return 结果
     */
    public int updateUserLog(UserLog userLog);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】主键集合
     * @return 结果
     */
    public int deleteUserLogByIds(Long[] ids);

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteUserLogById(Long id);
}
