package com.juhai.business.service.impl;

import java.util.List;
import com.juhai.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juhai.business.mapper.PrepareMapper;
import com.juhai.business.domain.Prepare;
import com.juhai.business.service.IPrepareService;

/**
 * 预派送列表Service业务层处理
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
@Service
public class PrepareServiceImpl extends ServiceImpl<PrepareMapper, Prepare> implements IPrepareService
{
    @Autowired
    private PrepareMapper prepareMapper;

    /**
     * 查询预派送列表
     * 
     * @param id 预派送列表主键
     * @return 预派送列表
     */
    @Override
    public Prepare selectPrepareById(Long id)
    {
        return prepareMapper.selectPrepareById(id);
    }

    /**
     * 查询预派送列表列表
     * 
     * @param prepare 预派送列表
     * @return 预派送列表
     */
    @Override
    public List<Prepare> selectPrepareList(Prepare prepare)
    {
        return prepareMapper.selectPrepareList(prepare);
    }

    /**
     * 新增预派送列表
     * 
     * @param prepare 预派送列表
     * @return 结果
     */
    @Override
    public int insertPrepare(Prepare prepare)
    {
        prepare.setCreateTime(DateUtils.getNowDate());
        return prepareMapper.insertPrepare(prepare);
    }

    /**
     * 修改预派送列表
     * 
     * @param prepare 预派送列表
     * @return 结果
     */
    @Override
    public int updatePrepare(Prepare prepare)
    {
        prepare.setUpdateTime(DateUtils.getNowDate());
        return prepareMapper.updatePrepare(prepare);
    }

    /**
     * 批量删除预派送列表
     * 
     * @param ids 需要删除的预派送列表主键
     * @return 结果
     */
    @Override
    public int deletePrepareByIds(Long[] ids)
    {
        return prepareMapper.deletePrepareByIds(ids);
    }

    /**
     * 删除预派送列表信息
     * 
     * @param id 预派送列表主键
     * @return 结果
     */
    @Override
    public int deletePrepareById(Long id)
    {
        return prepareMapper.deletePrepareById(id);
    }
}
