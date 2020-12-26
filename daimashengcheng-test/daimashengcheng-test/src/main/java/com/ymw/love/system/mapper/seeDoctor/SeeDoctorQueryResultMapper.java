package com.ymw.love.system.mapper.seeDoctor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.entity.seeDoctor.query.SeeDoctorQueryResult;
import org.apache.ibatis.annotations.Param;

/**
 * 就诊记录查询
 */
public interface SeeDoctorQueryResultMapper extends BaseMapper<SeeDoctorQueryResult> {

    IPage<SeeDoctorQueryResult> findRecordsByUid(Page page, @Param("uid") String uid);

    IPage<SeeDoctorQueryResult> findRecordsByType(Page page, @Param("uid") String uid, @Param("type") Integer type);

}
