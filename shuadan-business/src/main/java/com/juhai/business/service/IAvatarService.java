package com.juhai.business.service;

import java.util.List;
import com.juhai.business.domain.Avatar;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * 头像列表Service接口
 * 
 * @author zhaotiezhu
 * @date 2023-11-09
 */
public interface IAvatarService extends IService<Avatar>
{
    /**
     * 查询头像列表
     * 
     * @param id 头像列表主键
     * @return 头像列表
     */
    public Avatar selectAvatarById(Long id);

    /**
     * 查询头像列表列表
     * 
     * @param avatar 头像列表
     * @return 头像列表集合
     */
    public List<Avatar> selectAvatarList(Avatar avatar);

    /**
     * 新增头像列表
     * 
     * @param avatar 头像列表
     * @return 结果
     */
    public int insertAvatar(Avatar avatar);

    /**
     * 修改头像列表
     * 
     * @param avatar 头像列表
     * @return 结果
     */
    public int updateAvatar(Avatar avatar);

    /**
     * 批量删除头像列表
     * 
     * @param ids 需要删除的头像列表主键集合
     * @return 结果
     */
    public int deleteAvatarByIds(Long[] ids);

    /**
     * 删除头像列表信息
     * 
     * @param id 头像列表主键
     * @return 结果
     */
    public int deleteAvatarById(Long id);
}
