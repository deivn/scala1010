package com.ymw.love.system.mapper.test;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ymw.love.system.dto.test.TestResultDTO;
import com.ymw.love.system.entity.test.TestEntity;
import org.apache.ibatis.annotations.Param;

public interface TestMapper extends BaseMapper<TestEntity> {

    TestResultDTO findName(@Param("id") Integer id);
}
