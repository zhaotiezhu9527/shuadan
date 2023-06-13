package com.juhai.business.service;

import java.util.List;
import com.juhai.business.domain.PrepareDetail;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * 预派送详情列表Service接口
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
public interface IPrepareDetailService extends IService<PrepareDetail>
{
    /**
     * 查询预派送详情列表
     * 
     * @param id 预派送详情列表主键
     * @return 预派送详情列表
     */
    public PrepareDetail selectPrepareDetailById(Long id);

    /**
     * 查询预派送详情列表列表
     * 
     * @param prepareDetail 预派送详情列表
     * @return 预派送详情列表集合
     */
    public List<PrepareDetail> selectPrepareDetailList(PrepareDetail prepareDetail);

    /**
     * 新增预派送详情列表
     * 
     * @param prepareDetail 预派送详情列表
     * @return 结果
     */
    public int insertPrepareDetail(PrepareDetail prepareDetail);

    /**
     * 修改预派送详情列表
     * 
     * @param prepareDetail 预派送详情列表
     * @return 结果
     */
    public int updatePrepareDetail(PrepareDetail prepareDetail);

    /**
     * 批量删除预派送详情列表
     * 
     * @param ids 需要删除的预派送详情列表主键集合
     * @return 结果
     */
    public int deletePrepareDetailByIds(Long[] ids);

    /**
     * 删除预派送详情列表信息
     * 
     * @param id 预派送详情列表主键
     * @return 结果
     */
    public int deletePrepareDetailById(Long id);
}
