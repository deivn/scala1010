package com.ymw.love.system.mapper.message;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.entity.message.UMessagePush;
import com.ymw.love.system.entity.message.query.MessageCountQueryResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 消息推送记录表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-08-24
 */
public interface UMessagePushMapper extends BaseMapper<UMessagePush> {

    /**
     * 全部消息的查询
     * @param page
     * @param destUid
     * @return
     */
    IPage<UMessagePush> queryMessagesByUid(Page page, @Param("destUid") String destUid);

    /**
     * 按分类统计消息未读数
     * @return
     */
    List<MessageCountQueryResult> messageUnreadCount(@Param("destUid") String destUid);

    /**
     * 按分类查询对应的消息列表
     * @param page
     * @param type
     * @return
     */
    IPage<UMessagePush> queryMessagesByType(Page page, @Param("destUid") String destUid, @Param("type") Integer type);

    /**
     * 批量删除消息
     * @param ids
     */
    void deleteMessagesByIds(@Param("ids")String[] ids, @Param("updateTime")String updateTime, @Param("uid") String uid);

    /**
     * 批量改未读为已读
     * @param ids
     */
    void updateUnreadsByIds(@Param("ids")String[] ids, @Param("updateTime")String updateTime, @Param("uid") String uid);

}