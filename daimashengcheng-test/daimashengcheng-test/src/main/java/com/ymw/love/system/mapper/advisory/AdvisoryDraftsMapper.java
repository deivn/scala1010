package com.ymw.love.system.mapper.advisory;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ymw.love.system.entity.advisory.AdvisoryCollect;
import com.ymw.love.system.entity.advisory.AdvisoryDraftsEntity;
import com.ymw.love.system.entity.advisory.NewsAdvisoryEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * advisory Drafts dao
 *
 * @author zengjuncheng
 * @version 1.1
 * @Creation time  2019/8/8
 */
public interface AdvisoryDraftsMapper extends BaseMapper<AdvisoryDraftsEntity> {

}
