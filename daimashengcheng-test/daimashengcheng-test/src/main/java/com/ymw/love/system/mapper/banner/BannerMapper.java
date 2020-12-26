package com.ymw.love.system.mapper.banner;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ymw.love.system.entity.banner.BannerEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * banner dao
 * @author zjc
 * @version 1.1
 * @date 2019-08-16
 */
public interface BannerMapper extends BaseMapper<BannerEntity> {

    /**
     * add  Banner Click Record
     * @param id
     * @return Integer
     */
    @Update("UPDATE sys_banner SET click_count=click_count+1 WHERE id=#{id}")
    Integer insClickRecordBanner(@Param("id") String id);
}
