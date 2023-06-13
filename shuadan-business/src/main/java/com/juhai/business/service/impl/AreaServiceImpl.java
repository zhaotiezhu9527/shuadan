package com.juhai.business.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juhai.business.domain.Goods;
import com.juhai.business.mapper.GoodsMapper;
import com.juhai.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.juhai.business.mapper.AreaMapper;
import com.juhai.business.domain.Area;
import com.juhai.business.service.IAreaService;

/**
 * 商品分类Service业务层处理
 * 
 * @author zhaotiezhu
 * @date 2023-06-12
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements IAreaService
{
    @Autowired
    private AreaMapper areaMapper;

    /**
     * 查询商品分类
     * 
     * @param id 商品分类主键
     * @return 商品分类
     */
    @Override
    public Area selectAreaById(Long id)
    {
        return areaMapper.selectAreaById(id);
    }

    /**
     * 查询商品分类列表
     * 
     * @param area 商品分类
     * @return 商品分类
     */
    @Override
    public List<Area> selectAreaList(Area area)
    {
        return areaMapper.selectAreaList(area);
    }

    /**
     * 新增商品分类
     * 
     * @param area 商品分类
     * @return 结果
     */
    @Override
    public int insertArea(Area area)
    {
        area.setCreateTime(DateUtils.getNowDate());
        return areaMapper.insertArea(area);
    }

    /**
     * 修改商品分类
     * 
     * @param area 商品分类
     * @return 结果
     */
    @Override
    public int updateArea(Area area)
    {
        area.setUpdateTime(DateUtils.getNowDate());
        return areaMapper.updateArea(area);
    }

    /**
     * 批量删除商品分类
     * 
     * @param ids 需要删除的商品分类主键
     * @return 结果
     */
    @Override
    public int deleteAreaByIds(Long[] ids)
    {
        return areaMapper.deleteAreaByIds(ids);
    }

    /**
     * 删除商品分类信息
     * 
     * @param id 商品分类主键
     * @return 结果
     */
    @Override
    public int deleteAreaById(Long id)
    {
        return areaMapper.deleteAreaById(id);
    }
}
