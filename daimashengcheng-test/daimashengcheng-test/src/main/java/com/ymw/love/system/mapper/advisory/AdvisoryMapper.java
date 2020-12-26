package com.ymw.love.system.mapper.advisory;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ymw.love.system.entity.advisory.AdvisoryCommentEntity;
import com.ymw.love.system.entity.advisory.AdvisoryDraftsEntity;
import com.ymw.love.system.entity.advisory.NewsAdvisoryEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * advisory dao
 *
 * @author zengjuncheng
 * @version 1.1
 * @Creation time  2019/8/8
 */
public interface AdvisoryMapper extends BaseMapper<NewsAdvisoryEntity> {

    /**
     * advisory add browse count
     *
     * @param id
     * @return Integer
     */
    @Update("UPDATE u_news_advisory SET browse_count=browse_count+1 WHERE id=#{id}")
    Integer updateViewCount(@Param("id") String id);

    /**
     * find advisory details
     *
     * @param advisoryEntity
     * @return Map<String                               ,                               Object>
     */
    Map<String, Object> findDetails(NewsAdvisoryEntity advisoryEntity);

//    IPage<NewsAdvisoryEntity> selectPages(IPage<NewsAdvisoryEntity> page, @Param("advisoryEntity") NewsAdvisoryEntity advisoryEntity);


    /**
     *  findSysAdvisoryAuditList
     *
     * @param advisoryEntity
     * @return IPage<NewsAdvisoryEntity>
     */
    IPage<NewsAdvisoryEntity> findSysAdvisoryAuditList(IPage<NewsAdvisoryEntity> page, @Param("advisoryEntity") NewsAdvisoryEntity advisoryEntity);
    /**
     *  Consulting details
     *
     * @param advisoryEntity
     * @return IPage<NewsAdvisoryEntity>
     */
    IPage<NewsAdvisoryEntity> findSysAdvisoryConsultingList(IPage<NewsAdvisoryEntity> page, @Param("advisoryEntity") NewsAdvisoryEntity advisoryEntity);

    /**
     *  sys advisory Refused list
     *
     * @param advisoryEntity
     * @return IPage<NewsAdvisoryEntity>
     */
    IPage<NewsAdvisoryEntity> findAdvisoryRefusedList(IPage<NewsAdvisoryEntity> page, @Param("advisoryEntity") NewsAdvisoryEntity advisoryEntity);


    /**
     * sys advisory audit
     * @param auditReason
     * @param auditStatus
     * @param ids
     * @return Integer
     */
    Integer updateSysAdvisoryAudit(@Param("auditReason") String auditReason, @Param("auditStatus") Integer auditStatus, @Param("author") String author,@Param("ids") String[] ids);

    /**
     *  comments the list
     *
     * @param advisoryEntity
     * @return IPage<AdvisoryCommentEntity>
     */
    IPage<AdvisoryCommentEntity> findAdvisoryCommentsList(IPage<AdvisoryCommentEntity> page, @Param("advisoryEntity") AdvisoryCommentEntity advisoryEntity);

    /**
     *  advisory drafts list
     *
     * @param advisoryEntity
     * @return IPage<AdvisoryDraftsEntity>
     */
    IPage<AdvisoryDraftsEntity> findAdvisoryDraftsList(IPage<AdvisoryDraftsEntity> page, @Param("advisoryEntity") AdvisoryDraftsEntity advisoryEntity);
}
