package com.ymw.love.system.config;

/**
 * @author zsy
 * @version 1.1  controller
 * @date
 */
public class Constant {

    //下架
    public static final Integer BASE_CANCEL_STATE = 1;

    //上架
    public static final Integer BASE_ACTIVE_STATE = 2;

    public static final Integer GOODS_FINDTYPE_CATEGORY = 0;

    public static final Integer GOODS_FINDTYPE_RECO = 1;

    public static final Integer GOODS_FINDTYPE_LAST = 2;

    //待发货
    public static final Integer ORDER_STATE_STAY = 1;

    //已发货
    public static final Integer ORDER_STATE_START = 2;

    //已完成
    public static final Integer ORDER_STATE_COMPLETE = 3;

    public static final String QUERY_PARAM_INVALID = "日期选择参数不合法!";

    public static final String ENDDATE_INVALID = "结束时间不能小于起始时间!";

    public static final String GOODS_INFO_EMPTY = "商品不存在";

    public static final String BANNER_INFO_EMPTY = "Banner不存在";
}
