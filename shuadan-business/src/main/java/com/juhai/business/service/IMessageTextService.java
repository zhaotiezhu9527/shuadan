package com.juhai.business.service;

import java.util.List;
import com.juhai.business.domain.MessageText;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * 文本管理Service接口
 * 
 * @author zhaotiezhu
 * @date 2023-06-13
 */
public interface IMessageTextService extends IService<MessageText>
{
    /**
     * 查询文本管理
     * 
     * @param id 文本管理主键
     * @return 文本管理
     */
    public MessageText selectMessageTextById(Long id);

    /**
     * 查询文本管理列表
     * 
     * @param messageText 文本管理
     * @return 文本管理集合
     */
    public List<MessageText> selectMessageTextList(MessageText messageText);

    /**
     * 新增文本管理
     * 
     * @param messageText 文本管理
     * @return 结果
     */
    public int insertMessageText(MessageText messageText);

    /**
     * 修改文本管理
     * 
     * @param messageText 文本管理
     * @return 结果
     */
    public int updateMessageText(MessageText messageText);

    /**
     * 批量删除文本管理
     * 
     * @param ids 需要删除的文本管理主键集合
     * @return 结果
     */
    public int deleteMessageTextByIds(Long[] ids);

    /**
     * 删除文本管理信息
     * 
     * @param id 文本管理主键
     * @return 结果
     */
    public int deleteMessageTextById(Long id);
}
