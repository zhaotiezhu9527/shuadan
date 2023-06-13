package com.juhai.business.service.impl;

import java.util.List;
import com.juhai.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juhai.business.mapper.MessageTextMapper;
import com.juhai.business.domain.MessageText;
import com.juhai.business.service.IMessageTextService;

/**
 * 文本管理Service业务层处理
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
@Service
public class MessageTextServiceImpl extends ServiceImpl<MessageTextMapper, MessageText> implements IMessageTextService
{
    @Autowired
    private MessageTextMapper messageTextMapper;

    /**
     * 查询文本管理
     * 
     * @param id 文本管理主键
     * @return 文本管理
     */
    @Override
    public MessageText selectMessageTextById(Long id)
    {
        return messageTextMapper.selectMessageTextById(id);
    }

    /**
     * 查询文本管理列表
     * 
     * @param messageText 文本管理
     * @return 文本管理
     */
    @Override
    public List<MessageText> selectMessageTextList(MessageText messageText)
    {
        return messageTextMapper.selectMessageTextList(messageText);
    }

    /**
     * 新增文本管理
     * 
     * @param messageText 文本管理
     * @return 结果
     */
    @Override
    public int insertMessageText(MessageText messageText)
    {
        messageText.setCreateTime(DateUtils.getNowDate());
        return messageTextMapper.insertMessageText(messageText);
    }

    /**
     * 修改文本管理
     * 
     * @param messageText 文本管理
     * @return 结果
     */
    @Override
    public int updateMessageText(MessageText messageText)
    {
        messageText.setUpdateTime(DateUtils.getNowDate());
        return messageTextMapper.updateMessageText(messageText);
    }

    /**
     * 批量删除文本管理
     * 
     * @param ids 需要删除的文本管理主键
     * @return 结果
     */
    @Override
    public int deleteMessageTextByIds(Long[] ids)
    {
        return messageTextMapper.deleteMessageTextByIds(ids);
    }

    /**
     * 删除文本管理信息
     * 
     * @param id 文本管理主键
     * @return 结果
     */
    @Override
    public int deleteMessageTextById(Long id)
    {
        return messageTextMapper.deleteMessageTextById(id);
    }
}
