package com.ymw.love.system.controller.web.faq;

import com.ymw.love.system.common.Resource;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.common.SystemEnum;
import com.ymw.love.system.config.intercept.Authority;
import com.ymw.love.system.entity.faq.*;
import com.ymw.love.system.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author zengjuncheng
 * @version 1.1
 * @describe Q&a control layer
 * @Creation time  2019/8/2
 * @Contact information:953262399
 */
@RestController
@RequestMapping("/web/faq")
@Slf4j
public class FaqController extends BaseService {



    /**
     * Release q&a
     *
     * @param faqEntity
     * @param bindingResult
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @PostMapping("/insert")
    public Result insFaq(@RequestBody @Valid FaqEntity faqEntity, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldError().getDefaultMessage();
            return new Result().setMsg(errorMsg).setCode(SystemEnum.FAIL);
        }
        return sf.getFaqService().insFaq(faqEntity);
    }

    /**
     * q&a list
     *
     * @return Result
     */
    @GetMapping("/find/list")
    public Result findFaqList(FaqEntity faqEntity) {
        return sf.getFaqService().findFaqList(faqEntity);
    }

    /**
     * q&a column list
     *
     * @return Result
     */
    @GetMapping("/find/column")
    public Result findColumnList(SpecialEntity specialEntity) {
        return sf.getFaqService().findSpecialList(specialEntity);
    }

    /**
     * q&a column details list
     *
     * @return Result
     */
    @GetMapping("/find/column/details")
    public Result findSpecialDetailsList(FaqEntity specialEntity) {
        return sf.getFaqService().findSpecialDetailsList(specialEntity);
    }

    /**
     * q&a Search for fuzzy matching list
     *
     * @return Result
     */
    @GetMapping("/find/search/list")
    public Result findFaqSearchList(@RequestParam Map<String, Object> map) {
        return sf.getFaqService().findFaqSearchList(map);
    }

    /**
     * my q&a list
     *
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @GetMapping("/find/my/faq")
    public Result findMyFaqList(FaqEntity faqEntity) {
        return sf.getFaqService().findMyFaqList(faqEntity);
    }

    /**
     * my q&a answer list
     *
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @GetMapping("/find/my/faq/answer")
    public Result findMyFaqAnswerList(FaqEntity faqEntity) {
        return sf.getFaqService().findMyFaqAnswerList(faqEntity);
    }

    /**
     * q&a type list
     *
     * @return Result
     */
    @GetMapping("/type/list")
    public Result findFaqType() {
        return sf.getFaqService().findFaqTypeAll();
    }

    /**
     * q&a details
     *
     * @return Result
     */
    @GetMapping("/details")
    public Result findFaqDetails(FaqEntity faqEntity) {
        return sf.getFaqService().findDetails(faqEntity);
    }

    /**
     * q&a Comment list
     *
     * @return Result
     */
    @Authority(Resource.enter.web_user_dispensable)
    @GetMapping("/comment/list")
    public Result findFaqCommentList(FaqCommentEntity faqEntity) {
        return sf.getFaqService().findFaqCommentList(faqEntity);
    }

    /**
     * q&a View record statistics
     *
     * @return Result
     */
    @PostMapping("/view/count")
    public Result updateViewCount(@RequestBody FaqEntity faqEntity) {
        return sf.getFaqService().updateViewCount(faqEntity);
    }

    /**
     * q&a Comment
     *
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @PostMapping("/ins/comment")
    public Result insFaqComment(@RequestBody FaqCommentEntity faqCommentEntity) throws Exception {
        return sf.getFaqService().insFaqComment(faqCommentEntity);
    }

    /**
     * q&a Comment del
     *
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @PostMapping("/del/comment")
    public Result delFaqComment(@RequestBody FaqCommentEntity faqCommentEntity) throws Exception {
        return sf.getFaqService().delFaqComment(faqCommentEntity);
    }

    /**
     * q&a Comment Like
     *
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @PostMapping("/comment/like")
    public Result addFaqCommentLike(@RequestBody CommentGiveEntity commentGiveEntity) {
        return sf.getFaqService().addFaqCommentLike(commentGiveEntity);
    }

    /**
     * q&a Comment Like del
     *
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @PostMapping("/del/like")
    public Result delFaqCommentLike(@RequestBody CommentGiveEntity commentGiveEntity) {
        return sf.getFaqService().delFaqCommentLike(commentGiveEntity);
    }

    /**
     * q&a  del
     *
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @PostMapping("/del/aq")
    public Result delFaq(@RequestBody FaqEntity faqEntity) throws Exception {
        return sf.getFaqService().delFaq(faqEntity);
    }

    /**
     * q&a Comment details
     *
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @GetMapping("/find/comment/details")
    public Result findFaqCommentDetails(FaqCommentEntity faqCommentEntity) {
        return sf.getFaqService().findFaqCommentDetails(faqCommentEntity);
    }

    /**
     * q&a  collection
     *
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @PostMapping("/ins/collection")
    public Result insFaqCollection(@RequestBody FaqCollectEntity faqEntity) throws Exception {
        return sf.getFaqService().insFaqCollection(faqEntity);
    }

    /**
     * q&a  collection del
     *
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @PostMapping("/del/collection")
    public Result delFaqCollection(@RequestBody FaqCollectEntity faqEntity) throws Exception {
        return sf.getFaqService().delFaqCollection(faqEntity);
    }

    /**
     * my q&a  collection list
     *
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @GetMapping("/find/my/collection/list")
    public Result findMyFaqCollectionList(FaqCollectEntity faqEntity) throws Exception {
        return sf.getFaqService().findMyFaqCollectionList(faqEntity);
    }

    /**
     * my q&a  collection del
     *
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @PostMapping("/delete/collection")
    public Result delCollectionList(@RequestBody FaqCollectEntity faqEntity) throws Exception {
        return sf.getFaqService().delCollectionList(faqEntity);
    }

    /**
     * q&a  share record add
     *
     * @return Result
     */
    @Authority(Resource.enter.web_user)
    @PostMapping("/ins/share/record")
    public Result insShareRecord(@RequestBody FaqShareRecordEntity faqShareRecordEntity) throws Exception {
        return sf.getFaqService().insShareRecord(faqShareRecordEntity);
    }

}
