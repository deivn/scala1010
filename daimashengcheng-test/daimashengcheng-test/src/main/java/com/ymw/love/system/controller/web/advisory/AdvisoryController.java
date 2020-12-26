package com.ymw.love.system.controller.web.advisory;

import com.ymw.love.system.common.Resource;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.common.SystemEnum;
import com.ymw.love.system.config.intercept.Authority;
import com.ymw.love.system.entity.advisory.*;
import com.ymw.love.system.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author zengjuncheng
 * @version 1.1
 * @describe advisory control layer
 * @Creation time  2019/8/8
 * @Contact information:953262399
 */
@RestController
@RequestMapping("/web/advisory")
@Slf4j
public class AdvisoryController extends BaseService {

  

    /**
     * Release advisory
     *
     * @param advisoryEntity
     * @param bindingResult
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @PostMapping("/insert")
    public Result insFaq(@RequestBody @Valid NewsAdvisoryEntity advisoryEntity, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldError().getDefaultMessage();
            return new Result().setMsg(errorMsg).setCode(SystemEnum.FAIL);
        }
        return sf.getAdvisoryService().insAdvisory(advisoryEntity);
    }

    /**
     * advisory Search for fuzzy matching list
     *
     * @param advisoryEntity
     * @return Result
     */
    @GetMapping("/search/list")
    public Result advisorySearchList(NewsAdvisoryEntity advisoryEntity) {
        return sf.getAdvisoryService().advisorySearchList(advisoryEntity);
    }

    /**
     * advisory  list
     *
     * @param advisoryEntity
     * @return Result
     */
    @GetMapping("/list")
    public Result advisoryList(NewsAdvisoryEntity advisoryEntity) {
        return sf.getAdvisoryService().advisoryList(advisoryEntity);
    }

    /**
     * my article  list
     *
     * @param advisoryEntity
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @GetMapping("/my/article")
    public Result findMyArticleList(NewsAdvisoryEntity advisoryEntity) {
        return sf.getAdvisoryService().findMyArticleList(advisoryEntity);
    }

    /**
     * my article  delete
     *
     * @param advisoryEntity
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @PostMapping("/del/article")
    public Result delMyArticleList(@RequestBody NewsAdvisoryEntity advisoryEntity) throws Exception {
        return sf.getAdvisoryService().delMyArticleList(advisoryEntity);
    }

    /**
     * my article  update
     *
     * @param advisoryEntity
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @PostMapping("/update/article")
    public Result updateMyArticleList(@RequestBody NewsAdvisoryEntity advisoryEntity) {
        return sf.getAdvisoryService().updateMyArticleList(advisoryEntity);
    }

    /**
     * advisory type list
     *
     * @param typeEntity
     * @return Result
     */
    @GetMapping("/type/lis")
    public Result findTypeList(AdvisoryTypeEntity typeEntity) {
        return sf.getAdvisoryService().findTypeList(typeEntity);
    }

    /**
     * advisory add browse count
     *
     * @param advisoryEntity
     * @return Result
     */
    @PostMapping("/add/browse/count")
    public Result addBrowseCount(@RequestBody NewsAdvisoryEntity advisoryEntity) throws Exception {
        return sf.getAdvisoryService().addBrowseCount(advisoryEntity);
    }

    /**
     * advisory add give a like(comments)
     *
     * @param advisoryCommentGiveEntity
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @PostMapping("/add/like/count")
    public Result addLikeCount(@RequestBody AdvisoryCommentGiveEntity advisoryCommentGiveEntity) throws Exception {
        return sf.getAdvisoryService().addLikeCount(advisoryCommentGiveEntity);
    }

    /**
     * advisory delete give a like
     *
     * @param advisoryCommentGiveEntity
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @PostMapping("/del/like")
    public Result delLikeCount(@RequestBody AdvisoryCommentGiveEntity advisoryCommentGiveEntity) throws Exception {
        return sf.getAdvisoryService().delLikeCount(advisoryCommentGiveEntity);
    }

    /**
     * advisory add give a like
     *
     * @param advisoryGiveEntity
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @PostMapping("/add/like")
    public Result addLike(@RequestBody AdvisoryGiveEntity advisoryGiveEntity) throws Exception {
        return sf.getAdvisoryService().addLike(advisoryGiveEntity);
    }

    /**
     * advisory del give a like
     *
     * @param advisoryGiveEntity
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @PostMapping("/del/give")
    public Result delGive(@RequestBody AdvisoryGiveEntity advisoryGiveEntity) throws Exception {
        return sf.getAdvisoryService().delGive(advisoryGiveEntity);
    }

    /**
     * find Advisory details
     *
     * @return Result
     */
    @Authority(Resource.enter.web_user_dispensable)
    @GetMapping("/find/details")
    public Result findAdvisoryDetails(NewsAdvisoryEntity advisoryEntity) {
        return sf.getAdvisoryService().findAdvisoryDetails(advisoryEntity);
    }

    /**
     * Advisory Comment
     *
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @PostMapping("/ins/comment")
    public Result insAdvisoryComment(@RequestBody AdvisoryCommentEntity advisoryCommentEntity) throws Exception{
        return sf.getAdvisoryService().insAdvisoryComment(advisoryCommentEntity);
    }

    /**
     * Advisory Comment list
     *
     * @return Result
     */
    @Authority(Resource.enter.web_user_dispensable)
    @GetMapping("/comment/list")
    public Result findAdvisoryCommentList(AdvisoryCommentEntity advisoryCommentEntity) {
        return sf.getAdvisoryService().findAdvisoryCommentList(advisoryCommentEntity);
    }

    /**
     * Advisory Comment details
     *
     * @return Result
     */
    @Authority(Resource.enter.web_user_dispensable)
    @GetMapping("/comment/details")
    public Result findAdvisoryCommentDetails(AdvisoryCommentEntity advisoryCommentEntity) {
        return sf.getAdvisoryService().findAdvisoryCommentDetails(advisoryCommentEntity);
    }

    /**
     * advisory collection
     *
     * @param advisoryCollect
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @PostMapping("/ins/collection")
    public Result insCollection(@RequestBody AdvisoryCollect advisoryCollect) throws Exception {
        return sf.getAdvisoryService().insCollection(advisoryCollect);
    }

    /**
     * advisory collection delete
     *
     * @param advisoryCollect
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @PostMapping("/del/collection")
    public Result delCollection(@RequestBody AdvisoryCollect advisoryCollect) throws Exception {
        return sf.getAdvisoryService().delCollection(advisoryCollect);
    }

    /**
     * my advisory collection list
     *
     * @param advisoryCollect
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @GetMapping("/find/my/collection/list")
    public Result findMyCollectionList(AdvisoryCollect advisoryCollect) throws Exception {
        return sf.getAdvisoryService().findMyCollectionList(advisoryCollect);
    }

    /**
     * my advisory collection delete
     *
     * @param advisoryCollect
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @PostMapping("/delete/collection")
    public Result deleteCollection(@RequestBody AdvisoryCollect advisoryCollect) throws Exception {
        return sf.getAdvisoryService().insCollection(advisoryCollect);
    }

    /**
     * advisory share record
     *
     * @param shareRecordEntity
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @PostMapping("/share/record")
    public Result insShareRecord(@RequestBody ShareRecordEntity shareRecordEntity) throws Exception {
        return sf.getAdvisoryService().insShareRecord(shareRecordEntity);
    }

    /**
     * advisory comment delete
     *
     * @param advisoryCommentEntity
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @PostMapping("/del/delete")
    public Result delComment(@RequestBody AdvisoryCommentEntity advisoryCommentEntity) throws Exception {
        return sf.getAdvisoryService().delComment(advisoryCommentEntity);
    }

}
