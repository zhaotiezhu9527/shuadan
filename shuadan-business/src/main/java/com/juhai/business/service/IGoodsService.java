package com.juhai.business.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.juhai.business.domain.Goods;

/**
 * 商品列表Service接口
 * 
 * @author zhaotiezhu
 * @date 2023-06-12
 */
public interface IGoodsService extends IService<Goods>
{
    /**
     * 查询商品列表
     * 
     * @param id 商品列表主键
     * @return 商品列表
     */
    public Goods selectGoodsById(Long id);

    /**
     * 查询商品列表列表
     * 
     * @param goods 商品列表
     * @return 商品列表集合
     */
    public List<Goods> selectGoodsList(Goods goods);

    /**
     * 新增商品列表
     * 
     * @param goods 商品列表
     * @return 结果
     */
    public int insertGoods(Goods goods);

    /**
     * 修改商品列表
     * 
     * @param goods 商品列表
     * @return 结果
     */
    public int updateGoods(Goods goods);

    /**
     * 批量删除商品列表
     * 
     * @param ids 需要删除的商品列表主键集合
     * @return 结果
     */
    public int deleteGoodsByIds(Long[] ids);

    /**
     * 删除商品列表信息
     * 
     * @param id 商品列表主键
     * @return 结果
     */
    public int deleteGoodsById(Long id);
}
