package com.juhai.business.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.juhai.business.domain.Goods;
import com.juhai.business.domain.Level;

/**
 * 等级列表Service接口
 * 
 * @author zhaotiezhu
 * @date 2023-06-12
 */
public interface ILevelService extends IService<Level>
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
     * 批量删除等级列表
     * 
     * @param ids 需要删除的等级列表主键集合
     * @return 结果
     */
    public int deleteLevelByIds(Long[] ids);

    /**
     * 删除等级列表信息
     * 
     * @param id 等级列表主键
     * @return 结果
     */
    public int deleteLevelById(Long id);
}
