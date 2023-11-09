package com.juhai.business.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juhai.business.mapper.AvatarMapper;
import com.juhai.business.domain.Avatar;
import com.juhai.business.service.IAvatarService;

/**
 * 头像列表Service业务层处理
 * 
 * @author zhaotiezhu
 * @date 2023-11-09
 */
@Service
public class AvatarServiceImpl extends ServiceImpl<AvatarMapper, Avatar> implements IAvatarService
{
    @Autowired
    private AvatarMapper avatarMapper;

    /**
     * 查询头像列表
     * 
     * @param id 头像列表主键
     * @return 头像列表
     */
    @Override
    public Avatar selectAvatarById(Long id)
    {
        return avatarMapper.selectAvatarById(id);
    }

    /**
     * 查询头像列表列表
     * 
     * @param avatar 头像列表
     * @return 头像列表
     */
    @Override
    public List<Avatar> selectAvatarList(Avatar avatar)
    {
        return avatarMapper.selectAvatarList(avatar);
    }

    /**
     * 新增头像列表
     * 
     * @param avatar 头像列表
     * @return 结果
     */
    @Override
    public int insertAvatar(Avatar avatar)
    {
        return avatarMapper.insertAvatar(avatar);
    }

    /**
     * 修改头像列表
     * 
     * @param avatar 头像列表
     * @return 结果
     */
    @Override
    public int updateAvatar(Avatar avatar)
    {
        return avatarMapper.updateAvatar(avatar);
    }

    /**
     * 批量删除头像列表
     * 
     * @param ids 需要删除的头像列表主键
     * @return 结果
     */
    @Override
    public int deleteAvatarByIds(Long[] ids)
    {
        return avatarMapper.deleteAvatarByIds(ids);
    }

    /**
     * 删除头像列表信息
     * 
     * @param id 头像列表主键
     * @return 结果
     */
    @Override
    public int deleteAvatarById(Long id)
    {
        return avatarMapper.deleteAvatarById(id);
    }
}
