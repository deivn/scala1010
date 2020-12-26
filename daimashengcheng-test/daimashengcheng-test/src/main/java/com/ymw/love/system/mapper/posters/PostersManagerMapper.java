package com.ymw.love.system.mapper.posters;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.entity.posters.query.PostersQueryResult;
import org.apache.ibatis.annotations.Param;

public interface PostersManagerMapper extends BaseMapper<PostersQueryResult> {

    /**
     * 海报列表查询
     * @param page
     * @param stateDate
     * @param endDate
     * @return
     */
    IPage<PostersQueryResult> queryPostersList(Page page, @Param("startDate") String stateDate, @Param("endDate") String endDate);
}
