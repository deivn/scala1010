package com.ymw.love.system.mapper.message;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.entity.message.UMessageConfig;
import com.ymw.love.system.entity.message.query.MessageAdminQueryResult;
import com.ymw.love.system.entity.message.query.MessageQueryResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 消息配置表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-08-23
 */
public interface MessageManagerMapper extends BaseMapper<UMessageConfig> {

    /**
     * 待发送列表查询
     * @return
     */
    IPage<MessageQueryResult> queryPreSendList(Page page);

    /**
     * 发送后列表查询
     * @param page
     * @param startDate
     * @param endDate
     * @return
     */
    IPage<MessageQueryResult> querySentList(Page page, @Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 消息分类列表查询
     * @param page
     * @return
     */
    IPage<MessageAdminQueryResult> queryCategoryList(Page page);

}