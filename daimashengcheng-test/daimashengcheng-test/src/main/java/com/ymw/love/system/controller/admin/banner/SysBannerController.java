package com.ymw.love.system.controller.admin.banner;

import com.ymw.love.system.common.Resource;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.common.SystemEnum;
import com.ymw.love.system.config.intercept.Authority;
import com.ymw.love.system.entity.banner.BannerEntity;
import com.ymw.love.system.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author zjc
 * @version 1.1
 * banner controller
 * @date 2019-08-16
 */
@SuppressWarnings("ALL")
@RestController
@RequestMapping("/admin")
public class SysBannerController extends BaseService {




    @Authority(value= Resource.enter.admin_user,content="新增banner")
    @PostMapping("/ins/banner")
    public Result insBanner(@RequestBody @Valid BannerEntity bannerEntity, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldError().getDefaultMessage();
            return new Result().setMsg(errorMsg).setCode(SystemEnum.FAIL);
        }
        return sf.getBannerService().insBanner(bannerEntity);
    }

    @Authority(value= Resource.enter.admin_user)
    @GetMapping("/find/banner")
    public Result findBannerList(BannerEntity bannerEntity) throws Exception {
        return sf.getBannerService().findBannerList(bannerEntity);
    }

    @Authority(value= Resource.enter.admin_user,content="banner编辑")
    @PostMapping("/update/banner")
    public Result updateBanner(@RequestBody BannerEntity bannerEntity) throws Exception {
        return sf.getBannerService().updateBanner(bannerEntity);
    }

    @Authority(value= Resource.enter.admin_user,content="banner删除")
    @PostMapping("/del/banner")
    public Result delBanner(@RequestBody BannerEntity bannerEntity) throws Exception {
        return sf.getBannerService().delBanner(bannerEntity);
    }

    @Authority(value= Resource.enter.admin_user,content="banner发布/停止")
    @PostMapping("/banner/send/end")
    public Result updBannerSendAndEnd(@RequestBody BannerEntity bannerEntity) throws Exception {
        return sf.getBannerService().updBannerSendAndEnd(bannerEntity);
    }

}
