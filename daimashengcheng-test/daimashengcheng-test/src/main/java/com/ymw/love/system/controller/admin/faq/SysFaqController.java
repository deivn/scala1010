package com.ymw.love.system.controller.admin.faq;

import com.ymw.love.system.common.Resource;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.config.intercept.Authority;
import com.ymw.love.system.entity.faq.FaqEntity;
import com.ymw.love.system.entity.faq.FaqTypeEntity;
import com.ymw.love.system.entity.faq.SpecialEntity;
import com.ymw.love.system.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zengjuncheng
 * @version 1.1
 * @describe special layer
 * @Creation time  2019/8/2
 * @Contact information:953262399
 */
@RestController
@RequestMapping("/admin/faq")
public class SysFaqController extends BaseService {




    /**
     * special add
     *
     * @return Result
     */
    @Authority(value = Resource.enter.admin_user,content = "专栏添加")
    @PostMapping("/ins/special")
    public Result insSpecial(@RequestBody SpecialEntity specialEntity) throws Exception {
        return sf.getFaqService().insSpecial(specialEntity);
    }

    /**
     * update special
     *
     * @return Result
     */
    @Authority(value = Resource.enter.admin_user,content = "专栏修改")
    @PostMapping("/upd/special")
    public Result updSpecial(@RequestBody SpecialEntity specialEntity) throws Exception {
        return sf.getFaqService().updSpecial(specialEntity);
    }

    /**
     * delete special
     *
     * @return Result
     */
    @Authority(value = Resource.enter.admin_user,content = "专栏删除")
    @PostMapping("/del/special")
    public Result delSpecial(@RequestBody SpecialEntity specialEntity) throws Exception {
        return sf.getFaqService().delSpecial(specialEntity);
    }

    /**
     * faq update special list
     *
     * @return Result
     */
    @Authority(value=Resource.enter.admin_user,holdUp=false )
    @GetMapping("/find/drop/special")
    public Result findDropSpecial(SpecialEntity specialEntity) {
        return sf.getFaqService().findDropSpecial(specialEntity);
    }


    /**
     * special list
     *
     * @return Result
     */
    @Authority(Resource.enter.admin_user)
    @GetMapping("/find/special")
    public Result findSpecial(SpecialEntity specialEntity) {
        return sf.getFaqService().findSpecial(specialEntity);
    }

    /**
     * special detail
     *
     * @return Result
     */
    @Authority(value=Resource.enter.admin_user,holdUp=false)
    @GetMapping("/find/special/detail")
    public Result findSpecialDetail(SpecialEntity specialEntity) throws Exception {
        return sf.getFaqService().findSpecialDetail(specialEntity);
    }


    /**
     * faq list
     *
     * @return Result
     */
    @Authority(Resource.enter.admin_user)
    @GetMapping("/find/list")
    public Result findSysFaqList(FaqEntity faqEntity) {
        return sf.getFaqService().findSysFaqList(faqEntity);
    }


    /**
     * sys update faq
     *
     * @return Result
     */
    @Authority(value = Resource.enter.admin_user,content = "问答编辑")
    @PostMapping("/sys/update")
    public Result updSysFaq(@RequestBody FaqEntity faqEntity) throws Exception {
        return sf.getFaqService().updSysFaq(faqEntity);
    }

    /**
     * sys delete faq
     *
     * @return Result
     */
    @Authority(value = Resource.enter.admin_user,content = "问答删除")
    @PostMapping("/sys/delete")
    public Result delSysFaq(@RequestBody FaqEntity faqEntity) throws Exception {
        return sf.getFaqService().delSysFaq(faqEntity);
    }

    /**
     * faq type list
     *
     * @return Result
     */
    @Authority(Resource.enter.admin_user)
    @GetMapping("/find/type/list")
    public Result findSysFaqTypeList(FaqTypeEntity typeEntity) {
        return sf.getFaqService().findSysFaqTypeList(typeEntity);
    }

    /**
     * faq type add
     *
     * @return Result
     */
    @Authority(value = Resource.enter.admin_user,content = "问答类别添加")
    @PostMapping("/type/add")
    public Result insType(@RequestBody FaqTypeEntity faqEntity) throws Exception {
        return sf.getFaqService().insType(faqEntity);
    }


    /**
     * faq type update
     *
     * @return Result
     */
    @Authority(value = Resource.enter.admin_user,content = "问答类别编辑")
    @PostMapping("/type/update")
    public Result updType(@RequestBody FaqTypeEntity faqEntity) throws Exception {
        return sf.getFaqService().updType(faqEntity);
    }

    /**
     * faq type update
     *
     * @return Result
     */
    @Authority(value = Resource.enter.admin_user,content = "问答类别删除")
    @PostMapping("/type/del")
    public Result delType(@RequestBody FaqTypeEntity faqEntity) throws Exception {
        return sf.getFaqService().delType(faqEntity);
    }


}
