package com.ymw.love.system.mapper.advisory;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ymw.love.system.entity.advisory.AdvisoryCollect;
import com.ymw.love.system.entity.advisory.NewsAdvisoryEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * advisory collection dao
 *
 * @author zengjuncheng
 * @version 1.1
 * @Creation time  2019/8/8
 */
public interface AdvisoryCollectMapper extends BaseMapper<AdvisoryCollect> {

    /**
     * myCollectList
     *
     * @param id
     * @return IPage<NewsAdvisoryEntity>
     */
    @Select("SELECT\n" +
            "\ta.id,\n" +
            "\ta.news_title AS newsTitle,\n" +
            "\ta.news_describe AS newsDescribe,\n" +
            "\ta.browse_count AS browseCount,\n" +
            "\ta.news_img AS newsImg,\n" +
            "\ta.cover_img AS coverImg\n" +
            "FROM\n" +
            "\tu_news_advisory AS a\n" +
            "LEFT JOIN u_advisory_collect AS c ON c.advisory_id = a.id where c.user_id=#{id} order by c.add_time desc")
    IPage<NewsAdvisoryEntity> myCollectList(IPage<NewsAdvisoryEntity> page, @Param("id") String id);
}
