package com.ymw.love.system.mapper.faq;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.entity.faq.FaqEntity;
import com.ymw.love.system.entity.faq.SpecialEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * faq dao
 *
 * @author zengjuncheng
 * @version 1.1
 * @Creation time  2019/8/2
 */
public interface FaqMapper extends BaseMapper<FaqEntity> {
    /**
     * find faq details
     *
     * @param faqEntity
     * @return Map<String               ,               Object>
     */
    Map<String, Object> findDetails(FaqEntity faqEntity);

    /**
     * q&a View record statistics
     *
     * @param id
     * @return Integer
     */
    @Update("UPDATE u_faqs SET browse_count=browse_count+1 WHERE id=#{id}")
    Integer updateViewCount(@Param("id") String id);

    /**
     * find faq Comment list
     *
     * @param id
     * @param page
     * @return List<Map                               <                               String                               ,                               Object>>
     */
    IPage<Map<String, Object>> findCommentList(Page<List<Map<String, Object>>> page, @Param("id") String id, @Param("userId") String userId);

    /**
     * my Q&a list
     *
     * @param id
     * @param page
     * @return List<FaqEntity>
     */
    @Select("SELECT f.id,f.add_time as addTime,f.issue_content,f.user_id as userId,(SELECT COUNT(c.id) FROM u_faq_comment AS c WHERE c.faq_id=f.id)AS replyCount,\n" +
            "(SELECT MAX(c.comment_content) FROM u_faq_comment AS c WHERE c.faq_id=f.id ORDER BY c.add_time DESC)AS replyContent\n" +
            "FROM u_faqs AS f WHERE f.user_id=#{id}")
    IPage<FaqEntity> findMyFaqList(Page<FaqEntity> page, @Param("id") String id);

    /**
     * my q&a answer list
     *
     * @param id
     * @param page
     * @return List<FaqEntity>
     */
    @Select("SELECT f.id,f.add_time as addTime,c.comment_content AS replyContent,c.user_id as userId,f.issue_content AS issueContent,\n" +
            "(SELECT COUNT(c.id) FROM u_faq_comment AS c WHERE c.faq_id=f.id)AS replyCount\n" +
            "FROM u_faq_comment AS c\n" +
            "LEFT JOIN u_faqs as f ON c.faq_id=f.id\n" +
            "WHERE c.user_id=#{id} ORDER BY c.add_time DESC")
    IPage<FaqEntity> findMyFaqAnswerList(Page<FaqEntity> page, @Param("id") String id);

    /**
     * find faq Comment SubLevel list
     *
     * @param id
     * @return List<Map                               <                               String                               ,                               Object>>
     */
    List<Map<String, Object>> findCommentSubLevelList(@Param("id") String id);

    /**
     * find faq Comment list
     *
     * @param id
     * @return List<Map                               <                               String                               ,                               Object>>
     */
    Map<String, Object> findCommentById(@Param("id") String id, @Param("userId") String userId);

    /**
     * q&a Comment Like
     *
     * @param id
     * @return Integer
     */
    @Select("select count(id) from u_faq_comment_give WHERE user_id=#{id}")
    Integer findLikeCount(@Param("id") String id);


    IPage<FaqEntity> selectFaqPageList(Page<FaqEntity> page, @Param("faqEntity") FaqEntity faqEntity);

    IPage<FaqEntity> findSpecialDetailsList(IPage<FaqEntity> page,@Param("faqEntity") FaqEntity faqEntity);

    IPage<SpecialEntity> selectFaqSpecialList(Page<SpecialEntity> page, @Param("special") SpecialEntity specialEntity);

    IPage<SpecialEntity> findSpecial(IPage<SpecialEntity> page,@Param("special") SpecialEntity specialEntity);

    IPage<SpecialEntity> findSpecialDetail(IPage<SpecialEntity> page,@Param("special") SpecialEntity specialEntity);

    IPage<FaqEntity> findSysFaqList(IPage<FaqEntity> page,@Param("faq") FaqEntity faqEntity);

    Integer findFaqCount(@Param("parentId") String parentId);
}
