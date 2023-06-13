package com.juhai.business.service.impl;

import java.util.List;
import com.juhai.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juhai.business.mapper.PrepareDetailMapper;
import com.juhai.business.domain.PrepareDetail;
import com.juhai.business.service.IPrepareDetailService;

/**
 * 预派送详情列表Service业务层处理
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
@Service
public class PrepareDetailServiceImpl extends ServiceImpl<PrepareDetailMapper, PrepareDetail> implements IPrepareDetailService
{
    @Autowired
    private PrepareDetailMapper prepareDetailMapper;

    /**
     * 查询预派送详情列表
     * 
     * @param id 预派送详情列表主键
     * @return 预派送详情列表
     */
    @Override
    public PrepareDetail selectPrepareDetailById(Long id)
    {
        return prepareDetailMapper.selectPrepareDetailById(id);
    }

    /**
     * 查询预派送详情列表列表
     * 
     * @param prepareDetail 预派送详情列表
     * @return 预派送详情列表
     */
    @Override
    public List<PrepareDetail> selectPrepareDetailList(PrepareDetail prepareDetail)
    {
        return prepareDetailMapper.selectPrepareDetailList(prepareDetail);
    }

    /**
     * 新增预派送详情列表
     * 
     * @param prepareDetail 预派送详情列表
     * @return 结果
     */
    @Override
    public int insertPrepareDetail(PrepareDetail prepareDetail)
    {
        prepareDetail.setCreateTime(DateUtils.getNowDate());
        return prepareDetailMapper.insertPrepareDetail(prepareDetail);
    }

    /**
     * 修改预派送详情列表
     * 
     * @param prepareDetail 预派送详情列表
     * @return 结果
     */
    @Override
    public int updatePrepareDetail(PrepareDetail prepareDetail)
    {
        prepareDetail.setUpdateTime(DateUtils.getNowDate());
        return prepareDetailMapper.updatePrepareDetail(prepareDetail);
    }

    /**
     * 批量删除预派送详情列表
     * 
     * @param ids 需要删除的预派送详情列表主键
     * @return 结果
     */
    @Override
    public int deletePrepareDetailByIds(Long[] ids)
    {
        return prepareDetailMapper.deletePrepareDetailByIds(ids);
    }

    /**
     * 删除预派送详情列表信息
     * 
     * @param id 预派送详情列表主键
     * @return 结果
     */
    @Override
    public int deletePrepareDetailById(Long id)
    {
        return prepareDetailMapper.deletePrepareDetailById(id);
    }
}
