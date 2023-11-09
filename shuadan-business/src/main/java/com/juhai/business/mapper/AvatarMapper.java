package com.juhai.business.mapper;

import java.util.List;
import com.juhai.business.domain.Avatar;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
/**
 * 头像列表Mapper接口
 * 
 * @author zhaotiezhu
 * @date 2023-11-09
 */
public interface AvatarMapper extends BaseMapper<Avatar>
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
     * 删除头像列表
     * 
     * @param id 头像列表主键
     * @return 结果
     */
    public int deleteAvatarById(Long id);

    /**
     * 批量删除头像列表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAvatarByIds(Long[] ids);
}
