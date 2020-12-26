package com.ymw.love.system.service.faq;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.common.SystemEnum;
import com.ymw.love.system.config.excep.MissRequiredParamException;
import com.ymw.love.system.entity.faq.*;
import com.ymw.love.system.entity.sensitive.SensitiveEntity;
import com.ymw.love.system.service.BaseService;
import com.ymw.love.system.util.StringUtils;
import com.ymw.love.system.vo.UUserVO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Q&a The business logic implementation class
 *
 * @author zengjuncheng
 * @version 1.1
 * @Creation time  2019/8/2
 */
@SuppressWarnings("ALL")
@Slf4j
@Service
public class FaqServiceImpl extends BaseService {



    @Transactional(rollbackFor = Exception.class)
    public Result insFaq(FaqEntity faqEntity) throws Exception {
        try {
            List<SensitiveEntity> likeSensitive = mf.getSensitive().findLikeSensitive(faqEntity.getIssueContent());
            if (likeSensitive.size() > 0) {
               throw new Exception("不能带有敏感词,谢谢！");
            }
            UUserVO webUser = mf.getUserLogInInfo().getWebUser();
            faqEntity.setUserId(webUser.getId());
            faqEntity.setAddTime(LocalDateTime.now());
            faqEntity.setAuditStatus(2);
            boolean insert = faqEntity.insert();
            return new Result().setCode(insert == false ? SystemEnum.FAIL : SystemEnum.SUCCESS).setMsg(insert == false ? "fail" : "success");
        } catch (Exception x) {
            throw new Exception("發佈問答異常." + x.getMessage());
        }
    }

    public Result findFaqList(FaqEntity faqEntity) {
        Page<FaqEntity> page = new Page<>(faqEntity.getPageSum(), faqEntity.getPageSize());
        IPage<FaqEntity> faqEntities = mf.getFaqMapper().selectFaqPageList(page, faqEntity);
        faqEntities.getRecords().forEach(faqlist -> {
            LambdaQueryWrapper<FaqCommentEntity> lambdaQuery = Wrappers.lambdaQuery();
            lambdaQuery.select(FaqCommentEntity::getCommentContent).eq(FaqCommentEntity::getFaqId, faqlist.getId()).orderByDesc(FaqCommentEntity::getAddTime).last("limit 1");
            List<FaqCommentEntity> faqCommentEntities = mf.getFaqCommentMapper().selectList(lambdaQuery);
            Integer replyCount = mf.getFaqCommentMapper().selectCount(new QueryWrapper<FaqCommentEntity>().eq("faq_id", faqlist.getId()));
            faqlist.setReplyCount(replyCount);
            faqlist.setReplyContent(faqCommentEntities.size() > 0 ? faqCommentEntities.get(0).getCommentContent() : null);
        });
        return new Result(faqEntities);
    }


    public Result findSpecialDetailsList(FaqEntity faqEntity) {
        Page<FaqEntity> page = new Page<>(faqEntity.getPageSum(), faqEntity.getPageSize());
        if (StringUtils.isEmpty(faqEntity.getId())) {
            return new Result().setCode(SystemEnum.SUCCESS).setMsg("专栏id必传.");
        }
        IPage<FaqEntity> faqlists = mf.getFaqMapper().findSpecialDetailsList(page, faqEntity);
        faqlists.getRecords().forEach(faqlist -> {
            LambdaQueryWrapper<FaqCommentEntity> lambdaQuery = Wrappers.lambdaQuery();
            lambdaQuery.select(FaqCommentEntity::getCommentContent).eq(FaqCommentEntity::getFaqId, faqlist.getId()).orderByDesc(FaqCommentEntity::getAddTime).last("limit 1");
            List<FaqCommentEntity> faqCommentEntities = mf.getFaqCommentMapper().selectList(lambdaQuery);
            //Integer replyCount = faqCommentMapper.selectCount(new QueryWrapper<FaqCommentEntity>().eq("faq_id", faqlist.getId()));
            //faqlist.setReplyCount(replyCount);
            faqlist.setReplyContent(faqCommentEntities.size() > 0 ? faqCommentEntities.get(0).getCommentContent() : null);
        });
        return new Result(faqlists);
    }



    public Result findSpecialList(SpecialEntity specialEntity) {
        Page<SpecialEntity> page = new Page<>(specialEntity.getPageSum(), specialEntity.getPageSize());
        IPage<SpecialEntity> specialEntityIPage = mf.getFaqMapper().selectFaqSpecialList(page, specialEntity);
        specialEntityIPage.getRecords().forEach(specialEntity1 -> {
            Integer viewCount = mf.getFaqMapper().findFaqCount(specialEntity1.getId());
            if (StringUtils.isEmpty(viewCount)) {
                specialEntity1.setBrowseCount(0);
            } else {
                specialEntity1.setBrowseCount(viewCount);
            }
        });
        return new Result(specialEntityIPage);
    }


    public Result findFaqSearchList(Map<String, Object> map) {
        LambdaQueryWrapper<FaqEntity> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.select(FaqEntity::getId, FaqEntity::getIssueContent).eq(FaqEntity::getAuditStatus, 2).
                like(StringUtils.isNotEmpty(map.get("content")), FaqEntity::getIssueContent, map.get("content")).orderByDesc(FaqEntity::getAddTime);
        List<FaqEntity> faqEntities = mf.getFaqMapper().selectList(lambdaQueryWrapper);
        return new Result(faqEntities);
    }


    public Result findMyFaqList(FaqEntity faqEntity) {
        UUserVO webUser = mf.getUserLogInInfo().getWebUser();
        Page<FaqEntity> page = new Page<>(faqEntity.getPageSum(), faqEntity.getPageSize());
        IPage<FaqEntity> myFaqList = mf.getFaqMapper().findMyFaqList(page, webUser.getId());
        return new Result(myFaqList);
    }


    public Result findMyFaqAnswerList(FaqEntity faqEntity) {
        UUserVO webUser = mf.getUserLogInInfo().getWebUser();
        Page<FaqEntity> page = new Page<>(faqEntity.getPageSum(), faqEntity.getPageSize());
        IPage<FaqEntity> myFaqList = mf.getFaqMapper().findMyFaqAnswerList(page, webUser.getId());
        return new Result(myFaqList);
    }


    public Result findFaqTypeAll() {
        LambdaQueryWrapper<FaqTypeEntity> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.select(FaqTypeEntity::getId, FaqTypeEntity::getTypeName).eq(FaqTypeEntity::getTypeStatus, 1);
        List<FaqTypeEntity> faqTypeEntities = mf.getFaqTypeMapper().selectList(lambdaQueryWrapper);
        return new Result(faqTypeEntities);
    }


    public Result findDetails(FaqEntity faqEntity) {
        if (com.baomidou.mybatisplus.core.toolkit.StringUtils.isEmpty(faqEntity.getId())) {
            return new Result().setCode(SystemEnum.FAIL).setMsg("id不能为空!");
        }
        Map<String, Object> details = mf.getFaqMapper().findDetails(faqEntity);
        if(StringUtils.isNotEmpty(details)){
            if (details.containsKey("collectStatus")) {
                if (Integer.parseInt(details.get("collectStatus").toString()) > 0) {
                    details.put("collectStatus", "1");
                } else {
                    details.put("collectStatus", "0");
                }
            } else {
                details.put("collectStatus", "0");
            }
            Integer replyCount = mf.getFaqCommentMapper().selectCount(new QueryWrapper<FaqCommentEntity>().eq(StringUtils.isNotEmpty(details.get("id")), "faq_id", details.get("id")));
            details.put("replyCount", replyCount);
        }
        return new Result(details);
    }


    public Result findFaqCommentList(FaqCommentEntity faqEntity) {
        UUserVO webUser = mf.getUserLogInInfo().getWebUser();
        if (com.ymw.love.system.util.StringUtils.isNotEmpty(webUser)) {
            faqEntity.setUserId(webUser.getId());
        }
        if (com.baomidou.mybatisplus.core.toolkit.StringUtils.isEmpty(faqEntity.getId())) {
            return new Result().setCode(SystemEnum.FAIL).setMsg("id不能为空!");
        }
        Page<List<Map<String, Object>>> page = new Page<>(faqEntity.getPageSum(), faqEntity.getPageSize());
        IPage<Map<String, Object>> advisoryCommentList = mf.getFaqMapper().findCommentList(page, faqEntity.getId(), faqEntity.getUserId());
        advisoryCommentList.getRecords().forEach(map -> {
            if (map.containsKey("commentGiveStatus")) {
                if (Integer.parseInt(map.get("commentGiveStatus").toString()) > 0) {
                    map.put("commentGiveStatus", "1");
                } else {
                    map.put("commentGiveStatus", "0");
                }
            } else {
                map.put("commentGiveStatus", "0");
            }
            if (!map.containsKey("nickName")) {
                map.put("nickName", " ");
            }
            if (!map.containsKey("imageUrl")) {
                map.put("imageUrl", " ");
            }
            Integer replyCount = mf.getFaqCommentMapper().selectCount(new QueryWrapper<FaqCommentEntity>().eq("parent_id", map.get("id").toString()));
            List<Map<String, Object>> mapList1 = mf.getFaqMapper().findCommentSubLevelList(map.get("id").toString());
            mapList1.forEach(map1 -> {
                if (!map1.containsKey("nickName")) {
                    map1.put("nickName", " ");
                }
                if (!map1.containsKey("imageUrl")) {
                    map1.put("imageUrl", " ");
                }
            });
            map.put("LevelList1", mapList1);
            map.put("replyCount", replyCount);
        });
        return new Result(advisoryCommentList);
    }


    public Result updateViewCount(FaqEntity faqEntity) {
        mf.getSeeDoctorSender().addFaqView(faqEntity);
        return new Result();
    }


    public Result insFaqComment(FaqCommentEntity faqCommentEntity) throws Exception {
        UUserVO webUser = mf.getUserLogInInfo().getWebUser();
        faqCommentEntity.setUserId(webUser.getId());
        if (StringUtils.isEmpty(faqCommentEntity.getFaqId()) || StringUtils.isEmpty(faqCommentEntity.getCommentContent())) {
            return new Result().setMsg("缺少参数.").setCode(SystemEnum.FAIL);
        }
        List<SensitiveEntity> likeSensitive = mf.getSensitive().findLikeSensitive(faqCommentEntity.getCommentContent());
        if (likeSensitive.size() > 0) {
            throw new Exception("不能带有敏感词,谢谢！");
        }
        faqCommentEntity.setAddTime(LocalDateTime.now());
        boolean insert = faqCommentEntity.insert();
        return new Result().setCode(insert == false ? SystemEnum.FAIL : SystemEnum.SUCCESS).setMsg(insert == false ? "fail" : "success");
    }

    @Transactional(rollbackFor = Exception.class)
    public Result delFaqComment(FaqCommentEntity faqCommentEntity) throws Exception {
        try {
            if (StringUtils.isEmpty(faqCommentEntity.getId())) {
                return new Result().setMsg("缺少参数.").setCode(SystemEnum.FAIL);
            }
            boolean insert = faqCommentEntity.deleteById(faqCommentEntity.getId());
            if (insert) {
                LambdaQueryWrapper<FaqCommentEntity> lambdaQueryWrapper = Wrappers.lambdaQuery();
                lambdaQueryWrapper.eq(FaqCommentEntity::getParentId, faqCommentEntity.getId());
                boolean delete = faqCommentEntity.delete(lambdaQueryWrapper);
            }
            return new Result().setCode(insert == false ? SystemEnum.FAIL : SystemEnum.SUCCESS).setMsg(insert == false ? "fail" : "success");
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }


    public Result delFaqCommentLike(CommentGiveEntity commentGiveEntity) {
        try {
            if (com.baomidou.mybatisplus.core.toolkit.StringUtils.isEmpty(commentGiveEntity.getCommentId())) {
                return new Result().setCode(SystemEnum.FAIL).setMsg("评论ID不能为空!");
            }
            UUserVO webUser = mf.getUserLogInInfo().getWebUser();
            LambdaQueryWrapper<CommentGiveEntity> lambdaQueryWrapper = Wrappers.lambdaQuery();
            lambdaQueryWrapper.eq(CommentGiveEntity::getCommentId, commentGiveEntity.getCommentId()).eq(CommentGiveEntity::getUserId, webUser.getId());
            int insert = mf.getFaqCommentGiveMapper().delete(lambdaQueryWrapper);
            return new Result().setCode(insert > 0 ? SystemEnum.SUCCESS : SystemEnum.FAIL);
        } catch (Exception x) {
            throw new MissRequiredParamException(SystemEnum.FAIL, SystemEnum.FAIL, x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result addFaqCommentLike(CommentGiveEntity commentGiveEntity) {
        try {
            UUserVO webUser = mf.getUserLogInInfo().getWebUser();
            if (com.baomidou.mybatisplus.core.toolkit.StringUtils.isEmpty(commentGiveEntity.getCommentId())) {
                return new Result().setCode(SystemEnum.FAIL).setMsg("评论ID不能为空!");
            }
            commentGiveEntity.setUserId(webUser.getId());
            commentGiveEntity.setAddTime(LocalDateTime.now());
//            Integer userCount = this.baseMapper.findLikeCount(webUser.getId());
//            if (userCount <= 0) {
                int insert = mf.getFaqCommentGiveMapper().insert(commentGiveEntity);
                return new Result().setCode(insert > 0 ? SystemEnum.SUCCESS : SystemEnum.FAIL);
            //}
        } catch (Exception x) {
            throw new MissRequiredParamException(SystemEnum.FAIL, SystemEnum.FAIL, x.getMessage());
        }
    }


    public Result findFaqCommentDetails(FaqCommentEntity faqCommentEntity) {
        UUserVO webUser = mf.getUserLogInInfo().getWebUser();
        if (com.ymw.love.system.util.StringUtils.isNotEmpty(webUser)) {
            faqCommentEntity.setUserId(webUser.getId());
        }
        if (com.baomidou.mybatisplus.core.toolkit.StringUtils.isEmpty(faqCommentEntity.getId())) {
            return new Result().setCode(SystemEnum.FAIL).setMsg("id不能为空!");
        }
        Map<String, Object> advisoryCommentList = mf.getFaqMapper().findCommentById(faqCommentEntity.getId(), faqCommentEntity.getUserId());
        Integer replyCount = mf.getFaqCommentMapper().selectCount(new QueryWrapper<FaqCommentEntity>().eq("parent_id", faqCommentEntity.getId()));
        //AtomicReference<Integer> replyCount = new AtomicReference<>(0);
        if (advisoryCommentList.containsKey("commentGiveStatus")) {
            if (Integer.parseInt(advisoryCommentList.get("commentGiveStatus").toString()) > 0) {
                advisoryCommentList.put("commentGiveStatus", "1");
            } else {
                advisoryCommentList.put("commentGiveStatus", "0");
            }
        } else {
            advisoryCommentList.put("commentGiveStatus", "0");
        }
        if (!advisoryCommentList.containsKey("nickName")) {
            advisoryCommentList.put("nickName", " ");
        }
        if (!advisoryCommentList.containsKey("imageUrl")) {
            advisoryCommentList.put("imageUrl", " ");
        }
        List<Map<String, Object>> commentSubLevelList = mf.getFaqMapper().findCommentSubLevelList(faqCommentEntity.getId());
        commentSubLevelList.forEach(map2 -> {
            if (!map2.containsKey("nickName")) {
                map2.put("nickName", " ");
            }
            if (!map2.containsKey("imageUrl")) {
                map2.put("imageUrl", " ");
            }
        });
        advisoryCommentList.put("LevelList1", commentSubLevelList);
        advisoryCommentList.put("replyCount", replyCount);
        return new Result(advisoryCommentList);
    }

    @Transactional(rollbackFor = Exception.class)
    public Result delFaq(FaqEntity faqEntity) throws Exception{
        try {
            if (StringUtils.isEmpty(faqEntity.getId())) {
                throw new Exception("参数异常.");
            }
            int faq_id = mf.getSpecialMapper().delete(new QueryWrapper<SpecialEntity>().eq("faq_id", faqEntity.getId()));
            if (faq_id > 0) {
                mf.getFaqCommentMapper().delete(new QueryWrapper<FaqCommentEntity>().eq("faq_id", faqEntity.getId()));
                mf.getFaqMapper().deleteById(faqEntity.getId());
                mf.getFaqCollectMapper().delete(new QueryWrapper<FaqCollectEntity>().eq("faq_id", faqEntity.getId()));
            }
            return new Result().setCode(faq_id > 0 ? SystemEnum.SUCCESS : SystemEnum.FAIL);
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result insFaqCollection(FaqCollectEntity faqEntity) throws Exception{
        try {
            if (com.baomidou.mybatisplus.core.toolkit.StringUtils.isEmpty(faqEntity.getFaqId())) {
                return new Result().setCode(SystemEnum.FAIL).setMsg("缺少参数.");
            }
            UUserVO webUser = mf.getUserLogInInfo().getWebUser();
            faqEntity.setUserId(webUser.getId());
            faqEntity.setAddTime(LocalDateTime.now());
            int insert = mf.getFaqCollectMapper().insert(faqEntity);
            return new Result().setCode(insert > 0 ? SystemEnum.SUCCESS : SystemEnum.FAIL);
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result delFaqCollection(FaqCollectEntity faqEntity) throws Exception{
        try {
            if (com.baomidou.mybatisplus.core.toolkit.StringUtils.isEmpty(faqEntity.getFaqId())) {
                return new Result().setCode(SystemEnum.FAIL).setMsg("缺少参数.");
            }
            UUserVO webUser = mf.getUserLogInInfo().getWebUser();
            LambdaQueryWrapper<FaqCollectEntity> lambdaQueryWrapper = Wrappers.lambdaQuery();
            lambdaQueryWrapper.eq(FaqCollectEntity::getFaqId, faqEntity.getFaqId()).eq(FaqCollectEntity::getUserId, webUser.getId());
            int insert = mf.getFaqCollectMapper().delete(lambdaQueryWrapper);
            return new Result().setCode(insert > 0 ? SystemEnum.SUCCESS : SystemEnum.FAIL);
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }


    public Result findMyFaqCollectionList(FaqCollectEntity faqEntity) throws Exception {
        try {
            UUserVO webUser = mf.getUserLogInInfo().getWebUser();
            IPage<FaqCollectEntity> page = new Page<>(faqEntity.getPageSum(), faqEntity.getPageSize());
            IPage<FaqCollectEntity> page1 = mf.getFaqCollectMapper().myCollectList(page, webUser.getId());
            return new Result(page1);
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result delCollectionList(FaqCollectEntity faqEntity) throws Exception{
        try {
            if (com.baomidou.mybatisplus.core.toolkit.StringUtils.isEmpty(faqEntity.getId())) {
                return new Result().setCode(SystemEnum.FAIL).setMsg("缺少参数.");
            }
            UUserVO webUser = mf.getUserLogInInfo().getWebUser();
            int insert = mf.getFaqCollectMapper().deleteById(faqEntity.getId());
            return new Result().setCode(insert > 0 ? SystemEnum.SUCCESS : SystemEnum.FAIL);
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result insShareRecord(FaqShareRecordEntity faqShareRecordEntity) throws Exception{
        try {
            if (com.baomidou.mybatisplus.core.toolkit.StringUtils.isEmpty(faqShareRecordEntity.getFaqId())) {
                return new Result().setCode(SystemEnum.FAIL).setMsg("缺少参数.");
            }
            UUserVO webUser = mf.getUserLogInInfo().getWebUser();
            faqShareRecordEntity.setAddTime(LocalDateTime.now());
            faqShareRecordEntity.setUserId(webUser.getId());
            //Send
            mf.getSeeDoctorSender().addFaqShare(faqShareRecordEntity);
            return new Result();
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result insSpecial(SpecialEntity specialEntity) throws Exception{
        try {
            if (StringUtils.isEmpty(specialEntity.getTitle())) {
                throw new Exception("参数异常.");
            }
            specialEntity.setAddTime(LocalDateTime.now());
            int insert = mf.getSpecialMapper().insert(specialEntity);
            return new Result().setCode(insert > 0 ? SystemEnum.SUCCESS : SystemEnum.FAIL);
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result updSpecial(SpecialEntity specialEntity) throws Exception{
        try {
            if (StringUtils.isEmpty(specialEntity.getId())) {
                throw new Exception("参数异常.");
            }
            boolean insert = specialEntity.updateById();
            return new Result().setCode(insert ? SystemEnum.SUCCESS : SystemEnum.FAIL);
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result delSpecial(SpecialEntity specialEntity) throws Exception{
        try {
            if (StringUtils.isEmpty(specialEntity.getId())) {
                throw new Exception("参数异常.");
            }
            boolean deleteById = specialEntity.deleteById();
            if (deleteById) {
                LambdaQueryWrapper<SpecialEntity> lambdaQueryWrapper = Wrappers.lambdaQuery();
                lambdaQueryWrapper.eq(SpecialEntity::getParentId, specialEntity.getId());
                int delete = mf.getSpecialMapper().delete(lambdaQueryWrapper);
                log.debug(String.valueOf(delete));
            }
            return new Result().setCode(deleteById ? SystemEnum.SUCCESS : SystemEnum.FAIL);
        } catch (Exception x) {
            throw new Exception(x.getLocalizedMessage());
        }
    }

    public Result findDropSpecial(SpecialEntity specialEntity) {
        try {
            List<SpecialEntity> specialEntities = mf.getSpecialMapper().selectList(new QueryWrapper<SpecialEntity>().isNull("parent_id"));
            return new Result().setCode(SystemEnum.SUCCESS).setData(specialEntities);
        } catch (Exception x) {
            throw new MissRequiredParamException(SystemEnum.FAIL, SystemEnum.FAIL, x.getMessage());
        }
    }

    public Result findSpecial(SpecialEntity specialEntity) {
        try {
            IPage<SpecialEntity> special = mf.getFaqMapper().findSpecial(new Page<>(specialEntity.getPageSum(), specialEntity.getPageSize()), specialEntity);
            special.getRecords().forEach(lst -> {
                if (StringUtils.isEmpty(lst.getBrowseCount())) {
                    lst.setBrowseCount(0);
                }
                if (StringUtils.isEmpty(lst.getCoverImg())) {
                    lst.setCoverImg("");
                }
            });
            return new Result().setCode(SystemEnum.SUCCESS).setData(special);
        } catch (Exception x) {
            throw new MissRequiredParamException(SystemEnum.FAIL, SystemEnum.FAIL, x.getMessage());
        }
    }

    public Result findSpecialDetail(SpecialEntity specialEntity) throws Exception{
        try {
            if (StringUtils.isEmpty(specialEntity.getId())) {
                throw new Exception("参数异常.");
            }
            IPage<SpecialEntity> special = mf.getFaqMapper().findSpecialDetail(new Page<>(specialEntity.getPageSum(), specialEntity.getPageSize()), specialEntity);
            special.getRecords().forEach(lst -> {
                if (StringUtils.isEmpty(lst.getBrowseCount())) {
                    lst.setBrowseCount(0);
                }
                if (StringUtils.isEmpty(lst.getIssueContent())) {
                    lst.setIssueContent("");
                }
            });
            return new Result().setCode(SystemEnum.SUCCESS).setData(special);
        } catch (Exception x) {
            throw new Exception(x.getLocalizedMessage());
        }
    }

    public Result findSysFaqList(FaqEntity faqEntity) {
        try {
            IPage<FaqEntity> faqList = mf.getFaqMapper().findSysFaqList(new Page<>(faqEntity.getPageSum(), faqEntity.getPageSize()), faqEntity);
            faqList.getRecords().forEach(lst -> {
                if (StringUtils.isEmpty(lst.getIssueContent())) {
                    lst.setIssueContent("");
                }
                if (StringUtils.isEmpty(lst.getIsRecommend())) {
                    lst.setIsRecommend(0);
                } else {
                    if (lst.getIsRecommend() > 0) {
                        lst.setIsRecommend(1);
                    }
                }

            });
            return new Result().setCode(SystemEnum.SUCCESS).setData(faqList);
        } catch (Exception x) {
            throw new MissRequiredParamException(SystemEnum.FAIL, SystemEnum.FAIL, x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result updSysFaq(FaqEntity faqEntity) throws Exception{
        try {
            if (StringUtils.isEmpty(faqEntity.getId())) {
                throw new Exception("参数异常.");
            }
            LambdaUpdateWrapper<FaqEntity> lambdaUpdateWrapper = Wrappers.lambdaUpdate();
            lambdaUpdateWrapper.set(FaqEntity::getRecommendStatus, faqEntity.getRecommendStatus()).eq(FaqEntity::getId, faqEntity.getId());
            int update = mf.getFaqMapper().update(null, lambdaUpdateWrapper);
            if (update > 0) {
                if (StringUtils.isNotEmpty(faqEntity.getSpecialId()) || StringUtils.isNotEmpty(faqEntity.getTitle())) {
                    List<SpecialEntity> selectList = mf.getSpecialMapper().selectList(new QueryWrapper<SpecialEntity>().eq("faq_id", faqEntity.getId()));
                    if (selectList.size() <=0) {
                        SpecialEntity specialEntity = new SpecialEntity();
                        specialEntity.setAddTime(LocalDateTime.now());
                        specialEntity.setFaqId(faqEntity.getId());
                        specialEntity.setTitle(faqEntity.getTitle());
                        specialEntity.setParentId(faqEntity.getSpecialId());
                        int insert = mf.getSpecialMapper().insert(specialEntity);
                        log.debug(String.valueOf(insert));
                    }
                }
            }
            return new Result().setCode(update > 0 ? SystemEnum.SUCCESS : SystemEnum.FAIL);
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result delSysFaq(FaqEntity faqEntity) throws Exception{
        try {
            if (StringUtils.isEmpty(faqEntity.getId())) {
                throw new Exception("参数异常.");
            }
            int faq_id =mf.getFaqMapper().deleteById(faqEntity.getId());
            if (faq_id > 0) {
                 mf.getSpecialMapper().delete(new QueryWrapper<SpecialEntity>().eq("faq_id", faqEntity.getId()));
                mf.getFaqCommentMapper().delete(new QueryWrapper<FaqCommentEntity>().eq("faq_id", faqEntity.getId()));
                mf.getFaqCollectMapper().delete(new QueryWrapper<FaqCollectEntity>().eq("faq_id", faqEntity.getId()));
            }
            return new Result().setCode(faq_id > 0 ? SystemEnum.SUCCESS : SystemEnum.FAIL);
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    public Result findSysFaqTypeList(FaqTypeEntity faqEntity) {
        try {
            IPage<FaqTypeEntity> faqList = mf.getFaqTypeMapper().selectPage(new Page<>(faqEntity.getPageSum(), faqEntity.getPageSize()), null);
            return new Result().setCode(SystemEnum.SUCCESS).setData(faqList);
        } catch (Exception x) {
            throw new MissRequiredParamException(SystemEnum.FAIL, SystemEnum.FAIL, x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result insType(FaqTypeEntity typeEntity) throws Exception{
        try {
            if (StringUtils.isEmpty(typeEntity.getTypeName())) {
                throw new Exception("参数异常.");
            }
            typeEntity.setAddTime(LocalDateTime.now());
            boolean insert = typeEntity.insert();
            return new Result().setCode(insert ? SystemEnum.SUCCESS : SystemEnum.FAIL);
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result updType(FaqTypeEntity typeEntity) throws Exception{
        try {
            if (StringUtils.isEmpty(typeEntity.getId()) && StringUtils.isEmpty(typeEntity.getTypeName())) {
                throw new Exception("参数异常.");
            }

            boolean insert = typeEntity.updateById();
            return new Result().setCode(insert ? SystemEnum.SUCCESS : SystemEnum.FAIL);
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result delType(FaqTypeEntity typeEntity) throws Exception{
        try {
            if (StringUtils.isEmpty(typeEntity.getId())) {
                throw new Exception("参数异常.");
            }
            int deleteBatchIds = mf.getFaqTypeMapper().deleteBatchIds(Arrays.asList(typeEntity.getId().split(",")));
            return new Result().setCode(deleteBatchIds > 0 ? SystemEnum.SUCCESS : SystemEnum.FAIL);
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }
}
