package com.juhai.business.service;

import java.util.List;
import com.juhai.business.domain.Prepare;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * 预派送列表Service接口
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
public interface IPrepareService extends IService<Prepare>
{
    /**
     * 查询预派送列表
     * 
     * @param id 预派送列表主键
     * @return 预派送列表
     */
    public Prepare selectPrepareById(Long id);

    /**
     * 查询预派送列表列表
     * 
     * @param prepare 预派送列表
     * @return 预派送列表集合
     */
    public List<Prepare> selectPrepareList(Prepare prepare);

    /**
     * 新增预派送列表
     * 
     * @param prepare 预派送列表
     * @return 结果
     */
    public int insertPrepare(Prepare prepare);

    /**
     * 修改预派送列表
     * 
     * @param prepare 预派送列表
     * @return 结果
     */
    public int updatePrepare(Prepare prepare);

    /**
     * 批量删除预派送列表
     * 
     * @param ids 需要删除的预派送列表主键集合
     * @return 结果
     */
    public int deletePrepareByIds(Long[] ids);

    /**
     * 删除预派送列表信息
     * 
     * @param id 预派送列表主键
     * @return 结果
     */
    public int deletePrepareById(Long id);
}
