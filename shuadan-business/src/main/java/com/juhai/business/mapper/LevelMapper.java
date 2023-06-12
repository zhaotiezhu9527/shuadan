package com.juhai.business.mapper;

import java.util.List;
import com.juhai.business.domain.Level;

/**
 * 等级列表Mapper接口
 * 
 * @author zhaotiezhu
 * @date 2023-06-12
 */
public interface LevelMapper 
{
    /**
     * 查询等级列表
     * 
     * @param id 等级列表主键
     * @return 等级列表
     */
    public Level selectLevelById(Long id);

    /**
     * 查询等级列表列表
     * 
     * @param level 等级列表
     * @return 等级列表集合
     */
    public List<Level> selectLevelList(Level level);

    /**
     * 新增等级列表
     * 
     * @param level 等级列表
     * @return 结果
     */
    public int insertLevel(Level level);

    /**
     * 修改等级列表
     * 
     * @param level 等级列表
     * @return 结果
     */
    public int updateLevel(Level level);

    /**
     * 删除等级列表
     * 
     * @param id 等级列表主键
     * @return 结果
     */
    public int deleteLevelById(Long id);

    /**
     * 批量删除等级列表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteLevelByIds(Long[] ids);
}
