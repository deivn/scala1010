package com.ymw.love.system.service.banner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ymw.love.system.common.Result;
import com.ymw.love.system.common.SystemEnum;
import com.ymw.love.system.entity.banner.BannerEntity;
import com.ymw.love.system.service.BaseService;
import com.ymw.love.system.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * banner service impl
 *
 * @author zjc
 * @version 1.1
 * @date 2019-08-16
 */
@Service
@Slf4j
public class BannerServiceImpl extends BaseService {


    @Transactional(rollbackFor = Exception.class)
    public Result insBanner(BannerEntity bannerEntity) throws Exception {
        try {
            bannerEntity.setAddTime(LocalDateTime.now());
            boolean insert = bannerEntity.insert();
            return new Result().setCode(insert == false ? SystemEnum.FAIL : SystemEnum.SUCCESS).setMsg(insert == false ? "发布失败" : "发布成功");
        } catch (Exception x) {
            log.info("add banner error");
            throw new Exception(x.getMessage());
        }
    }

    public Result findBannerList(BannerEntity bannerEntity) throws Exception {
        try {
            IPage<BannerEntity> page = new Page<>(bannerEntity.getPageSum(), bannerEntity.getPageSize());
            //new QueryWrapper<BannerEntity>().eq("status",1)
            return new Result(mf.getBannerMapper().selectPage(page, null));
        } catch (Exception x) {
            log.info("findBannerList error");
            throw new Exception(x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result updateBanner(BannerEntity bannerEntity) throws Exception {
        try {
            if (StringUtils.isEmpty(bannerEntity.getId())) {
                throw new Exception("banner id不能为空!");
            }
            boolean insert = bannerEntity.updateById();
            return new Result().setCode(insert == false ? SystemEnum.FAIL : SystemEnum.SUCCESS).setMsg(insert == false ? "修改失败" : "修改成功");
        } catch (Exception x) {
            log.info("updateBanner error");
            throw new Exception(x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result delBanner(BannerEntity bannerEntity) throws Exception {
        try {
            if (StringUtils.isEmpty(bannerEntity.getId())) {
                throw new Exception("banner id不能为空!");
            }
            String[] list = bannerEntity.getId().split(",");
            int insert = mf.getBannerMapper().deleteBatchIds(Arrays.asList(list));
            return new Result().setCode(insert <= 0 ? SystemEnum.FAIL : SystemEnum.SUCCESS).setMsg(insert <= 0 ? "删除失败" : "删除成功");
        } catch (Exception x) {
            log.info("delBanner error");
            throw new Exception(x.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Result clickRecordBanner(BannerEntity bannerEntity) throws Exception {
        try {
            if (StringUtils.isEmpty(bannerEntity.getId())) {
                log.info("banner id  is null");
                return new Result();
            }
           mf.getPostersCaculateSender().bannerClickCount(bannerEntity);
        } catch (Exception x) {
            log.info("delBanner error");
            throw new Exception(x.getMessage());
        }
        return new Result();
    }

    @Transactional(rollbackFor = Exception.class)
    public Result updBannerSendAndEnd(BannerEntity bannerEntity) throws Exception {
        try {
            if (StringUtils.isEmpty(bannerEntity.getStatus()) || StringUtils.isEmpty(bannerEntity.getId())){
                throw new Exception("参数异常....");
            }
            if (bannerEntity.getStatus()== 1){
                bannerEntity.setSendTime(LocalDateTime.now());
            }else{
                bannerEntity.setEndTime(LocalDateTime.now());
            }
            boolean b = bannerEntity.updateById();
            return new Result().setCode(b  ? SystemEnum.SUCCESS : SystemEnum.FAIL).setMsg(b  ? "SUCCESS" : "FAIL");
        } catch (Exception x) {
            log.info("updBannerSendAndEnd error");
            throw new Exception(x.getMessage());
        }
    }
}
