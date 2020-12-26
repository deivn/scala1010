package com.ymw.love.system.mapper.coupon;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ymw.love.system.entity.coupon.CouponsEntity;
import com.ymw.love.system.entity.coupon.WithdrawMoneyEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author zjc
 * @date 2019年8月26日14:05:26
 */
public interface InvitationCodeMapper extends BaseMapper<CouponsEntity> {

    IPage<CouponsEntity> findCouponsList(@Param("page") IPage<CouponsEntity> page, @Param("couponsEntity") CouponsEntity couponsEntity);

    IPage<CouponsEntity> findLoveCouponsList(@Param("page") IPage<CouponsEntity> page, @Param("couponsEntity") CouponsEntity couponsEntity);

    Integer findLoveTotalSum(@Param("couponsEntity") CouponsEntity couponsEntity);

    IPage<Map<String, Object>> findHxList(@Param("page") IPage<Map<String, Object>> page, @Param("couponsEntity") CouponsEntity couponsEntity);

    IPage<Map<String, Object>> findYHxList(@Param("page") IPage<Map<String, Object>> page, @Param("couponsEntity") CouponsEntity couponsEntity);

    CouponsEntity findloveList(@Param("id") String id);

    IPage<WithdrawMoneyEntity> findWithdrawList(@Param("page") IPage<WithdrawMoneyEntity> page, @Param("withdrawMoneyEntity") WithdrawMoneyEntity withdrawMoneyEntity);

    IPage<Map<String,Object>> findCount(IPage<Map<String,Object>> page,@Param("dates") String[] dates);

    Map<String,Object> findBottomCount(@Param("withdrawMoneyEntity") WithdrawMoneyEntity withdrawMoneyEntity);
}
