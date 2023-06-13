package com.juhai.business.service.impl;

import java.util.List;
import com.juhai.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juhai.business.mapper.NoticeMapper;
import com.juhai.business.domain.Notice;
import com.juhai.business.service.INoticeService;

/**
 * 公告管理Service业务层处理
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService
{
    @Autowired
    private NoticeMapper noticeMapper;

    /**
     * 查询公告管理
     * 
     * @param id 公告管理主键
     * @return 公告管理
     */
    @Override
    public Notice selectNoticeById(Long id)
    {
        return noticeMapper.selectNoticeById(id);
    }

    /**
     * 查询公告管理列表
     * 
     * @param notice 公告管理
     * @return 公告管理
     */
    @Override
    public List<Notice> selectNoticeList(Notice notice)
    {
        return noticeMapper.selectNoticeList(notice);
    }

    /**
     * 新增公告管理
     * 
     * @param notice 公告管理
     * @return 结果
     */
    @Override
    public int insertNotice(Notice notice)
    {
        notice.setCreateTime(DateUtils.getNowDate());
        return noticeMapper.insertNotice(notice);
    }

    /**
     * 修改公告管理
     * 
     * @param notice 公告管理
     * @return 结果
     */
    @Override
    public int updateNotice(Notice notice)
    {
        notice.setUpdateTime(DateUtils.getNowDate());
        return noticeMapper.updateNotice(notice);
    }

    /**
     * 批量删除公告管理
     * 
     * @param ids 需要删除的公告管理主键
     * @return 结果
     */
    @Override
    public int deleteNoticeByIds(Long[] ids)
    {
        return noticeMapper.deleteNoticeByIds(ids);
    }

    /**
     * 删除公告管理信息
     * 
     * @param id 公告管理主键
     * @return 结果
     */
    @Override
    public int deleteNoticeById(Long id)
    {
        return noticeMapper.deleteNoticeById(id);
    }
}
