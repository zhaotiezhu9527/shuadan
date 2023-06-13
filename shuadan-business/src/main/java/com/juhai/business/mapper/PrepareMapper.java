package com.juhai.business.mapper;

import java.util.List;
import com.juhai.business.domain.Prepare;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
/**
 * 预派送列表Mapper接口
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
public interface PrepareMapper extends BaseMapper<Prepare>
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
     * 删除预派送列表
     * 
     * @param id 预派送列表主键
     * @return 结果
     */
    public int deletePrepareById(Long id);

    /**
     * 批量删除预派送列表
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePrepareByIds(Long[] ids);
}
