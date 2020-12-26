package com.ymw.love.system.controller.admin;

import com.ymw.love.system.common.Resource;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.config.intercept.Authority;
import com.ymw.love.system.entity.seeDoctor.input.TakeNumberInput;
import com.ymw.love.system.entity.seeDoctor.input.UpdateNumbersInput;
import com.ymw.love.system.service.BaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/takenumber")
@ResponseBody
@Api(tags = { "后台号源配置" })
public class TakeNumberController extends BaseService {



    @Authority(Resource.enter.admin_user)
    @ApiOperation(value = "添加预约号源分配", notes = "")
    @PostMapping("/insert/numbers")
    public Result insertNumber(@RequestBody TakeNumberInput numberInput) {
        return sf.getTakeNumberService().numberDistribute(numberInput);
    }

    @Authority(Resource.enter.admin_user)
    @ApiOperation(value = "编辑预约号源分配", notes = "")
    @PostMapping("/edit/numbers")
    public Result editNumber(@RequestBody UpdateNumbersInput input) {
        return sf.getTakeNumberService().updateNumbers(input);
    }


}
