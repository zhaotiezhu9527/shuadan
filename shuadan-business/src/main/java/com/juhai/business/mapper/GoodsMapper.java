package com.juhai.business.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.juhai.business.domain.Goods;
import com.juhai.business.domain.User;

/**
 * 商品列表Mapper接口
 * 
 * @author zhaotiezhu
 * @date 2023-06-12
 */
public interface GoodsMapper extends BaseMapper<Goods>
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
     * 删除商品列表
     * 
     * @param id 商品列表主键
     * @return 结果
     */
    public int deleteGoodsById(Long id);

    /**
     * 批量删除商品列表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsByIds(Long[] ids);
}
