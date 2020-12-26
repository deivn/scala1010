package com.ymw.love.system.service.message;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.config.MessageTemplate;
import com.ymw.love.system.config.excep.MissRequiredParamException;
import com.ymw.love.system.entity.message.UMessagePush;
import com.ymw.love.system.entity.message.input.push.*;
import com.ymw.love.system.immobile.Constant;
import com.ymw.love.system.mq.sender.MessageSender;
import com.ymw.love.system.service.BaseService;
import com.ymw.love.system.util.CommonUtil;
import com.ymw.love.system.util.DateUtil;
import com.ymw.love.system.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;

/**
 * 消息推送业务类
 */
@Service
public class MessagePushService extends BaseService {


    @Autowired
    private MessageSender sender;

    @Autowired
    private MessageTemplate messageTemplate;

    /**
     *
     * @param jumpType  大类：7.系统消息 8动态消息
     * @param pushInput
     * @return
     */
    public Result optionPush(Integer jumpType, MessagePushInput pushInput){
        paramVerify(pushInput, jumpType);
        Map<String, String> body = buildMessageBody(pushInput);
        String destUid = pushInput.getDestUid();
        UMessagePush messagePush = new UMessagePush(jumpType, pushInput.getOption(), pushInput.getBusinessId(), destUid, body.get("title"), body.get("content"));
        sender.messagePush(messagePush);
        return new Result(buildResultMap(messagePush.getType(), messagePush.getSubType(), messagePush.getBusinessId()));
    }

    public Result queryList(Integer option, String userId, MessageQueryInput queryInput){
        if(option == Constant.OPTION_MESSAGE_QUERY || option == Constant.OPTION_MESSAGE_QUERY_BY_TYPE){
            IPage<UMessagePush> messagePushes = null;
            Page<UMessagePush> page = new Page<>(queryInput.getPageNum(), queryInput.getPageSize());
            if(option == Constant.OPTION_MESSAGE_QUERY){
                messagePushes = mf.getMessagePushMapper().queryMessagesByUid(page, userId);
            }else{
                messagePushes = mf.getMessagePushMapper().queryMessagesByType(page, userId, queryInput.getType());
            }
            return new Result(messagePushes);
        }else if(option == Constant.OPTION_MESSAGE_QUERY_UNREADS){
            //消息未读数查询
            return new Result(mf.getMessagePushMapper().messageUnreadCount(userId));
        }
        return new Result();
    }

    public Result option(Integer option, String userId, MessageUpdateInput updateInput){
        String msgId = updateInput.getIds();
        if(StringUtils.isEmpty(msgId)){
            throw new MissRequiredParamException(Constant.IDS_EMPTY);
        }
        String[] ids = msgId.split(",");
        String dateTime = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS);
        if(option == Constant.OPTION_MESSAGE_DELETE_BY_Id){
            mf.getMessagePushMapper().deleteMessagesByIds(ids, dateTime, userId);
        }else if(option == Constant.OPTION_MESSAGE_UPDATE_BY_Id){
            mf.getMessagePushMapper().updateUnreadsByIds(ids, dateTime, userId);
        }
        return new Result();
    }
    
    public Map<String, String> buildMessageBody(MessagePushInput pushInput){
        Map<String, String> messageBody = new HashMap<String, String>();
        Integer option = pushInput.getOption();
        SystemMessageInput systemInput = pushInput.getSystemInput();
        DynamicMessageInput dynamicInput = pushInput.getDynamicInput();
        String title = "";
        String content = "";
        List<String> titleParamList = new ArrayList<String>();
        List<String> contentParamList = new ArrayList<String>();
        if(option == Constant.MESSAGE_SYSTEM_SALAVATION){
            title = messageTemplate.getSalvationTitle();
            content = messageTemplate.getSalvationContent();
            contentParamList.add(systemInput.getAmount());
        }else if(option == Constant.MESSAGE_SYSTEM_LOVE){
            title = messageTemplate.getLoveTitle();
            content = messageTemplate.getLoveContent();
            //手机号关键字加密
            String phone = CommonUtil.getStarString(systemInput.getPhone(), systemInput.getPhone().length()-8, systemInput.getPhone().length()-4);
            contentParamList.add(phone);
            contentParamList.add(systemInput.getAmount());
        }else if(option == Constant.MESSAGE_SYSTEM_LOVE_ACTIVATION){
            title = messageTemplate.getLoveActivationTitle();
            content = messageTemplate.getLoveActivationContent();
            contentParamList.add(systemInput.getAmount());
        }else if(option == Constant.MESSAGE_SYSTEM_WITHDRAW){
            title = messageTemplate.getWithdrawTitle();
            content = messageTemplate.getWithdrawContent();
            contentParamList.add(systemInput.getWeChartAccount());
            contentParamList.add(systemInput.getAmount());
        }else if(option == Constant.MESSAGE_SYSTEM_APPOINT_SUCCESS){
            title = messageTemplate.getAppointSuccessTitle();
            content = messageTemplate.getAppointSuccessContent();
            contentParamList.add(systemInput.getAppointDetail());
        }else if(option == Constant.MESSAGE_SYSTEM_APPOINT_CANCEL){
            title = messageTemplate.getAppointCancelTitle();
            content = messageTemplate.getAppointCancelContent();
            contentParamList.add(systemInput.getAppointDetail());
        }else if(option == Constant.MESSAGE_SYSTEM_APPOINT_OUTDATE){
            title = messageTemplate.getOutDateTitle();
            content = messageTemplate.getOutDateContent();
            contentParamList.add(systemInput.getAppointDetail());
        }else if(option == Constant.MESSAGE_DYNAMIC_QUESTION_REPLY){
            title = messageTemplate.getQuestionReplyTitle();
            content = messageTemplate.getQuestionReplyContent();
            titleParamList.add(dynamicInput.getReplyName());
            contentParamList.add(dynamicInput.getContent());
        }else if(option == Constant.MESSAGE_DYNAMIC_COMMENT_REPLY){
            title = messageTemplate.getCommentReplyTitle();
            content = messageTemplate.getCommentReplyContent();
            titleParamList.add(dynamicInput.getReplyName());
            contentParamList.add(dynamicInput.getContent());
        }else if(option == Constant.MESSAGE_DYNAMIC_LIKE){
            title = messageTemplate.getLikeTitle();
            content = messageTemplate.getLikeContent();
            contentParamList.add(dynamicInput.getReplyName());
        }
        if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)){
            throw new MissRequiredParamException(Constant.OPTION_INVALID);
        }
        messageBody.put("title", getValue(title, titleParamList));
        messageBody.put("content", getValue(content, contentParamList));
        return messageBody;
    }

    public String getValue(String str, List<String> list){
        String[] values=list.toArray(new String[list.size()]);
        return MessageFormat.format(str, values);
    }

    public void paramVerify(MessagePushInput pushInput, Integer jumpType){
        Integer option = pushInput.getOption();
        String destUid = pushInput.getDestUid();
        String businessId = pushInput.getBusinessId();
        if(option == null){
            throw new MissRequiredParamException(Constant.OPTIN_PARAM_EMPTY);
        }
        if(StringUtils.isEmpty(destUid)){
            throw new MissRequiredParamException(Constant.DEST_UID_EMPTY);
        }
        if(StringUtils.isEmpty(businessId)){
            throw new MissRequiredParamException(Constant.MESSAGE_BUSINESS_ID_EMPTY);
        }
        if(jumpType == Constant.OPTION_MESSAGE_SYSTEM_PUSH){
            SystemMessageInput systemInput = pushInput.getSystemInput();
            if(systemInput == null){
                throw new MissRequiredParamException(Constant.MESSAGE_SYSTEM_PARAM_EMPTY);
            }
            if(option == Constant.MESSAGE_SYSTEM_SALAVATION || option == Constant.MESSAGE_SYSTEM_LOVE
                    || option == Constant.MESSAGE_SYSTEM_LOVE_ACTIVATION || option == Constant.MESSAGE_SYSTEM_WITHDRAW){
                //20.救助券消息
                String amount = systemInput.getAmount();
                if(StringUtils.isEmpty(amount)){
                    throw new MissRequiredParamException(Constant.AMOUNT_EMPTY);
                }
            }
            if(option == Constant.MESSAGE_SYSTEM_LOVE){
                //21.爱心券消息
                String phone = systemInput.getPhone();
                if(StringUtils.isEmpty(phone)){
                    throw new MissRequiredParamException(Constant.PHONE_EMPTY);
                }
            }
            if(option == Constant.MESSAGE_SYSTEM_WITHDRAW){
                //23.提现通知
                String weChartAccount = systemInput.getWeChartAccount();
                if(StringUtils.isEmpty(weChartAccount)){
                    throw new MissRequiredParamException(Constant.WECHART_ACCOUNT_EMPTY);
                }
            }
            if(option == Constant.MESSAGE_SYSTEM_APPOINT_SUCCESS || option == Constant.MESSAGE_SYSTEM_APPOINT_CANCEL
                    || option == Constant.MESSAGE_SYSTEM_APPOINT_OUTDATE){
                //24.预约成功
                String appointDetail = systemInput.getAppointDetail();
                if(StringUtils.isEmpty(appointDetail)){
                    throw new MissRequiredParamException(Constant.APPOINT_DETAL_EMPTY);
                }
            }
        }else if(jumpType == Constant.OPTION_MESSAGE_DYNAMIC_PUSH){
            DynamicMessageInput dynamicInput = pushInput.getDynamicInput();
            if(dynamicInput == null){
                throw new MissRequiredParamException(Constant.MESSAGE_DYNAMIC_PARAM_EMPTY);
            }
            if(option == Constant.MESSAGE_DYNAMIC_QUESTION_REPLY || option == Constant.MESSAGE_DYNAMIC_COMMENT_REPLY
                    || option == Constant.MESSAGE_DYNAMIC_LIKE){
                String replyName = dynamicInput.getReplyName();
                String content = dynamicInput.getContent();
                if(StringUtils.isEmpty(replyName)){
                    throw new MissRequiredParamException(Constant.NAME_EMPTY);
                }
                if(option == Constant.MESSAGE_DYNAMIC_QUESTION_REPLY || option == Constant.MESSAGE_DYNAMIC_COMMENT_REPLY){
                    if(StringUtils.isEmpty(content)){
                        throw new MissRequiredParamException(Constant.REPLY_CONTENT_EMPTY);
                    }
                }
            }
        }
    }

    private Map<String, Object> buildResultMap(Integer type, Integer subType, String bussinessId){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("type", type);
        resultMap.put("subType", subType);
        resultMap.put("businessId", bussinessId);
        return resultMap;
    }
}
