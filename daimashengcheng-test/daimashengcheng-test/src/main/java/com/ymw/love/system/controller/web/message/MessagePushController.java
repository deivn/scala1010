package com.ymw.love.system.controller.web.message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ymw.love.system.common.Resource;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.config.intercept.Authority;
import com.ymw.love.system.config.intercept.UserLogInInfo;
import com.ymw.love.system.entity.message.input.push.MessagePushInput;
import com.ymw.love.system.entity.message.input.push.MessageQueryInput;
import com.ymw.love.system.entity.message.input.push.MessageUpdateInput;
import com.ymw.love.system.immobile.Constant;
import com.ymw.love.system.service.BaseService;
import com.ymw.love.system.service.message.MessagePushService;
import com.ymw.love.system.vo.UUserVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/web/message/push")
@ResponseBody
@Api(tags = { "消息推送" })
public class MessagePushController extends BaseService  {

    private static final Logger log = LoggerFactory.getLogger(MessagePushController.class);

    @Autowired
    private MessagePushService pushService;

    @Autowired
    private UserLogInInfo userLogInInfo;

    @Authority(Resource.enter.web_user)
    @ApiOperation(value = "系统消息推送", notes = "")
    @PostMapping("/system")
    public Result systemMsgPush(@RequestBody MessagePushInput pushInput){
        log.info("--系统消息推送(/system)-------------"+JSON.toJSONString(pushInput));
        return pushService.optionPush(Constant.OPTION_MESSAGE_SYSTEM_PUSH, pushInput);
    }

    @Authority(Resource.enter.web_user)
    @ApiOperation(value = "动态消息推送", notes = "")
    @PostMapping("/dynamic")
    public Result dynamicMsgpush(@RequestBody MessagePushInput pushInput){
        log.info("--动态消息推送(/dynamic)-------------"+JSON.toJSONString(pushInput));
        return pushService.optionPush(Constant.OPTION_MESSAGE_DYNAMIC_PUSH, pushInput);
    }

    @Authority(Resource.enter.web_user)
    @ApiOperation(value = "消息列表所有记录查询", notes = "")
    @PostMapping("/query/all")
    public Result queryList(@RequestBody MessageQueryInput queryInput){
        UUserVO webUser = userLogInInfo.getWebUser();
        return pushService.queryList(Constant.OPTION_MESSAGE_QUERY,  webUser.getId(), queryInput);
    }

    @Authority(Resource.enter.web_user)
    @ApiOperation(value = "消息列表按消息分类查询", notes = "")
    @PostMapping("/query/type")
    public Result queryListByType(@RequestBody MessageQueryInput queryInput){
        UUserVO webUser = userLogInInfo.getWebUser();
        return pushService.queryList(Constant.OPTION_MESSAGE_QUERY_BY_TYPE, webUser.getId(), queryInput);
    }

    @Authority(Resource.enter.web_user)
    @ApiOperation(value = "消息未读数查询", notes = "")
    @GetMapping("/query/unreads")
    public Result queryUnreads(){
        UUserVO webUser = userLogInInfo.getWebUser();
        return pushService.queryList(Constant.OPTION_MESSAGE_QUERY_UNREADS, webUser.getId(), null);
    }

    @Authority(Resource.enter.web_user)
    @ApiOperation(value = "消息删除", notes = "")
    @PostMapping("/delete/ids")
    public Result deleteById(@RequestBody MessageUpdateInput updateInput){
        UUserVO webUser = userLogInInfo.getWebUser();
        log.info("--消息批量删除(/delete/ids)-------------"+JSON.toJSONString(updateInput)+"------userId"+webUser.getId());
        return pushService.option(Constant.OPTION_MESSAGE_DELETE_BY_Id, webUser.getId(), updateInput);
    }

    @Authority(Resource.enter.web_user)
    @ApiOperation(value = "消息未读改已读", notes = "")
    @PostMapping("/update/unread/ids")
    public Result updateUnreadById(@RequestBody MessageUpdateInput updateInput){
        UUserVO webUser = userLogInInfo.getWebUser();
        log.info("--消息批量未读改已读(/update/unread/ids)-------------"+JSON.toJSONString(updateInput)+"------userId"+webUser.getId());
        return pushService.option(Constant.OPTION_MESSAGE_UPDATE_BY_Id, webUser.getId(), updateInput);
    }
}
