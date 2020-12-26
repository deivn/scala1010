package com.ymw.love.system.controller.admin.PostersManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymw.love.system.common.Resource;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.config.intercept.Authority;
import com.ymw.love.system.config.intercept.UserLogInInfo;
import com.ymw.love.system.entity.posters.input.PosterInput;
import com.ymw.love.system.entity.posters.input.PostersQueryInput;
import com.ymw.love.system.immobile.Constant;
import com.ymw.love.system.service.BaseService;
import com.ymw.love.system.vo.AUserVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/admin/postersmanager")
@ResponseBody
@Api(tags = { "后台海报管理" })
public class PostersController extends BaseService  {

    @Autowired
    private UserLogInInfo userLogInInfo;
    
    @Authority(value=Resource.enter.admin_user,content="后台海报添加")
    @ApiOperation(value = "后台海报添加", notes = "")
    @PostMapping("/option/insert/posters")
    public Result insert(@RequestBody PosterInput posterInput) {
        AUserVO au=	userLogInInfo.getAdminUser();
        return sf.getPostersService().postersOption(posterInput, Constant.OPTION_ADD, au.getId());
    }

    @Authority(value=Resource.enter.admin_user,content="后台海报编辑")
    @ApiOperation(value = "后台海报编辑", notes = "")
    @PostMapping("/option/edit/posters")
    public Result edit(@RequestBody PosterInput posterInput) {
        AUserVO au=	userLogInInfo.getAdminUser();
        return sf.getPostersService().postersOption(posterInput, Constant.OPTION_EDIT, au.getId());
    }

    @Authority(value=Resource.enter.admin_user,content="后台海报删除")
    @ApiOperation(value = "后台海报删除", notes = "")
    @PostMapping("/option/delete/posters")
    public Result delete(@RequestBody PosterInput posterInput) {
        AUserVO au=	userLogInInfo.getAdminUser();
        return sf.getPostersService().postersOption(posterInput, Constant.OPTION_DELETE, au.getId());
    }

    @Authority(value=Resource.enter.admin_user,content="海报发布")
    @ApiOperation(value = "后台海报发布", notes = "")
    @PostMapping("/option/publish/posters")
    public Result publish(@RequestBody PosterInput posterInput) {
        AUserVO au=	userLogInInfo.getAdminUser();
        return sf.getPostersService().postersOption(posterInput, Constant.OPTION_PUBLISH, au.getId());
    }

    @Authority(value=Resource.enter.admin_user,holdUp=false,content="海报停止发布")
    @ApiOperation(value = "后台海报停止发布", notes = "")
    @PostMapping("/option/stoppublish/posters")
    public Result stopPublish(@RequestBody PosterInput posterInput) {
        AUserVO au=	userLogInInfo.getAdminUser();
        return sf.getPostersService().postersOption(posterInput, Constant.OPTION_CANCEL_PUBLISH, au.getId());
    }

    @Authority(value=Resource.enter.admin_user,holdUp=false)
    @ApiOperation(value = "后台海报查询", notes = "")
    @GetMapping("/option/query/posters")
    public Result query(@RequestParam("postersId") String postersId) {
        return sf.getPostersService().getRecordsById(postersId);
    }

    @Authority(Resource.enter.admin_user)
    @ApiOperation(value = "后台海报列表查询", notes = "")
    @PostMapping("/option/query/posters/list")
    public Result queryList(@RequestBody PostersQueryInput queryInput) {
        return sf.getPostersService().queryPostersList(queryInput);
    }

}
