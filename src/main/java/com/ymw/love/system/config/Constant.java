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

    //用户确认收货
    public static final Integer ORDER_CONFIRM_WAY_USER = 1;

    //系统确认收货
    public static final Integer ORDER_CONFIRM_WAY_SYSTEM = 2;

    //订单全部状态
    public static final Integer ORDER_STATE_FIND_ALL = 0;

    //待发货
    public static final Integer ORDER_STATE_NOT_SEND = 1;

    //已发货
    public static final Integer ORDER_STATE_YES_SEND = 2;

    //已完成
    public static final Integer ORDER_STATE_YES_CONFIRM = 3;

    public static final String QUERY_PARAM_INVALID = "日期选择参数不合法!";

    public static final String ENDDATE_INVALID = "结束时间不能小于起始时间!";

    public static final String GOODS_INFO_EMPTY = "该商品已下架";

    public static final String BANNER_INFO_EMPTY = "Banner不存在";

    public static final String ORDER_INFO_EMPTY = "订单不存在";

    public static final String ORDER_SUBMIT_FAIL_MESSAGE = "订单提交失败,请稍后再试";

    public static final String ADDRESS_INFO_EMPTY = "地址信息不存在";

    public static final String ADDRESS_SELECT_EMPTY = "请添加收货地址";

    public static final String GOODS_SKU_NOT_ENOUGH ="商品库存不足";

    public static final String USER_ENERGY_NOT_ENOUGH ="兑换能量不足";
}
