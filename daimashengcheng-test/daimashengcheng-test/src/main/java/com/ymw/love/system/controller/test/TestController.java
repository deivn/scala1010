package com.ymw.love.system.controller.test;


import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.config.excep.ServiceException;
import com.ymw.love.system.dto.test.TestDTO;
import com.ymw.love.system.dto.test.TestQuery;
import com.ymw.love.system.dto.test.TestRequest;
import com.ymw.love.system.entity.test.TestEntity;
import com.ymw.love.system.service.BaseService;
import com.ymw.love.system.vo.PageVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/test/demo")
@Api(tags = { "测试" })
public class TestController extends BaseService {

    @ApiOperation(value = "根据名称查询", notes = "根据名称查询")
    @GetMapping("/getByName")
    public Result<TestDTO> getByName(@RequestParam String name) throws Exception {
        return sf.getTestService().getByName(name);
    }

    @ApiOperation(value = "根据名称查询1", notes = "根据名称查询1")
    @GetMapping("/getByName1")
    public Result<TestDTO> getByName1(@RequestParam String name) throws Exception {
        return new Result<>(sf.getTestService().getByName1(name));
    }

    @ApiOperation(value = "查询列表", notes = "查询列表")
    @GetMapping("/getList")
    public Result<PageVO<TestDTO>> getList(TestQuery testQuest) throws Exception {
        return sf.getTestService().getLists(testQuest);
    }

    @ApiOperation(value = "查询列表2", notes = "查询列表2")
    @GetMapping("/getList2")
    public Result<IPage<TestEntity>>  getList2(TestQuery testQuest) throws Exception {
        return sf.getTestService().getLists2(testQuest);
    }

    @ApiOperation(value = "添加", notes = "添加")
    @PostMapping("/add")
    public Result add(@RequestBody @Valid TestRequest req, BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()) {
            throw new ServiceException(bindingResult.getFieldError().getDefaultMessage());
        }
        return sf.getTestService().insTestRecord(req);
    }


    @ApiOperation(value = "修改", notes = "修改")
    @PutMapping("/updae")
    public Result update(@RequestParam(required = false)  String name, @RequestParam(required = false) Integer sex,
                         @RequestParam(required = false) Integer age, @RequestParam Integer id) throws Exception {
        return sf.getTestService().updateRecord(name, sex, age, id);
    }

    @ApiOperation(value = "根据名称删除记录", notes = "根据名称删除记录")
    @DeleteMapping("/delByName")
    public Result delByName(@RequestParam String name) throws Exception {
        return sf.getTestService().delByNameRecord(name);
    }
}
