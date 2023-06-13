package com.juhai.business.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.juhai.business.domain.Area;
import com.juhai.business.domain.Goods;

/**
 * 商品分类Service接口
 * 
 * @author zhaotiezhu
 * @date 2023-06-12
 */
public interface IAreaService extends IService<Area>
{
    /**
     * 查询商品分类
     * 
     * @param id 商品分类主键
     * @return 商品分类
     */
    public Area selectAreaById(Long id);

    /**
     * 查询商品分类列表
     * 
     * @param area 商品分类
     * @return 商品分类集合
     */
    public List<Area> selectAreaList(Area area);

    /**
     * 新增商品分类
     * 
     * @param area 商品分类
     * @return 结果
     */
    public int insertArea(Area area);

    /**
     * 修改商品分类
     * 
     * @param area 商品分类
     * @return 结果
     */
    public int updateArea(Area area);

    /**
     * 批量删除商品分类
     * 
     * @param ids 需要删除的商品分类主键集合
     * @return 结果
     */
    public int deleteAreaByIds(Long[] ids);

    /**
     * 删除商品分类信息
     * 
     * @param id 商品分类主键
     * @return 结果
     */
    public int deleteAreaById(Long id);
}
