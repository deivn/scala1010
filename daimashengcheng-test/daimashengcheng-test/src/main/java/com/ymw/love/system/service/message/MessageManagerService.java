package com.ymw.love.system.service.message;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.config.excep.MissRequiredParamException;
import com.ymw.love.system.dto.BaseEntity;
import com.ymw.love.system.entity.message.UMessageCategory;
import com.ymw.love.system.entity.message.UMessageConfig;
import com.ymw.love.system.entity.message.UMessagePush;
import com.ymw.love.system.entity.message.input.config.MessageAdminQueryInput;
import com.ymw.love.system.entity.message.input.config.MessageBaseInput;
import com.ymw.love.system.entity.message.input.config.MessageAdminInput;
import com.ymw.love.system.entity.message.input.config.MessageCategoryInput;
import com.ymw.love.system.entity.message.query.MessageAdminQueryResult;
import com.ymw.love.system.entity.message.query.MessageQueryResult;
import com.ymw.love.system.immobile.Constant;
import com.ymw.love.system.mq.sender.MessageSender;
import com.ymw.love.system.service.BaseService;
import com.ymw.love.system.util.CommonUtil;
import com.ymw.love.system.util.DateUtil;
import com.ymw.love.system.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 后台消息管理业务类
 */
@Service
public class MessageManagerService extends BaseService {



    @Value("${tag.remove.regex}")
    private String tagRegex;


    @Autowired
    private MessageSender sender;

    @Transactional
    public Result messageOption(MessageAdminInput adminInput, Integer option, String optionUid){
        MessageBaseInput baseInput = adminInput.getBaseInput();
        String id = adminInput.getId();
        paramVerify(baseInput, option, id);
        if(option == Constant.OPTION_ADD){
            //添加
            UMessageConfig messageConfig = new UMessageConfig(baseInput, optionUid, CommonUtil.tag2content(baseInput.getContent(), tagRegex));
            mf.getConfigMapper().insert(messageConfig);
        }else if(option == Constant.OPTION_EDIT){
            //编辑
            UMessageConfig messageConfig = new UMessageConfig(baseInput.getTitle(), baseInput.getContent(), baseInput.getCategoryId(),CommonUtil.tag2content(baseInput.getContent(), tagRegex), optionUid);
            mf.getConfigMapper().update(messageConfig, new UpdateWrapper<UMessageConfig>().eq("id", id));
        }else if(option == Constant.OPTION_DELETE){
            //删除（逻辑删除）
            UMessageConfig messageConfig = new UMessageConfig();
            messageConfig.setOptionUid(optionUid);
            messageConfig.setIsDelete(2);
            messageConfig.setEditTime(DateUtil.parseDateToStr(new Date(), DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI_SS));
            mf.getConfigMapper().update(messageConfig, new UpdateWrapper<UMessageConfig>().eq("id", id));
        }else if(option == Constant.OPTION_MESSAGE_ACTIVITY_PUSH){
                UMessageConfig messageConfig = getRecordById(id);
                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap.put("type", Constant.OPTION_MESSAGE_ACTIVITY_PUSH);
                resultMap.put("subType", Constant.OPTION_MESSAGE_ACTIVITY_PUSH);
                resultMap.put("bussinessId", id);
                send(messageConfig.getId(), messageConfig.getTitle(), messageConfig.getActivityInfo());
                messageConfig.setSendTime(DateUtil.parseDateToStr(new Date(), DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI));
                messageConfig.setOptionStatus(2);
                mf.getConfigMapper().update(messageConfig, new UpdateWrapper<UMessageConfig>().eq("id", id));
                return new Result(resultMap);
        }
        return new Result();

    }

    public void send(String businessId, String title, String content){
        //全平台用户消息推送

        UMessagePush messagePush = new UMessagePush(Constant.OPTION_MESSAGE_ACTIVITY_PUSH,
                        Constant.OPTION_MESSAGE_ACTIVITY_PUSH, businessId, "", title, content);
        sender.messagePush(messagePush);

    }

    public Result queryPushList(Integer option, MessageAdminQueryInput queryInput){
        Page<MessageQueryResult> page = new Page(queryInput.getPageNum(), queryInput.getPageSize());
        IPage<MessageQueryResult> messageQueryResults = null;
        if(option == Constant.OPTION_MESSAGE_PRE_SEND){
            //待发送列表查询
            messageQueryResults = mf.getMessageManagerMapper().queryPreSendList(page);
        }else if(option == Constant.OPTION_MESSAGE_SENT){
            Map<String, String> dateMap = CommonUtil.getQueryDate(queryInput.getDateValue(), queryInput.getStartDate(), queryInput.getEndDate());
            String startDate = dateMap.get("startDate");
            String endDate = dateMap.get("endDate");
            messageQueryResults = mf.getMessageManagerMapper().querySentList(page, startDate, endDate);
        }
        return new Result(messageQueryResults);
    }

    /**
     * 根据消息配置ID查询记录
     * @param id
     * @return
     */
    public Result queryRecordById(String id){
        return new Result(getRecordById(id));
    }

    public Result optionMessageCategory(MessageCategoryInput categoryInput, Integer option, String optionUid){
        String id = categoryInput.getId();
        String name = categoryInput.getName();
        categoryVerify(option, name, id);
        if(option == Constant.OPTION_ADD){
            UMessageCategory category = new UMessageCategory(name, optionUid);
            mf.getCategoryMapper().insert(category);
        }else if(option == Constant.OPTION_EDIT || option == Constant.OPTION_DELETE){
            UMessageCategory category = new UMessageCategory(optionUid);
            if(option == Constant.OPTION_EDIT){
                category.setName(name);
            }else {
                category.setIsDelete(2);
            }
            mf.getCategoryMapper().update(category, new UpdateWrapper<UMessageCategory>().eq("id", id));
        }
        return new Result();
    }

    public Result queryMessageCategories(){
        Map<String, String> categoryMap = null;
        List<UMessageCategory> categries = mf.getCategoryMapper().selectList(new QueryWrapper<UMessageCategory>().eq("is_delete", 1));
        if(categries == null || categries.size() == 0){
            categoryMap = new HashMap<String, String>();
        }else{
            categoryMap = categries.stream().collect(Collectors.toMap(UMessageCategory::getId, UMessageCategory::getName));
        }
        return new Result(categoryMap);
    }

    public Result queryCategoryList(BaseEntity baseEntity){
        Page<MessageAdminQueryResult> page = new Page(baseEntity.getPageNum(), baseEntity.getPageSize());
        IPage<MessageAdminQueryResult> messageAdminQueryResults = mf.getMessageManagerMapper().queryCategoryList(page);
        return new Result(messageAdminQueryResults);
    }

    public Result queryMessageCategoryById(String categoryId){
        return new Result(getMessageCategoryById(categoryId));
    }

    public void categoryVerify(Integer option, String categoryName, String id){
        if(option == null){
            throw new MissRequiredParamException(Constant.OPTIN_PARAM_EMPTY);
        }
        if(option == Constant.OPTION_EDIT || option == Constant.OPTION_DELETE){
            if(StringUtils.isEmpty(id)){
                throw new MissRequiredParamException(Constant.MESSAGE_ID_EMPTY);
            }
        }
        if(option == Constant.OPTION_ADD || option == Constant.OPTION_EDIT){
            if(StringUtils.isEmpty(categoryName)){
                throw new MissRequiredParamException(Constant.MESSAGE_CATEGORY_NAME_EMPTY);
            }
        }
    }

    public void paramVerify(MessageBaseInput baseInput, Integer option, String id){
        if(option == null){
            throw new MissRequiredParamException(Constant.OPTIN_PARAM_EMPTY);
        }
        if(option == Constant.OPTION_ADD || option == Constant.OPTION_EDIT){
            if(baseInput == null){
                throw new MissRequiredParamException(Constant.MESSAGE_SUBMIT_EMPTY);
            }
            String categoryId = baseInput.getCategoryId();
            String title = baseInput.getTitle();
            String content = baseInput.getContent();
            if(StringUtils.isEmpty(categoryId) || StringUtils.isEmpty(title) || StringUtils.isEmpty(content)){
                throw new MissRequiredParamException(Constant.REQUIRED_PARAM_INVALID);
            }
            String activityInfo = CommonUtil.tag2content(baseInput.getContent(), tagRegex);
            if(StringUtils.isEmpty(activityInfo)){
                throw new MissRequiredParamException(Constant.ACTIVITY_INFO_INVALID);
            }
        }
        if(option == Constant.OPTION_EDIT || option == Constant.OPTION_DELETE || option == Constant.OPTION_MESSAGE_ACTIVITY_PUSH){
            if(StringUtils.isEmpty(id)){
                throw new MissRequiredParamException(Constant.MESSAGE_ID_EMPTY);
            }
        }
    }

    public UMessageConfig getRecordById(String id){
        List<UMessageConfig> records = mf.getConfigMapper().selectList(new QueryWrapper<UMessageConfig>().eq("id", id));
        if(records == null || records.size() == 0){
            throw new MissRequiredParamException(Constant.MESSAGE_NOT_EXIST);
        }
        return records.get(0);
    }

    public UMessageCategory getMessageCategoryById(String categoryId){
        List<UMessageCategory> categries = mf.getCategoryMapper().selectList(new QueryWrapper<UMessageCategory>().eq("id", categoryId));
        if(categries == null || categries.size() == 0){
            throw new MissRequiredParamException(Constant.MESSAGE_NOT_EXIST);
        }
        return categries.get(0);
    }
}
