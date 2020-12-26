package com.ymw.love.system.controller.web.banner;

import com.ymw.love.system.common.Result;
import com.ymw.love.system.entity.banner.BannerEntity;
import com.ymw.love.system.service.BaseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author zjc
 * @version 1.1
 * banner controller
 * @date 2019-08-16
 */
@SuppressWarnings("ALL")
@RestController
@RequestMapping("/web/banner")
public class BannerController extends BaseService {





    @ApiOperation(value = "banner list")
    @GetMapping("/find")
    public Result findBannerList(BannerEntity bannerEntity) throws Exception {
        return sf.getBannerService().findBannerList(bannerEntity);
    }

    @ApiOperation(value = "banner click record")
    @PostMapping("/click/record")
    public Result clickRecordBanner(@RequestBody BannerEntity bannerEntity) throws Exception {
        return sf.getBannerService().clickRecordBanner(bannerEntity);
    }

}
