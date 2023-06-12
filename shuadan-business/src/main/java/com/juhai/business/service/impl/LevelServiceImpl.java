package com.juhai.business.service.impl;

import java.util.List;
import com.juhai.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.juhai.business.mapper.LevelMapper;
import com.juhai.business.domain.Level;
import com.juhai.business.service.ILevelService;

/**
 * 等级列表Service业务层处理
 * 
 * @author zhaotiezhu
 * @date 2023-06-12
 */
@Service
public class LevelServiceImpl implements ILevelService 
{
    @Autowired
    private LevelMapper levelMapper;

    /**
     * 查询等级列表
     * 
     * @param id 等级列表主键
     * @return 等级列表
     */
    @Override
    public Level selectLevelById(Long id)
    {
        return levelMapper.selectLevelById(id);
    }

    /**
     * 查询等级列表列表
     * 
     * @param level 等级列表
     * @return 等级列表
     */
    @Override
    public List<Level> selectLevelList(Level level)
    {
        return levelMapper.selectLevelList(level);
    }

    /**
     * 新增等级列表
     * 
     * @param level 等级列表
     * @return 结果
     */
    @Override
    public int insertLevel(Level level)
    {
        level.setCreateTime(DateUtils.getNowDate());
        return levelMapper.insertLevel(level);
    }

    /**
     * 修改等级列表
     * 
     * @param level 等级列表
     * @return 结果
     */
    @Override
    public int updateLevel(Level level)
    {
        level.setUpdateTime(DateUtils.getNowDate());
        return levelMapper.updateLevel(level);
    }

    /**
     * 批量删除等级列表
     * 
     * @param ids 需要删除的等级列表主键
     * @return 结果
     */
    @Override
    public int deleteLevelByIds(Long[] ids)
    {
        return levelMapper.deleteLevelByIds(ids);
    }

    /**
     * 删除等级列表信息
     * 
     * @param id 等级列表主键
     * @return 结果
     */
    @Override
    public int deleteLevelById(Long id)
    {
        return levelMapper.deleteLevelById(id);
    }
}
