package com.ymw.love.system.mapper.advisory;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.entity.advisory.AdvisoryCommentEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Advisory Comment dao
 *
 * @author zengjuncheng
 * @version 1.1
 * @Creation time  2019/8/2
 */
public interface AdvisoryCommentMapper extends BaseMapper<AdvisoryCommentEntity> {

    /**
     * findAdvisoryCommentList
     *
     * @param page
     * @param id
     * @return
     */
    IPage<Map<String, Object>> findAdvisoryCommentList(Page<List<Map<String, Object>>> page, @Param("id") String id, @Param("userId") String userId);

    /**
     * findAdvisoryCommentDetails
     *
     * @param id
     * @return
     */
    Map<String, Object> findAdvisoryCommentDetails(@Param("id") String id, @Param("userId") String userId);


    /**
     * findAdvisoryCommentZiJi
     *
     * @param id
     * @return
     */
    @Select("select c.id,c.comment_content AS commentContent,c.parent_id AS parentId," +
            "        DATE_FORMAT(c.add_time,'%Y-%m-%d') as addTime,u.nick_name AS nickName,u.image_url as imageUrl,c.user_id as userId" +
            "        from u_advisory_comment as c" +
            "        LEFT JOIN u_user as u ON c.user_id=u.id" +
            "        WHERE c.status=1 and c.parent_id=#{id}")
    List<Map<String, Object>> findAdvisoryCommentZiJi(@Param("id") String id);

    /**
     * select user Like Count
     *
     * @param id
     * @return
     */
    @Select("select count(id) from u_advisory_comment_give where user_id=#{id}")
    Integer findLikeCount(@Param("id") String id);
}
