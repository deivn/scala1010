package com.ymw.love.system.mapper.seeDoctor;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.entity.seeDoctor.input.RegistMangerInput;
import com.ymw.love.system.entity.seeDoctor.query.*;
import org.apache.ibatis.annotations.Param;

public interface RegistManagerMapper extends BaseMapper<PreSeeDoctorQueryResult> {

    /**
     * 待就诊列表查询
     * @param page
     * @return
     */
    IPage<PreSeeDoctorQueryResult> selectPreList(Page page, @Param("mangerInput")RegistMangerInput mangerInput);

    /**
     * 已就诊列表查询
     * @param page
     * @return
     */
    IPage<FinishSeeDoctorQueryResult> selectFinishList(Page page, @Param("mangerInput")RegistMangerInput mangerInput);

    /**
     * 过期列表查询
     * @param page
     * @return
     */
    IPage<BaseQuery> selectOutOfDateList(Page page, @Param("mangerInput")RegistMangerInput mangerInput);

    /**
     * 取消就诊列表查询
     * @param page
     * @return
     */
    IPage<CancelSeeDoctorQueryResult> selectCancelDateList(Page page, @Param("mangerInput")RegistMangerInput mangerInput);

    /**
     * 号源管理查询
     * @param page
     * @return
     */
    IPage<NumberQueryResult> selectNumbersList(Page page, @Param("mangerInput")RegistMangerInput mangerInput);
}
