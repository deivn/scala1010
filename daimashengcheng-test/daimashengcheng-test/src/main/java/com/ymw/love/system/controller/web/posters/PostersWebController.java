package com.ymw.love.system.controller.web.posters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymw.love.system.common.Resource;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.config.intercept.Authority;
import com.ymw.love.system.config.intercept.UserLogInInfo;
import com.ymw.love.system.entity.posters.input.PosterCaculateInput;
import com.ymw.love.system.service.BaseService;
import com.ymw.love.system.service.posters.PostersService;
import com.ymw.love.system.vo.UUserVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/web/posters")
@ResponseBody
@Api(tags = { "前台海报分享统计" })
public class PostersWebController extends BaseService  {

    @Autowired
    private PostersService postersService;

    @Autowired
    private UserLogInInfo userLogInInfo;

    @Authority(Resource.enter.web_user)
    @ApiOperation(value = "海报分享统计", notes = "")
    @PostMapping("/share/caculate")
    public Result caculate(@RequestBody PosterCaculateInput caculateInput){
        return postersService.caculateShares(caculateInput);
    }

    @Authority(Resource.enter.web_user)
    @ApiOperation(value = "获取海报信息坐标", notes = "")
    @GetMapping("/query/position")
    public Result queryPosition(){
        return postersService.posterPositionInfo();
    }

    @Authority(Resource.enter.web_user)
    @ApiOperation(value = "获取海报背景图", notes = "")
    @GetMapping("/query/detail")
    public Result queryDetail(){
        UUserVO webUser = userLogInInfo.getWebUser();
        return postersService.queryPostersDetail(webUser.getId());
    }

}
