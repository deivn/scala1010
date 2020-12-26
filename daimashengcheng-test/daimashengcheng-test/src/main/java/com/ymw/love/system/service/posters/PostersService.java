package com.ymw.love.system.service.posters;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.config.excep.MissRequiredParamException;
import com.ymw.love.system.entity.posters.UPosters;
import com.ymw.love.system.entity.posters.input.BaseInput;
import com.ymw.love.system.entity.posters.input.PosterCaculateInput;
import com.ymw.love.system.entity.posters.input.PosterInput;
import com.ymw.love.system.entity.posters.input.PostersQueryInput;
import com.ymw.love.system.entity.posters.query.PostersQueryResult;
import com.ymw.love.system.immobile.Constant;
import com.ymw.love.system.mq.sender.PostersCaculateSender;
import com.ymw.love.system.service.BaseService;
import com.ymw.love.system.util.CommonUtil;
import com.ymw.love.system.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 海报业务类
 */
@Service
public class PostersService extends BaseService {

    @Autowired
    private PostersCaculateSender sender;

    @Value("${qr.top.left.position}")
    private String qrTopLeft; //二维码左上角坐标

    @Value("${qr.lower.right.position}")
    private String qrLowerRight; //二维码右下角坐标

    @Value("${invitation.top.left.position}")
    private String invitationTopLeft; //邀请码左上角坐标

    @Value("${invitation.lower.right.position}")
    private String invitationLowerRight; //邀请码右下角坐标



    public Result postersOption(PosterInput posterInput, Integer option, String optionUid){
        BaseInput baseInput = posterInput.getBaseInput();
        String postersId = posterInput.getPostersId();
        paramVerify(baseInput, option, postersId);
        if(option == Constant.OPTION_ADD){
            //添加
            UPosters posters = new UPosters(baseInput, optionUid);
            mf.getPostersMapper().insert(posters);
        }else if(option == Constant.OPTION_EDIT){
            //编辑
            UPosters posters = new UPosters(optionUid);
            posters.setPostersName(baseInput.getPostersName());
            posters.setPostersImg(baseInput.getPostersImg());
            posters.setPostersDesc(StringUtils.isEmpty(baseInput.getDescription())? "": baseInput.getDescription());
            mf.getPostersMapper().update(posters, new UpdateWrapper<UPosters>().eq("id", postersId));
        }else if(option == Constant.OPTION_DELETE){
            //删除
            UPosters posters = new UPosters(optionUid);
            posters.setIsDelete(2);
            mf.getPostersMapper().update(posters, new QueryWrapper<UPosters>().eq("id", postersId));
        }else if(option == Constant.OPTION_PUBLISH || option == Constant.OPTION_CANCEL_PUBLISH){
            //发布/停止发布
            UPosters posters = new UPosters(optionUid);
            if(option == Constant.OPTION_PUBLISH){
                posters.setPublishStatus(Constant.PUBLISED);
            }else {
                posters.setPublishStatus(Constant.STOP_PUBLISH);
            }
            mf.getPostersMapper().update(posters, new UpdateWrapper<UPosters>().eq("id", postersId));
        }
        return new Result();
    }

    public Result getRecordsById(String postersId){
        if(StringUtils.isEmpty(postersId)){
            throw new MissRequiredParamException(Constant.REQUIRED_PARAM_INVALID);
        }
        return new Result(getPostersRecordById(postersId));
    }


    public Result queryPostersList(PostersQueryInput queryInput){
        Map<String, String> dateMap = CommonUtil.getQueryDate(queryInput.getDateValue(), queryInput.getStartDate(), queryInput.getEndDate());
        String startDate = dateMap.get("startDate");
        String endDate = dateMap.get("endDate");
        Page<PostersQueryResult> page = new Page<>(queryInput.getPageNum(), queryInput.getPageSize());
        IPage<PostersQueryResult> postersQueryResult = mf.getPostersManagerMapper().queryPostersList(page, startDate, endDate);
        return new Result(postersQueryResult);
    }

    public Result caculateShares(PosterCaculateInput caculateInput){
        Integer appSign = caculateInput.getAppSign();
        String postersId = caculateInput.getPostersId();
        //目前只有1.微信 2.朋友圈 3.QQ
        if(appSign == null || appSign < 1 || appSign > 3 || StringUtils.isEmpty(postersId)){
            throw new MissRequiredParamException(Constant.REQUIRED_PARAM_INVALID);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("appSign", appSign);
        map.put("postersId", postersId);
        sender.sharedCaculate(map);
        return new Result();
    }


    public UPosters getPostersRecordById(String postersId){
        List<UPosters> records = mf.getPostersMapper().selectList(new QueryWrapper<UPosters>().eq("id", postersId));
        if(records == null || records.size() == 0){
            throw new MissRequiredParamException(Constant.POSTERS_NOT_EXIST);
        }
        return records.get(0);
    }

    public UPosters getPostersByPublisStatus(){
        List<UPosters> records = mf.getPostersMapper().selectList(new QueryWrapper<UPosters>().eq("publish_status", Constant.PUBLISED));
        if(records == null || records.size() == 0){
            throw new MissRequiredParamException(Constant.POSTERS_NOT_EXIST);
        }
        return records.get(0);
    }


    /**
     * 参数校验
     * @param baseInput
     * @param option
     * @param postersId
     */
    public void paramVerify(BaseInput baseInput, Integer option, String postersId){
        if(option == null){
            throw new MissRequiredParamException(Constant.OPTIN_PARAM_EMPTY);
        }
        if(option < Constant.OPTION_ADD || option > Constant.OPTION_CANCEL_PUBLISH){
            throw new MissRequiredParamException(Constant.OPTION_PARAM_INVALID);
        }
        if(option == Constant.OPTION_ADD || option == Constant.OPTION_EDIT){
            if(baseInput == null){
                throw new MissRequiredParamException(Constant.OPTION_SUMBIT_OR_EDIT_EMPTY);
            }
            String postersName = baseInput.getPostersName();
            String postersImg = baseInput.getPostersImg();
            if(StringUtils.isEmpty(postersName) || StringUtils.isEmpty(postersImg)){
                throw new MissRequiredParamException(Constant.REQUIRED_PARAM_INVALID);
            }
        }
        if(option == Constant.OPTION_EDIT || option == Constant.OPTION_DELETE
                || option == Constant.OPTION_PUBLISH || option == Constant.OPTION_CANCEL_PUBLISH){
            if(StringUtils.isEmpty(postersId)){
                throw new MissRequiredParamException(Constant.REQUIRED_PARAM_INVALID);
            }
        }
    }

    public Result posterPositionInfo(){
        Map<String, String> position = new HashMap<String, String>();
        position.put("qrTopLeft", qrTopLeft);
        position.put("qrLowerRight", qrLowerRight);
        position.put("invitationTopLeft", invitationTopLeft);
        position.put("invitationLowerRight", invitationLowerRight);
        return new Result(position);
    }

    public Result queryPostersDetail(String uid){
        Map<String, String> detailMap = new HashMap<String, String>();
        UPosters posters = getPostersByPublisStatus();
        detailMap.put("uid", uid.substring(0, uid.length()-3));
        detailMap.put("postersId", posters.getId());
        detailMap.put("postersImg", posters.getPostersImg());
        return new Result(detailMap);
    }
}
