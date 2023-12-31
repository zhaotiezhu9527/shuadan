package com.juhai.business.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juhai.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.juhai.business.mapper.DayReportMapper;
import com.juhai.business.domain.DayReport;
import com.juhai.business.service.IDayReportService;

/**
 * 日报表Service业务层处理
 * 
 * @author zhaotiezhu
 * @date 2023-06-12
 */
@Service
public class DayReportServiceImpl extends ServiceImpl<DayReportMapper, DayReport> implements IDayReportService
{
    @Autowired
    private DayReportMapper dayReportMapper;

    /**
     * 查询日报表
     * 
     * @param id 日报表主键
     * @return 日报表
     */
    @Override
    public DayReport selectDayReportById(Long id)
    {
        return dayReportMapper.selectDayReportById(id);
    }

    /**
     * 查询日报表列表
     * 
     * @param dayReport 日报表
     * @return 日报表
     */
    @Override
    public List<DayReport> selectDayReportList(DayReport dayReport)
    {
        return dayReportMapper.selectDayReportList(dayReport);
    }

    /**
     * 新增日报表
     * 
     * @param dayReport 日报表
     * @return 结果
     */
    @Override
    public int insertDayReport(DayReport dayReport)
    {
        return dayReportMapper.insertDayReport(dayReport);
    }

    /**
     * 修改日报表
     * 
     * @param dayReport 日报表
     * @return 结果
     */
    @Override
    public int updateDayReport(DayReport dayReport)
    {
        return dayReportMapper.updateDayReport(dayReport);
    }

    /**
     * 批量删除日报表
     * 
     * @param ids 需要删除的日报表主键
     * @return 结果
     */
    @Override
    public int deleteDayReportByIds(Long[] ids)
    {
        return dayReportMapper.deleteDayReportByIds(ids);
    }

    /**
     * 删除日报表信息
     * 
     * @param id 日报表主键
     * @return 结果
     */
    @Override
    public int deleteDayReportById(Long id)
    {
        return dayReportMapper.deleteDayReportById(id);
    }

    @Override
    public void batchInsertOrUpdate(List<DayReport> list) throws Exception {
        int i = dayReportMapper.batchInsertOrUpdate(list);
        if (i <= 0) {
            throw new Exception("修改日报表失败");
        }
    }

    @Override
    public void insertOrUpdate(DayReport report) throws Exception {
        int i = dayReportMapper.insertOrUpdate(report);
        if (i <= 0) {
            throw new Exception("修改日报表失败");
        }
    }
}
