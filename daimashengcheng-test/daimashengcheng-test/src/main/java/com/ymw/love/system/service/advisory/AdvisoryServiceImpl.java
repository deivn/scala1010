package com.ymw.love.system.service.advisory;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.common.SystemEnum;
import com.ymw.love.system.config.excep.MissRequiredParamException;
import com.ymw.love.system.entity.advisory.*;
import com.ymw.love.system.entity.sensitive.SensitiveEntity;
import com.ymw.love.system.service.BaseService;
import com.ymw.love.system.util.StringUtils;
import com.ymw.love.system.vo.AUserVO;
import com.ymw.love.system.vo.UUserVO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * advisory The business logic implementation class
 *
 * @author zengjuncheng
 * @version 1.1
 * @Creation time  2019/8/8
 */
@SuppressWarnings("ALL")
@Slf4j
@Service
public class AdvisoryServiceImpl extends BaseService {


    @Transactional(rollbackFor = Exception.class)
    public Result insAdvisory(NewsAdvisoryEntity advisoryEntity) {
        try {
            UUserVO webUser = mf.getUserLogInInfo().getWebUser();
            advisoryEntity.setUserId(webUser.getId());
            advisoryEntity.setAddTime(LocalDateTime.now());
            boolean insert = advisoryEntity.insert();
            return new Result().setCode(insert == false ? SystemEnum.FAIL : SystemEnum.SUCCESS).setMsg(insert == false ? "fail" : "success");
        } catch (Exception x) {
            throw new MissRequiredParamException(SystemEnum.FAIL, SystemEnum.FAIL, x.getMessage());
        }
    }

    public Result findMyArticleList(NewsAdvisoryEntity advisoryEntity) {
        UUserVO webUser = mf.getUserLogInInfo().getWebUser();
        LambdaQueryWrapper<NewsAdvisoryEntity> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.select(NewsAdvisoryEntity::getNewsTitle, NewsAdvisoryEntity::getBrowseCount, NewsAdvisoryEntity::getNewsDescribe).eq(NewsAdvisoryEntity::getUserId, 1);
        Page<NewsAdvisoryEntity> page = new Page<>(advisoryEntity.getPageSum(), advisoryEntity.getPageSize());
        IPage<NewsAdvisoryEntity> newsAdvisoryEntityIPage = mf.getAdvisoryMapper().selectPage(page, lambdaQueryWrapper);
        return new Result(newsAdvisoryEntityIPage);
    }

    @Transactional(rollbackFor = Exception.class)
    public Result delMyArticleList(NewsAdvisoryEntity advisoryEntity) throws Exception {
        try {
            if (StringUtils.isEmpty(advisoryEntity.getId())) {
                return new Result().setCode(SystemEnum.FAIL).setMsg("id不能为空!");
            }
            boolean b = advisoryEntity.deleteById();
            if (b) {
                LambdaQueryWrapper<AdvisoryCommentEntity> lambdaQueryWrapper = Wrappers.lambdaQuery();
                lambdaQueryWrapper.eq(AdvisoryCommentEntity::getAdvisoryId, advisoryEntity.getId());
                mf.getAdvisoryCommentMapper().delete(lambdaQueryWrapper);
            }
            return new Result().setCode(b ? SystemEnum.SUCCESS : SystemEnum.FAIL).setMsg(b ? "success" : "fail");
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    public Result updateMyArticleList(NewsAdvisoryEntity advisoryEntity) {
        if (StringUtils.isEmpty(advisoryEntity.getId())) {
            return new Result().setCode(SystemEnum.FAIL).setMsg("id不能为空!");
        }
        LambdaUpdateWrapper<NewsAdvisoryEntity> lambdaUpdateWrapper = Wrappers.lambdaUpdate();
        lambdaUpdateWrapper.set(StringUtils.isNotEmpty(advisoryEntity.getNewsTitle()), NewsAdvisoryEntity::getNewsTitle, advisoryEntity.getNewsTitle())
                .set(StringUtils.isNotEmpty(advisoryEntity.getNewsImg()), NewsAdvisoryEntity::getNewsImg, advisoryEntity.getNewsImg())
                .set(StringUtils.isNotEmpty(advisoryEntity.getNewsDescribe()), NewsAdvisoryEntity::getNewsDescribe, advisoryEntity)
                .eq(StringUtils.isNotEmpty(advisoryEntity.getId()), NewsAdvisoryEntity::getId, advisoryEntity.getId());
        int update = mf.getAdvisoryMapper().update(null, lambdaUpdateWrapper);
        return new Result().setCode(update > 0 ? SystemEnum.SUCCESS : SystemEnum.FAIL).setMsg(update > 0 ? "success" : "fail");
    }

    public Result advisoryList(NewsAdvisoryEntity advisoryEntity) {
        LambdaQueryWrapper<NewsAdvisoryEntity> lambdaQueryWrapper=Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(NewsAdvisoryEntity::getAuditStatus,2).like(StringUtils.isNotEmpty(advisoryEntity.getNewsTitle()),NewsAdvisoryEntity::getNewsTitle,advisoryEntity.getNewsTitle())
                .like(StringUtils.isNotEmpty(advisoryEntity.getTypeName()),NewsAdvisoryEntity::getTypeName,advisoryEntity.getTypeName()).orderByDesc(NewsAdvisoryEntity::getRecommendStatus)
                .orderByDesc(NewsAdvisoryEntity::getAddTime);
        IPage<NewsAdvisoryEntity> newsAdvisoryEntities = mf.getAdvisoryMapper().selectPage(new Page<NewsAdvisoryEntity>(advisoryEntity.getPageSum(), advisoryEntity.getPageSize()), lambdaQueryWrapper);
        newsAdvisoryEntities.getRecords().forEach(news -> {
            if (StringUtils.isEmpty(news.getNewsImg())) {
                news.setNewsImg("");
            }
            if (StringUtils.isEmpty(news.getAuthor())) {
                news.setAuthor("");
            }
            if (StringUtils.isEmpty(news.getCoverImg())) {
                news.setCoverImg("");
            }
        });
        return new Result(newsAdvisoryEntities);
    }

    public Result advisorySearchList(NewsAdvisoryEntity advisoryEntity) {
        LambdaQueryWrapper<NewsAdvisoryEntity> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.select(NewsAdvisoryEntity::getNewsTitle, NewsAdvisoryEntity::getId)
                .eq(NewsAdvisoryEntity::getAuditStatus, 2).like(StringUtils.isNotEmpty(advisoryEntity.getNewsTitle()), NewsAdvisoryEntity::getNewsTitle, advisoryEntity.getNewsTitle());
        List<NewsAdvisoryEntity> newsAdvisoryEntities = mf.getAdvisoryMapper().selectList(lambdaQueryWrapper);
        return new Result(newsAdvisoryEntities);
    }

    public Result findTypeList(AdvisoryTypeEntity typeEntity) {
        LambdaQueryWrapper<AdvisoryTypeEntity> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.select(AdvisoryTypeEntity::getId, AdvisoryTypeEntity::getTypeName).eq(AdvisoryTypeEntity::getTypeStatus, 1);
        List<AdvisoryTypeEntity> advisoryTypeEntities = mf.getAdvisoryTypeMapper().selectList(lambdaQueryWrapper);
        return new Result(advisoryTypeEntities);
    }

    @Transactional(rollbackFor = Exception.class)
    public Result delGive(AdvisoryGiveEntity advisoryGiveEntity) throws Exception {
        try {
            if (StringUtils.isEmpty(advisoryGiveEntity.getAdvisoryId())) {
                return new Result().setCode(SystemEnum.FAIL).setMsg("咨询不能为空!");
            }
            UUserVO webUser = mf.getUserLogInInfo().getWebUser();
            LambdaQueryWrapper<AdvisoryGiveEntity> lambdaQueryWrapper = Wrappers.lambdaQuery();
            lambdaQueryWrapper.eq(AdvisoryGiveEntity::getAdvisoryId, advisoryGiveEntity.getAdvisoryId()).eq(AdvisoryGiveEntity::getUserId, webUser.getId());
            int insert = mf.getAdvisoryGiveMapper().delete(lambdaQueryWrapper);
            return new Result().setCode(insert > 0 ? SystemEnum.SUCCESS : SystemEnum.FAIL).setData(insert);
        } catch (Exception x) {
            throw new MissRequiredParamException(SystemEnum.FAIL, SystemEnum.FAIL, x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result delLikeCount(AdvisoryCommentGiveEntity advisoryCommentGiveEntity) throws Exception{
        try {
            if (StringUtils.isEmpty(advisoryCommentGiveEntity.getCommentId())) {
                throw new Exception("评论ID不能为空!");

            }
            UUserVO webUser = mf.getUserLogInInfo().getWebUser();
            LambdaQueryWrapper<AdvisoryCommentGiveEntity> lambdaQueryWrapper = Wrappers.lambdaQuery();
            lambdaQueryWrapper.eq(AdvisoryCommentGiveEntity::getCommentId, advisoryCommentGiveEntity.getCommentId()).eq(AdvisoryCommentGiveEntity::getUserId, webUser.getId());
            int insert = mf.getCommentGiveMapper().delete(lambdaQueryWrapper);
            return new Result().setCode(insert > 0 ? SystemEnum.SUCCESS : SystemEnum.FAIL);
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public Result addLikeCount(AdvisoryCommentGiveEntity advisoryCommentGiveEntity) throws Exception{
        try {
            UUserVO webUser = mf.getUserLogInInfo().getWebUser();
            if (StringUtils.isEmpty(advisoryCommentGiveEntity.getCommentId())) {
                throw new Exception("评论ID不能为空!");
            }
            advisoryCommentGiveEntity.setUserId(webUser.getId());
            advisoryCommentGiveEntity.setAddTime(LocalDateTime.now());
            //Integer userCount = advisoryCommentMapper.findLikeCount(webUser.getId());
            //if (userCount <= 0) {
            int insert = mf.getCommentGiveMapper().insert(advisoryCommentGiveEntity);
            return new Result().setCode(insert > 0 ? SystemEnum.SUCCESS : SystemEnum.FAIL).setData(insert);
            //}
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result addBrowseCount(NewsAdvisoryEntity newsAdvisoryEntity) throws Exception{
        try {
            if (StringUtils.isEmpty(newsAdvisoryEntity.getId())) {
                throw new Exception("id不能为空!");
            }
            mf.getSeeDoctorSender().addAdvisoryView(newsAdvisoryEntity);
            return new Result();
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }
    public Result findAdvisoryDetails(NewsAdvisoryEntity newsAdvisoryEntity) {
        UUserVO webUser = mf.getUserLogInInfo().getWebUser();
        if (com.ymw.love.system.util.StringUtils.isNotEmpty(webUser)){
            newsAdvisoryEntity.setUserId(webUser.getId());
        }
        if (StringUtils.isEmpty(newsAdvisoryEntity.getId())) {
            return new Result().setCode(SystemEnum.FAIL).setMsg("id不能为空!");
        }
        Map<String, Object> details = mf.getAdvisoryMapper().findDetails(newsAdvisoryEntity);
        if(details==null || details.size()<=0) {
        	return  new Result();
        }
        
        if (!details.containsKey("newsImg")) {
            details.put("newsImg", "");
        }
        if (!details.containsKey("newsDescribe")) {
            details.put("newsDescribe", "");
        }
        if (details.containsKey("collectStatus")) {
            if (Integer.parseInt(details.get("collectStatus").toString()) > 0) {
                details.put("collectStatus", "1");
            } else {
                details.put("collectStatus", "0");
            }
        } else {
            details.put("collectStatus", "0");
        }
        if (details.containsKey("advisoryGiveStatus")) {
            if (Integer.parseInt(details.get("advisoryGiveStatus").toString()) > 0) {
                details.put("advisoryGiveStatus", "1");
            } else {
                details.put("advisoryGiveStatus", "0");
            }
        } else {
            details.put("advisoryGiveStatus", "0");
        }
        return new Result(details);
    }


    @Transactional(rollbackFor = Exception.class)
    public Result insAdvisoryComment(AdvisoryCommentEntity advisoryCommentEntity) throws Exception {
        List<SensitiveEntity> likeSensitive = mf.getSensitive().findLikeSensitive(advisoryCommentEntity.getCommentContent());
        if (likeSensitive.size() > 0) {
            throw new Exception("不能带有敏感词,谢谢！");
        }
        UUserVO webUser = mf.getUserLogInInfo().getWebUser();
        advisoryCommentEntity.setUserId(webUser.getId());
        if (com.ymw.love.system.util.StringUtils.isEmpty(advisoryCommentEntity.getAdvisoryId()) || com.ymw.love.system.util.StringUtils.isEmpty(advisoryCommentEntity.getCommentContent())) {
            return new Result().setMsg("缺少参数.").setCode(SystemEnum.FAIL);
        }
        advisoryCommentEntity.setAddTime(LocalDateTime.now());
        int insert = mf.getAdvisoryCommentMapper().insert(advisoryCommentEntity);
        return new Result().setCode(insert <= 0 ? SystemEnum.FAIL : SystemEnum.SUCCESS).setMsg(insert <= 0 ? "fail" : "success");
    }

    public Result findAdvisoryCommentDetails(AdvisoryCommentEntity advisoryCommentEntity) {
        UUserVO webUser = mf.getUserLogInInfo().getWebUser();
        if (com.ymw.love.system.util.StringUtils.isNotEmpty(webUser)){
            advisoryCommentEntity.setUserId(webUser.getId());
        }
        if (StringUtils.isEmpty(advisoryCommentEntity.getId())) {
            return new Result().setCode(SystemEnum.FAIL).setMsg("id不能为空!");
        }

        Map<String, Object> advisoryCommentList = mf.getAdvisoryCommentMapper().findAdvisoryCommentDetails(advisoryCommentEntity.getId(), advisoryCommentEntity.getUserId());
        if (!advisoryCommentList.containsKey("nickName")) {
            advisoryCommentList.put("nickName", " ");
        }
        if (!advisoryCommentList.containsKey("imageUrl")) {
            advisoryCommentList.put("imageUrl", " ");
        }
        if (advisoryCommentList.containsKey("commentGiveStatus")) {
            if (Integer.parseInt(advisoryCommentList.get("commentGiveStatus").toString()) > 0) {
                advisoryCommentList.put("commentGiveStatus", "1");
            } else {
                advisoryCommentList.put("commentGiveStatus", "0");
            }
        } else {
            advisoryCommentList.put("commentGiveStatus", "0");
        }
        Integer replyCount1 = mf.getAdvisoryCommentMapper().selectCount(new QueryWrapper<AdvisoryCommentEntity>().eq("parent_id", advisoryCommentList.get("id").toString()));
        List<Map<String, Object>> mapList1 = mf.getAdvisoryCommentMapper().findAdvisoryCommentZiJi(advisoryCommentList.get("id").toString());
        mapList1.forEach(map1 -> {
            if (!map1.containsKey("nickName")) {
                map1.put("nickName", " ");
            }
            if (!map1.containsKey("imageUrl")) {
                map1.put("imageUrl", " ");
            }
        });
        advisoryCommentList.put("LevelList1", mapList1);
        advisoryCommentList.put("replyCount", replyCount1);
        return new Result(advisoryCommentList);
    }

    public Result findAdvisoryCommentList(AdvisoryCommentEntity advisoryCommentEntity) {
        UUserVO webUser = mf.getUserLogInInfo().getWebUser();
        if (com.ymw.love.system.util.StringUtils.isNotEmpty(webUser)){
            advisoryCommentEntity.setUserId(webUser.getId());
        }
        if (StringUtils.isEmpty(advisoryCommentEntity.getId())) {
            return new Result().setCode(SystemEnum.FAIL).setMsg("id不能为空!");
        }
        Page<List<Map<String, Object>>> page = new Page<>(advisoryCommentEntity.getPageSum(), advisoryCommentEntity.getPageSize());
        IPage<Map<String, Object>> advisoryCommentList = mf.getAdvisoryCommentMapper().findAdvisoryCommentList(page, advisoryCommentEntity.getId(), advisoryCommentEntity.getUserId());
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
            Integer replyCount = mf.getAdvisoryCommentMapper().selectCount(new QueryWrapper<AdvisoryCommentEntity>().eq("parent_id", map.get("id").toString()));
            List<Map<String, Object>> mapList1 = mf.getAdvisoryCommentMapper().findAdvisoryCommentZiJi(map.get("id").toString());
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

    @Transactional(rollbackFor = Exception.class)
    public Result insCollection(AdvisoryCollect advisoryCollect) throws Exception {
        try {
            if (StringUtils.isEmpty(advisoryCollect.getAdvisoryId())) {
                return new Result().setMsg("缺少参数.").setCode(SystemEnum.FAIL);
            }

            UUserVO webUser = mf.getUserLogInInfo().getWebUser();
            advisoryCollect.setUserId(webUser.getId());
            advisoryCollect.setAddTime(LocalDateTime.now());
            int insert = mf.getAdvisoryCollectMapper().insert(advisoryCollect);
            return new Result().setCode(insert <=0 ? SystemEnum.FAIL : SystemEnum.SUCCESS).setMsg(insert <=0 ? "fail" : "success");
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result insShareRecord(ShareRecordEntity shareRecordEntity) throws Exception {
        try {
            if (StringUtils.isEmpty(shareRecordEntity.getAdvisoryId())) {
                return new Result().setMsg("缺少参数.").setCode(SystemEnum.FAIL);
            }
            UUserVO webUser = mf.getUserLogInInfo().getWebUser();
            shareRecordEntity.setUserId(webUser.getId());
            shareRecordEntity.setAddTime(LocalDateTime.now());
            mf.getSeeDoctorSender().addAdvisoryShare(shareRecordEntity);
            return new Result();
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result delCollection(AdvisoryCollect advisoryCollect) throws Exception {
        if (com.ymw.love.system.util.StringUtils.isEmpty(advisoryCollect.getAdvisoryId())) {
            return new Result().setMsg("缺少参数.").setCode(SystemEnum.FAIL);
        }
        UUserVO webUser = mf.getUserLogInInfo().getWebUser();
        advisoryCollect.setUserId(webUser.getId());
        LambdaQueryWrapper<AdvisoryCollect> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(AdvisoryCollect::getAdvisoryId, advisoryCollect.getAdvisoryId()).eq(AdvisoryCollect::getUserId, advisoryCollect.getUserId());
        int delete = mf.getAdvisoryCollectMapper().delete(lambdaQueryWrapper);
        return new Result().setCode(delete <= 0 ? SystemEnum.FAIL : SystemEnum.SUCCESS).setMsg(delete <= 0 ? "fail" : "success");
    }

    public Result findMyCollectionList(AdvisoryCollect advisoryCollect) throws Exception {
        try {
            UUserVO webUser = mf.getUserLogInInfo().getWebUser();
            IPage<NewsAdvisoryEntity> page = new Page<>(advisoryCollect.getPageSum(), advisoryCollect.getPageSize());
            IPage<NewsAdvisoryEntity> page1 = mf.getAdvisoryCollectMapper().myCollectList(page, webUser.getId());
            return new Result(page1);
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result deleteCollection(AdvisoryCollect advisoryCollect) throws Exception {
        try {
            if (StringUtils.isEmpty(advisoryCollect.getId())) {
                return new Result().setMsg("缺少参数.").setCode(SystemEnum.FAIL);
            }
            int deleteById = mf.getAdvisoryCollectMapper().deleteById(advisoryCollect.getId());
            return new Result().setCode(deleteById <=0 ? SystemEnum.FAIL : SystemEnum.SUCCESS).setMsg(deleteById <=0 ? "fail" : "success");
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result addLike(AdvisoryGiveEntity advisoryGiveEntity) throws Exception {
        try {
            if (StringUtils.isEmpty(advisoryGiveEntity.getAdvisoryId())) {
                return new Result().setMsg("缺少参数.").setCode(SystemEnum.FAIL);
            }
            UUserVO webUser = mf.getUserLogInInfo().getWebUser();
            advisoryGiveEntity.setUserId(webUser.getId());
            advisoryGiveEntity.setAddTime(LocalDateTime.now());
            int insert = mf.getAdvisoryGiveMapper().insert(advisoryGiveEntity);
            return new Result().setCode(insert <=0 ? SystemEnum.FAIL : SystemEnum.SUCCESS).setMsg(insert <=0 ? "fail" : "success");
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result delComment(AdvisoryCommentEntity advisoryCommentEntity) throws Exception {
        try {
            if (StringUtils.isEmpty(advisoryCommentEntity.getId())) {
                return new Result().setMsg("缺少参数.").setCode(SystemEnum.FAIL);
            }
            int i = mf.getAdvisoryCommentMapper().deleteById(advisoryCommentEntity.getId());
            if (i > 0) {
                LambdaQueryWrapper<AdvisoryCommentEntity> lambdaQueryWrapper = Wrappers.lambdaQuery();
                lambdaQueryWrapper.eq(AdvisoryCommentEntity::getParentId, advisoryCommentEntity.getParentId());
                mf.getAdvisoryCommentMapper().delete(lambdaQueryWrapper);
            }
            return new Result().setCode(i <=0 ? SystemEnum.FAIL : SystemEnum.SUCCESS).setMsg(i <=0 ? "fail" : "success");
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    public Result findSysAdvisoryList(NewsAdvisoryEntity advisoryEntity) throws Exception {
        try {
            IPage<NewsAdvisoryEntity> page = new Page<>(advisoryEntity.getPageSum(), advisoryEntity.getPageSize());
            if (StringUtils.isEmpty(advisoryEntity.getId())){
                advisoryEntity.setAuditStatus(1);
            }
            IPage<NewsAdvisoryEntity> newsAdvisoryEntities = mf.getAdvisoryMapper().findSysAdvisoryConsultingList(page, advisoryEntity);
            newsAdvisoryEntities.getRecords().forEach(lis -> {
                if (StringUtils.isEmpty(lis.getAuthor())) {
                    lis.setAuthor("");
                }
            });
            return new Result(newsAdvisoryEntities);
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    public Result findAdvisoryRefusedList(NewsAdvisoryEntity advisoryEntity) throws Exception {
        try {
            IPage<NewsAdvisoryEntity> page = new Page<>(advisoryEntity.getPageSum(), advisoryEntity.getPageSize());
            advisoryEntity.setAuditStatus(3);
            IPage<NewsAdvisoryEntity> newsAdvisoryEntities = mf.getAdvisoryMapper().findSysAdvisoryAuditList(page, advisoryEntity);
            newsAdvisoryEntities.getRecords().forEach(lis -> {
                if (StringUtils.isEmpty(lis.getAuthor())) {
                    lis.setAuthor("");
                }
            });
            return new Result(newsAdvisoryEntities);
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    public Result findAdvisoryPassList(NewsAdvisoryEntity advisoryEntity) throws Exception {
        try {
            IPage<NewsAdvisoryEntity> page = new Page<>(advisoryEntity.getPageSum(), advisoryEntity.getPageSize());
            advisoryEntity.setAuditStatus(2);
            IPage<NewsAdvisoryEntity> newsAdvisoryEntities = mf.getAdvisoryMapper().findSysAdvisoryAuditList(page, advisoryEntity);
            newsAdvisoryEntities.getRecords().forEach(lis -> {
                if (StringUtils.isEmpty(lis.getAuthor())) {
                    lis.setAuthor("");
                }
                Integer commentCount = mf.getAdvisoryCommentMapper().selectCount(new QueryWrapper<AdvisoryCommentEntity>().eq("advisory_id", lis.getId()));
                lis.setCommentCount(commentCount);
                Integer giveCount = mf.getAdvisoryGiveMapper().selectCount(new QueryWrapper<AdvisoryGiveEntity>().eq("advisory_id", lis.getId()));
                lis.setGiveCount(giveCount);
                Integer shareRecordCount = mf.getShareRecordMapper().selectCount(new QueryWrapper<ShareRecordEntity>().eq("advisory_id", lis.getId()));
                Integer collectCount = mf.getAdvisoryCollectMapper().selectCount(new QueryWrapper<AdvisoryCollect>().eq("advisory_id", lis.getId()));
                lis.setCollectCount(collectCount);

            });
            return new Result(newsAdvisoryEntities);
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result insSysAdvisory(NewsAdvisoryEntity newsAdvisoryEntity) throws Exception {
        try {
            boolean insert=true;
            if (com.ymw.love.system.util.StringUtils.isEmpty(newsAdvisoryEntity.getDraftsStatus())){
                throw new Exception("参数异常.");
            }
            newsAdvisoryEntity.setAddTime(LocalDateTime.now());
            newsAdvisoryEntity.setStatus(1);
            if (StringUtils.isNotEmpty(newsAdvisoryEntity.getId())){
                NewsAdvisoryEntity newsAdvisoryEntity1 = newsAdvisoryEntity.selectById();
                if (StringUtils.isNotEmpty(newsAdvisoryEntity1)){
                    newsAdvisoryEntity.setAuditStatus(1);
                    newsAdvisoryEntity.updateById();
                }
            }else{
                insert= newsAdvisoryEntity.insert();
            }
            return new Result(insert ? SystemEnum.SUCCESS : SystemEnum.FAIL).setMsg(insert ? "success" : "fail");
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public Result updSysAdvisory(NewsAdvisoryEntity newsAdvisoryEntity) throws Exception {
        try {
            if (StringUtils.isEmpty(newsAdvisoryEntity.getId())
                    || StringUtils.isEmpty(newsAdvisoryEntity.getNewsTitle())
                    || StringUtils.isEmpty(newsAdvisoryEntity.getNewsDescribe())){
                throw new Exception("参数异常.");
            }
            newsAdvisoryEntity.setAddTime(LocalDateTime.now());
            newsAdvisoryEntity.setStatus(1);
            newsAdvisoryEntity.setAuditStatus(1);
            boolean insert = newsAdvisoryEntity.updateById();
            return new Result(insert ? SystemEnum.SUCCESS : SystemEnum.FAIL).setMsg(insert ? "success" : "fail");
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result delSysAdvisory(NewsAdvisoryEntity newsAdvisoryEntity) throws Exception {
        try {
            if (StringUtils.isEmpty(newsAdvisoryEntity.getId())){
                throw new Exception("参数异常.");
            }
            boolean insert = newsAdvisoryEntity.deleteById();
            return new Result(insert ? SystemEnum.SUCCESS : SystemEnum.FAIL).setMsg(insert ? "success" : "fail");
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result updateSysAdvisoryAudit(NewsAdvisoryEntity newsAdvisoryEntity) throws Exception {
        try {
            if (StringUtils.isEmpty(newsAdvisoryEntity.getId())) {
                throw new Exception("参数异常.");
            }
            if (com.ymw.love.system.util.StringUtils.isEmpty(newsAdvisoryEntity.getAuditStatus())) {
                throw new Exception("参数异常.");
            }
            AUserVO webUser = mf.getUserLogInInfo().getAdminUser();
            newsAdvisoryEntity.setAuditor(webUser.getId());
            String[] listId = newsAdvisoryEntity.getId().split(",");
            Integer integer = mf.getAdvisoryMapper().updateSysAdvisoryAudit(newsAdvisoryEntity.getAuditReason(), newsAdvisoryEntity.getAuditStatus(), newsAdvisoryEntity.getAuthor(), listId);
            return new Result(integer > 0 ? SystemEnum.SUCCESS : SystemEnum.FAIL).setMsg(integer > 0 ? "success" : "fail");
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    public Result findAdvisoryCommentsList(AdvisoryCommentEntity advisoryCommentEntity) throws Exception {
        try {
            IPage<AdvisoryCommentEntity> page = new Page<>(advisoryCommentEntity.getPageSum(), advisoryCommentEntity.getPageSize());
            IPage<AdvisoryCommentEntity> newsAdvisoryEntities = mf.getAdvisoryMapper().findAdvisoryCommentsList(page, advisoryCommentEntity);
            newsAdvisoryEntities.getRecords().forEach(lis -> {
                if (StringUtils.isEmpty(lis.getName())) {
                    lis.setName("");
                }
            });
            return new Result(newsAdvisoryEntities);
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public Result delSysComments(AdvisoryCommentEntity advisoryCommentEntity) throws Exception {
        try {
            if (com.ymw.love.system.util.StringUtils.isEmpty(advisoryCommentEntity.getId())){
                throw new Exception("参数异常.");
            }
            int deleteBatchIds = mf.getAdvisoryCommentMapper().deleteBatchIds(Arrays.asList(advisoryCommentEntity.getId().split(",")));
            return new Result(deleteBatchIds >0 ? SystemEnum.SUCCESS : SystemEnum.FAIL).setMsg(deleteBatchIds >0 ? "success" : "fail").setData(deleteBatchIds);
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    public Result findAdvisoryCategoryList(AdvisoryTypeEntity advisoryTypeEntity) throws Exception {
        try {
            IPage<AdvisoryTypeEntity> page = new Page<>(advisoryTypeEntity.getPageSum(), advisoryTypeEntity.getPageSize());
            LambdaQueryWrapper<AdvisoryTypeEntity> lambdaQueryWrapper=Wrappers.lambdaQuery();
            lambdaQueryWrapper.eq(AdvisoryTypeEntity::getTypeStatus,1);
            IPage<AdvisoryTypeEntity> newsAdvisoryEntities = mf.getAdvisoryTypeMapper().selectPage(page,lambdaQueryWrapper);
            return new Result(newsAdvisoryEntities);
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public Result insSysCategory(AdvisoryTypeEntity advisoryTypeEntity) throws Exception {
        try {
            if (com.ymw.love.system.util.StringUtils.isEmpty(advisoryTypeEntity.getTypeName())){
                throw new Exception("参数异常.");
            }
            advisoryTypeEntity.setAddTime(LocalDateTime.now());
            int insert = mf.getAdvisoryTypeMapper().insert(advisoryTypeEntity);
            return new Result(insert >0 ? SystemEnum.SUCCESS : SystemEnum.FAIL).setMsg(insert >0 ? "success" : "fail").setData(insert);
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public Result delSysCategory(AdvisoryTypeEntity advisoryTypeEntity) throws Exception {
        try {
            if (com.ymw.love.system.util.StringUtils.isEmpty(advisoryTypeEntity.getId())){
                throw new Exception("参数异常.");
            }
            int deleteBatchIds=0;
            String [] ids=advisoryTypeEntity.getId().split(",");
            for(int i=0;i<ids.length;i++){
                deleteBatchIds= mf.getAdvisoryTypeMapper().deleteById(ids[i]);
            }
            return new Result(deleteBatchIds >0 ? SystemEnum.SUCCESS : SystemEnum.FAIL).setMsg(deleteBatchIds >0 ? "success" : "fail").setData(deleteBatchIds);
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result updSysCategory(AdvisoryTypeEntity advisoryTypeEntity) throws Exception {
        try {
            if (com.ymw.love.system.util.StringUtils.isEmpty(advisoryTypeEntity.getId())){
                throw new Exception("参数异常.");
            }
            if (com.ymw.love.system.util.StringUtils.isEmpty(advisoryTypeEntity.getTypeName())){
                throw new Exception("参数异常.");
            }
            int updateById = mf.getAdvisoryTypeMapper().updateById(advisoryTypeEntity);
            return new Result(updateById >0 ? SystemEnum.SUCCESS : SystemEnum.FAIL).setMsg(updateById >0 ? "success" : "fail").setData(updateById);
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result insSysAdvisoryDrafts(AdvisoryDraftsEntity draftsEntity) throws Exception {
        try {
            draftsEntity.setSaveTime(LocalDateTime.now());
            boolean insert = draftsEntity.insert();
            return new Result(insert ? SystemEnum.SUCCESS : SystemEnum.FAIL).setMsg(insert ? "success" : "fail");
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result updSysAdvisoryDrafts(AdvisoryDraftsEntity draftsEntity) throws Exception {
        try {
            if (com.ymw.love.system.util.StringUtils.isEmpty(draftsEntity.getId())){
                throw new Exception("参数异常.");
            }
            draftsEntity.setSaveTime(LocalDateTime.now());
            boolean insert = draftsEntity.updateById();
            return new Result(insert ? SystemEnum.SUCCESS : SystemEnum.FAIL).setMsg(insert ? "success" : "fail");
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

    public Result findAdvisoryDraftsList(AdvisoryDraftsEntity draftsEntity) throws Exception {
        try {
            IPage<AdvisoryDraftsEntity> page = new Page<>(draftsEntity.getPageSum(), draftsEntity.getPageSize());
            IPage<AdvisoryDraftsEntity> newsAdvisoryEntities = mf.getAdvisoryMapper().findAdvisoryDraftsList(page, draftsEntity);
            newsAdvisoryEntities.getRecords().forEach(lis -> {
                if (StringUtils.isEmpty(lis.getAuthor())) {
                    lis.setAuthor("");
                }
            });
            return new Result(newsAdvisoryEntities);
        } catch (Exception x) {
            throw new Exception(x.getMessage());
        }
    }

}
