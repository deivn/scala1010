package com.ymw.love.system.mapper.seeDoctor;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ymw.love.system.entity.seeDoctor.USeeDoctor;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
  * 就诊表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2019-08-17
 */
public interface USeeDoctorMapper extends BaseMapper<USeeDoctor> {


    Integer selectWeekday(@Param("dateTime") String dateTime);


}