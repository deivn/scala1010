package com.ymw.love.system.mapper.seeDoctor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.entity.seeDoctor.UPatientEvaluation;
import com.ymw.love.system.entity.seeDoctor.query.EvaluationQueryResult;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
  * 患者就诊评价表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-08-17
 */
public interface UPatientEvaluationMapper extends BaseMapper<UPatientEvaluation> {

    IPage<EvaluationQueryResult> selectPatientEvaluations(Page page, @Param("hospitalId") String hospitalId);

}