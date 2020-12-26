package com.ymw.love.system.mq.receiver;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ymw.love.system.entity.banner.BannerEntity;
import com.ymw.love.system.entity.posters.UPostersShare;
import com.ymw.love.system.mapper.banner.BannerMapper;
import com.ymw.love.system.mapper.posters.UPostersShareMapper;
import com.ymw.love.system.mq.ConstantMqQueue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
@Component
public class PostersCaculateReciver {

    @Autowired
    private UPostersShareMapper shareMapper;
    @Autowired
    private BannerMapper bannerMapper;

    /**
     * 海报分享统计
     * @param takeNumber
     */
    @RabbitListener(queues = ConstantMqQueue.POSTERS_SHARED_CACULATE)
    public void postersCaculate(Map<String, Object> map) {
        caculateShares(map);
    }

    public void caculateShares(Map<String, Object> map){
        Integer appSign = (int)map.get("appSign");
        String postersId = (String)map.get("postersId");
        //目前只有1.微信 2.朋友圈 3.QQ
        List<UPostersShare> postersShares = shareMapper.selectList(new QueryWrapper<UPostersShare>().eq("posters_id", postersId).eq("app_sign", appSign));
        if(postersShares == null || postersShares.size() == 0){
            UPostersShare postersShare = new UPostersShare(postersId, appSign);
            shareMapper.insert(postersShare);
        }else{
            UPostersShare postersShare = postersShares.get(0);
            postersShare.setShares(postersShare.getShares()+1);
            shareMapper.update(postersShare, new QueryWrapper<UPostersShare>().eq("posters_id", postersId).eq("app_sign", appSign));
        }
    }


    /**
     * banner 点击量统计
     * @param takeNumber
     */
    @RabbitListener(queues = ConstantMqQueue.BANNER_CLICK_COUNT)
    public void postersCaculate(BannerEntity bannerEntity) {
        bannerMapper.insClickRecordBanner(bannerEntity.getId());
    }


}
