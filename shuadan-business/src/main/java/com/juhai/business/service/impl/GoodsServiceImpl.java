package com.juhai.business.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juhai.business.domain.User;
import com.juhai.business.mapper.UserMapper;
import com.juhai.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.juhai.business.mapper.GoodsMapper;
import com.juhai.business.domain.Goods;
import com.juhai.business.service.IGoodsService;

/**
 * 商品列表Service业务层处理
 * 
 * @author zhaotiezhu
 * @date 2023-06-12
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService
{
    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 查询商品列表
     * 
     * @param id 商品列表主键
     * @return 商品列表
     */
    @Override
    public Goods selectGoodsById(Long id)
    {
        return goodsMapper.selectGoodsById(id);
    }

    /**
     * 查询商品列表列表
     * 
     * @param goods 商品列表
     * @return 商品列表
     */
    @Override
    public List<Goods> selectGoodsList(Goods goods)
    {
        return goodsMapper.selectGoodsList(goods);
    }

    /**
     * 新增商品列表
     * 
     * @param goods 商品列表
     * @return 结果
     */
    @Override
    public int insertGoods(Goods goods)
    {
        goods.setCreateTime(DateUtils.getNowDate());
        return goodsMapper.insertGoods(goods);
    }

    /**
     * 修改商品列表
     * 
     * @param goods 商品列表
     * @return 结果
     */
    @Override
    public int updateGoods(Goods goods)
    {
        goods.setUpdateTime(DateUtils.getNowDate());
        return goodsMapper.updateGoods(goods);
    }

    /**
     * 批量删除商品列表
     * 
     * @param ids 需要删除的商品列表主键
     * @return 结果
     */
    @Override
    public int deleteGoodsByIds(Long[] ids)
    {
        return goodsMapper.deleteGoodsByIds(ids);
    }

    /**
     * 删除商品列表信息
     * 
     * @param id 商品列表主键
     * @return 结果
     */
    @Override
    public int deleteGoodsById(Long id)
    {
        return goodsMapper.deleteGoodsById(id);
    }
}
