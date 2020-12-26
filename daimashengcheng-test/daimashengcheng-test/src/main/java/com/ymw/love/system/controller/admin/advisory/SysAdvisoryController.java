package com.ymw.love.system.controller.admin.advisory;

import com.ymw.love.system.common.Resource;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.common.SystemEnum;
import com.ymw.love.system.config.intercept.Authority;
import com.ymw.love.system.entity.advisory.AdvisoryCommentEntity;
import com.ymw.love.system.entity.advisory.AdvisoryDraftsEntity;
import com.ymw.love.system.entity.advisory.AdvisoryTypeEntity;
import com.ymw.love.system.entity.advisory.NewsAdvisoryEntity;
import com.ymw.love.system.service.BaseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author zjc
 * @version 1.1
 * advisory controller
 * @date 2019-08-17
 */
@SuppressWarnings("ALL")
@RestController
@RequestMapping("/admin/advisory")
public class SysAdvisoryController extends BaseService {

 
    @Authority(value = Resource.enter.admin_user)
    @ApiOperation("Consulting the list")
    @GetMapping("/list")
    public Result findAdvisoryList(NewsAdvisoryEntity newsAdvisoryEntity) throws Exception {

        return sf.getAdvisoryService().findSysAdvisoryList(newsAdvisoryEntity);
    }

    @Authority(value = Resource.enter.admin_user)
    @ApiOperation("Refused list")
    @GetMapping("/refused/list")
    public Result findAdvisoryRefusedList(NewsAdvisoryEntity newsAdvisoryEntity) throws Exception {
        return sf.getAdvisoryService().findAdvisoryRefusedList(newsAdvisoryEntity);
    }


    @Authority(value = Resource.enter.admin_user)
    @ApiOperation("pass Consulting the list")
    @GetMapping("/pass/list")
    public Result findAdvisoryPassList(NewsAdvisoryEntity newsAdvisoryEntity) throws Exception {
        return sf.getAdvisoryService().findAdvisoryPassList(newsAdvisoryEntity);
    }

    @Authority(value = Resource.enter.admin_user, content = "咨询新增")
    @ApiOperation("sys Published articles")
    @PostMapping("/ins")
    public Result insSysAdvisory(@RequestBody @Valid NewsAdvisoryEntity newsAdvisoryEntity, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldError().getDefaultMessage();
            return new Result().setMsg(errorMsg).setCode(SystemEnum.FAIL);
        }
        return sf.getAdvisoryService().insSysAdvisory(newsAdvisoryEntity);
    }
    @Authority(value = Resource.enter.admin_user, content = "咨询修改")
    @ApiOperation("update Advisory")
    @PostMapping("/upd")
    public Result updSysAdvisory(@RequestBody NewsAdvisoryEntity newsAdvisoryEntity) throws Exception {
        return sf.getAdvisoryService().updSysAdvisory(newsAdvisoryEntity);
    }

    @Authority(value = Resource.enter.admin_user, content = "咨询删除")
    @ApiOperation("del Advisory")
    @PostMapping("/del")
    public Result delSysAdvisory(@RequestBody NewsAdvisoryEntity newsAdvisoryEntity) throws Exception {
        return sf.getAdvisoryService().delSysAdvisory(newsAdvisoryEntity);
    }

    @Authority(value = Resource.enter.admin_user, content = "咨询审核")
    @ApiOperation("sys advisory audit")
    @PostMapping("/update/audit")
    public Result updateSysAdvisoryAudit(@RequestBody NewsAdvisoryEntity newsAdvisoryEntity) throws Exception {

        return sf.getAdvisoryService().updateSysAdvisoryAudit(newsAdvisoryEntity);
    }

    @Authority(value = Resource.enter.admin_user)
    @ApiOperation("comments the list")
    @GetMapping("/comments/list")
    public Result findAdvisoryCommentsList(AdvisoryCommentEntity advisoryCommentEntity) throws Exception {
        return sf.getAdvisoryService().findAdvisoryCommentsList(advisoryCommentEntity);
    }

    @Authority(value = Resource.enter.admin_user, content = "评论删除")
    @ApiOperation("sys Comments to delete")
    @PostMapping("/del/Comments")
    public Result delSysComments(@RequestBody AdvisoryCommentEntity advisoryCommentEntity) throws Exception {
        return sf.getAdvisoryService().delSysComments(advisoryCommentEntity);
    }

    @Authority(value = Resource.enter.admin_user)
    @ApiOperation("advisory category management list")
    @GetMapping("/category/list")
    public Result findAdvisoryCategoryList(AdvisoryTypeEntity advisoryTypeEntity) throws Exception {
        return sf.getAdvisoryService().findAdvisoryCategoryList(advisoryTypeEntity);
    }

    @Authority(value = Resource.enter.admin_user, content = "咨询类别添加")
    @ApiOperation("Add  category")
    @PostMapping("/ins/category")
    public Result insSysCategory(@RequestBody AdvisoryTypeEntity advisoryTypeEntity) throws Exception {
        return sf.getAdvisoryService().insSysCategory(advisoryTypeEntity);
    }

    @Authority(value = Resource.enter.admin_user, content = "咨询类别删除")
    @ApiOperation("delete  category")
    @PostMapping("/del/category")
    public Result delSysCategory(@RequestBody AdvisoryTypeEntity advisoryTypeEntity) throws Exception {
        return sf.getAdvisoryService().delSysCategory(advisoryTypeEntity);
    }

    @Authority(value = Resource.enter.admin_user, content = "咨询类别编辑")
    @ApiOperation("update  category")
    @PostMapping("/upd/category")
    public Result updSysCategory(@RequestBody AdvisoryTypeEntity advisoryTypeEntity) throws Exception {
        return sf.getAdvisoryService().updSysCategory(advisoryTypeEntity);
    }

    @Authority(value = Resource.enter.admin_user, content = "咨询保存草稿")
    @ApiOperation("sys Save drafts")
    @PostMapping("/ins/drafts")
    public Result insSysAdvisoryDrafts(@RequestBody @Valid AdvisoryDraftsEntity draftsEntity, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldError().getDefaultMessage();
            return new Result().setMsg(errorMsg).setCode(SystemEnum.FAIL);
        }
        return sf.getAdvisoryService().insSysAdvisoryDrafts(draftsEntity);
    }

    @Authority(value = Resource.enter.admin_user, content = "咨询修改草稿")
    @ApiOperation("sys upd drafts")
    @PostMapping("/upd/drafts")
    public Result updSysAdvisoryDrafts(@RequestBody AdvisoryDraftsEntity draftsEntity) throws Exception {
        return sf.getAdvisoryService().updSysAdvisoryDrafts(draftsEntity);
    }

    @Authority(value = Resource.enter.admin_user)
    @ApiOperation("drafts list")
    @GetMapping("/drafts/list")
    public Result findAdvisoryDraftsList(AdvisoryDraftsEntity draftsEntity) throws Exception {
        return sf.getAdvisoryService().findAdvisoryDraftsList(draftsEntity);
    }
}
