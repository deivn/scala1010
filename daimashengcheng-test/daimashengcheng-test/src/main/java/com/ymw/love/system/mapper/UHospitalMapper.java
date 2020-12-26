package com.ymw.love.system.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.entity.UHospital;
import com.ymw.love.system.vo.EvaluationQueryResultVO;

/**
 * <p>
  * 医院 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-08-15
 */
public interface UHospitalMapper extends BaseMapper<UHospital> {
  
	IPage<UHospital> findRecommend(Page page,@Param("lat") String lat, @Param("lng") String lng);
	
	/**
	 * 医院评价列表查询
	 * @param page
	 * @param startDate
	 * @param endDate
	 * @param hospitalId
	 * @return
	 */
	IPage<EvaluationQueryResultVO> queryEvaluatResultList(Page page,@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("hospitalId")String hospitalId);
	
}