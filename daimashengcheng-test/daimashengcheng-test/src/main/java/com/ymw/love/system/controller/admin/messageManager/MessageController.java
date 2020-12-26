package com.ymw.love.system.controller.admin.messageManager;

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
import com.ymw.love.system.dto.BaseEntity;
import com.ymw.love.system.entity.message.input.config.MessageAdminInput;
import com.ymw.love.system.entity.message.input.config.MessageAdminQueryInput;
import com.ymw.love.system.entity.message.input.config.MessageCategoryInput;
import com.ymw.love.system.immobile.Constant;
import com.ymw.love.system.service.BaseService;
import com.ymw.love.system.vo.AUserVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/admin/message")
@ResponseBody
@Api(tags = { "后台消息管理" })
public class MessageController extends BaseService 	 {

    @Autowired
    private UserLogInInfo userLogInInfo;
    
    @Authority(value=Resource.enter.admin_user,content="活动消息添加")
    @ApiOperation(value = "后台活动消息添加", notes = "")
    @PostMapping("/option/insert")
    public Result insert(@RequestBody MessageAdminInput adminInput) {
        AUserVO au=	userLogInInfo.getAdminUser();
        return sf.getManagerService().messageOption(adminInput, Constant.OPTION_ADD, au.getId());
    }

    @Authority(value=Resource.enter.admin_user,content="动消息编辑")
    @ApiOperation(value = "后台活动消息编辑", notes = "")
    @PostMapping("/option/edit")
    public Result edit(@RequestBody MessageAdminInput adminInput) {
        AUserVO au=	userLogInInfo.getAdminUser();
        return sf.getManagerService().messageOption(adminInput, Constant.OPTION_EDIT, au.getId());
    }

    @Authority(value=Resource.enter.admin_user,content="活动消息删除")
    @ApiOperation(value = "后台活动消息删除", notes = "")
    @PostMapping("/option/delete")
    public Result delete(@RequestBody MessageAdminInput adminInput) {
        AUserVO au=	userLogInInfo.getAdminUser();
        return sf.getManagerService().messageOption(adminInput, Constant.OPTION_DELETE, au.getId());
    }

    @Authority(value=Resource.enter.admin_user,holdUp=false)
    @ApiOperation(value = "后台活动消息详情查询", notes = "")
    @GetMapping("/option/query")
    public Result queryRecord(@RequestParam(value = "id", required = true) String id) {
        return sf.getManagerService().queryRecordById(id);
    }

    @Authority(Resource.enter.admin_user)
    @ApiOperation(value = "后台活动消息批量推送", notes = "")
    @PostMapping("/option/push")
    public Result push(@RequestBody MessageAdminInput adminInput) {
        AUserVO au=	userLogInInfo.getAdminUser();
        return sf.getManagerService().messageOption(adminInput, Constant.OPTION_MESSAGE_ACTIVITY_PUSH, au.getId());
    }

    @Authority(Resource.enter.admin_user)
    @ApiOperation(value = "后台活动消息待推送列表查询", notes = "")
    @PostMapping("/option/query/sendbefore/list")
    public Result queryList(@RequestBody MessageAdminQueryInput queryInput) {
        return sf.getManagerService().queryPushList(Constant.OPTION_MESSAGE_PRE_SEND, queryInput);
    }

    @Authority(Resource.enter.admin_user)
    @ApiOperation(value = "后台活动消息已推送列表查询", notes = "")
    @PostMapping("/option/query/sent/list")
    public Result sentList(@RequestBody MessageAdminQueryInput queryInput) {
        return sf.getManagerService().queryPushList(Constant.OPTION_MESSAGE_SENT, queryInput);
    }

    @Authority(value=Resource.enter.admin_user ,content="消息类别添加")
    @ApiOperation(value = "后台消息类别添加", notes = "")
    @PostMapping("/option/insert/category")
    public Result insertMessageCategory(@RequestBody MessageCategoryInput categoryInput) {
        AUserVO au=	userLogInInfo.getAdminUser();
        return sf.getManagerService().optionMessageCategory(categoryInput, Constant.OPTION_ADD, au.getId());
    }

    @Authority(value=Resource.enter.admin_user,content="消息类别修改")
    @ApiOperation(value = "后台消息类别修改", notes = "")
    @PostMapping("/option/edit/category")
    public Result editMessageCategory(@RequestBody MessageCategoryInput categoryInput) {
        AUserVO au=	userLogInInfo.getAdminUser();
        return sf.getManagerService().optionMessageCategory(categoryInput, Constant.OPTION_EDIT, au.getId());
    }

    @Authority(value=Resource.enter.admin_user,content="消息类别删除")
    @ApiOperation(value = "后台消息类别删除", notes = "")
    @PostMapping("/option/delete/category")
    public Result deleteMessageCategory(@RequestBody MessageCategoryInput categoryInput) {
        AUserVO au=	userLogInInfo.getAdminUser();
        return sf.getManagerService().optionMessageCategory(categoryInput, Constant.OPTION_DELETE, au.getId());
    }

    @Authority(value=Resource.enter.admin_user,holdUp=false)
    @ApiOperation(value = "后台消息类别详情查询", notes = "")
    @GetMapping("/option/query/category")
    public Result queryCategory(@RequestParam(value = "id", required = true) String categoryId) {
        return sf.getManagerService().queryMessageCategoryById(categoryId);
    }

    @Authority(value=Resource.enter.admin_user,holdUp=false)
    @ApiOperation(value = "后台消息类别列表查询(复选框)", notes = "")
    @GetMapping("/option/query/categories")
    public Result queryMessageCategories() {
        return sf.getManagerService().queryMessageCategories();
    }

    @Authority(Resource.enter.admin_user)
    @ApiOperation(value = "后台消息类别列表查询", notes = "")
    @PostMapping("/option/query/category/list")
    public Result queryCategoryList(@RequestBody BaseEntity baseEntity) {
        return sf.getManagerService().queryCategoryList(baseEntity);
    }
}
