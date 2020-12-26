package com.ymw.love.system.service.test.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.common.SystemEnum;
import com.ymw.love.system.dto.test.TestDTO;
import com.ymw.love.system.dto.test.TestQuery;
import com.ymw.love.system.dto.test.TestRequest;
import com.ymw.love.system.entity.test.TestEntity;
import com.ymw.love.system.service.BaseService;
import com.ymw.love.system.vo.PageVO;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class TestServiceImpl extends BaseService {

   
    public Result insTestRecord(TestRequest req) throws Exception {
        TestEntity te = new TestEntity();
        te.setAge(req.getAge());
        te.setName(req.getName());
        te.setSex(req.getSex());
        te.buildTime();
        te.insert();
        return new Result().setData(te.getName());
    }

    
    public Result updateRecord(String name, Integer sex, Integer age, Integer id) throws Exception {
        TestEntity te = mf.getTestMapper().selectById(id);
        if(te == null) {
            throw new Exception("查询不到记录.");
        }
        if(StringUtils.isNotBlank(name))
            te.setName(name);
        if(sex != null) {
            te.setSex(sex);
        }
        if(age != null) {
            te.setAge(age);
        }
        te.buildUpdateTime();
        te.updateById();
        return new Result();
    }

   
    public Result delByNameRecord(String name) throws Exception {
        TestEntity te = mf.getTestMapper().selectOne(new QueryWrapper<TestEntity>().eq("name", name));
        if(te == null) {
            throw new Exception("删除的记录不存在！");
        }
        return new Result().setCode(te.deleteById() ? SystemEnum.SUCCESS : SystemEnum.FAIL);
    }

   
    public Result<TestDTO> getByName(String name) throws Exception {
        TestEntity te = mf.getTestMapper().selectOne(new QueryWrapper<TestEntity>().eq("name", name));
        TestDTO t = new TestDTO();
        t.setId(te.getId());
        t.setName(te.getName());
        t.setSex(te.getSex() == 1 ? "男" : "女");
        t.setAge(te.getAge());
        t.setCreateTime(te.getCreateTime());
        t.setUpdateTime(te.getUpdateTime());
        return new Result(t);
    }

    public Result<PageVO<TestDTO>> getLists(TestQuery testQuest) throws Exception {
        IPage<TestEntity> page = new Page<>(testQuest.getPageNum(), testQuest.getPageSize());
        TestEntity te = new TestEntity();
        if(StringUtils.isNotBlank(testQuest.getName()))
            te.setName(testQuest.getName());
        if(testQuest.getSex() != null) {
            te.setSex(testQuest.getSex());
        }

        IPage<TestEntity> testLists = mf.getTestMapper().selectPage(page, new QueryWrapper<TestEntity>(te));
        List<TestDTO> lists = new ArrayList<>();
        testLists.getRecords().forEach(e -> {
            TestDTO t = new TestDTO();
            t.setId(e.getId());
            t.setName(e.getName());
            t.setSex(e.getSex() == 1 ? "男" : "女");
            t.setAge(e.getAge());
            t.setCreateTime(e.getCreateTime());
            t.setUpdateTime(e.getUpdateTime());
            lists.add(t);
        });

        PageVO<TestDTO> page33 = new PageVO(testQuest.getPageNum(), testQuest.getPageSize(), testLists.getTotal(), lists);

        return new Result(page33);
    }

   
    public Result<IPage<TestEntity>> getLists2(TestQuery testQuest) throws Exception {
        IPage<TestEntity> page = new Page<>(testQuest.getPageNum(), testQuest.getPageSize());
        TestEntity te = new TestEntity();
        if(StringUtils.isNotBlank(testQuest.getName()))
            te.setName(testQuest.getName());
        if(testQuest.getSex() != null) {
            te.setSex(testQuest.getSex());
        }
        IPage<TestEntity> testLists = mf.getTestMapper().selectPage(page, new QueryWrapper<TestEntity>(te));
        return new Result(testLists);
    }

   
    public TestDTO getByName1(String name) throws Exception {
        TestEntity te = mf.getTestMapper().selectOne(new QueryWrapper<TestEntity>().eq("name", name));
        TestDTO t = new TestDTO();
        t.setId(te.getId());
        t.setName(te.getName());
        t.setSex(te.getSex() == 0 ? "女" : "男");
        t.setAge(te.getAge());
        t.setCreateTime(te.getCreateTime());
        t.setUpdateTime(te.getUpdateTime());
        return t;
    }
}
