package com.ymw.love.system.mapper.faq;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ymw.love.system.entity.faq.FaqCollectEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * faq collection dao
 *
 * @author zengjuncheng
 * @version 1.1
 * @Creation time  2019/8/8
 */
public interface FaqCollectMapper extends BaseMapper<FaqCollectEntity> {


    /**
     * myCollectList
     *
     * @param id
     * @return IPage<FaqCollectEntity>
     */
    @Select("select a.id,a.issue_content as issueContent,c.add_time as addTime,(SELECT MAX(c.comment_content) FROM u_faq_comment AS c WHERE c.faq_id=a.id ORDER BY c.add_time DESC)AS replyContent" +
            ",(SELECT COUNT(c.id) FROM u_faq_comment AS c WHERE c.faq_id=a.id)AS replyCount" +
            " from u_faq_collect as c " +
            "left join u_faqs as a on c.faq_id=a.id where c.user_id=#{id} order by c.add_time desc")
    IPage<FaqCollectEntity> myCollectList(IPage<FaqCollectEntity> page, @Param("id") String id);
}
