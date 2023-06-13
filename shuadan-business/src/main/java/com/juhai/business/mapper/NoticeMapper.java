package com.juhai.business.mapper;

import java.util.List;
import com.juhai.business.domain.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
/**
 * 公告管理Mapper接口
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
public interface NoticeMapper extends BaseMapper<Notice>
{
    /**
     * 查询公告管理
     * 
     * @param id 公告管理主键
     * @return 公告管理
     */
    public Notice selectNoticeById(Long id);

    /**
     * 查询公告管理列表
     * 
     * @param notice 公告管理
     * @return 公告管理集合
     */
    public List<Notice> selectNoticeList(Notice notice);

    /**
     * 新增公告管理
     * 
     * @param notice 公告管理
     * @return 结果
     */
    public int insertNotice(Notice notice);

    /**
     * 修改公告管理
     * 
     * @param notice 公告管理
     * @return 结果
     */
    public int updateNotice(Notice notice);

    /**
     * 删除公告管理
     * 
     * @param id 公告管理主键
     * @return 结果
     */
    public int deleteNoticeById(Long id);

    /**
     * 批量删除公告管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteNoticeByIds(Long[] ids);
}
