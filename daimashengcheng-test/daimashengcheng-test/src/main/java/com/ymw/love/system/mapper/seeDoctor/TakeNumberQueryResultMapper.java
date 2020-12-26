package com.ymw.love.system.mapper.seeDoctor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.entity.seeDoctor.query.TakeNumberQueryResult;
import org.apache.ibatis.annotations.Param;

public interface TakeNumberQueryResultMapper extends BaseMapper<TakeNumberQueryResult> {

    IPage<TakeNumberQueryResult> findNumbersByHospitalId(Page page, @Param("hospitalId") String hospitalId);
}
