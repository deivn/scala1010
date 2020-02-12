package com.ymw.love.system.util;

/**
 * 腾讯直播推流、拉流工具
 * @author 作者 ：zhoushengyuan
 * @date 创建时间：2020年2月12日
 *
 */
//TODO 待完善测试
public class TecentCloudUtils {

    // 用于 生成推流防盗链的key
    public static final String key = "";

    public static final String bizid = "";

    public static final String APPID = "";

    // 用于主动查询和被动通知的key:API鉴定key
    public static final String API_KEY = "";

    // API回调地址
    public static final String API_ADDRESS = "http://fcgi.video.qcloud.com/common_access";

    /**
     * 推流地址
     */
    public static final String PUSH_URL = "rtmp://" + bizid + ".livepush.myqcloud.com/live/" + bizid + "_";

    /**
     * PC拉流地址
     */
    public static final String PULL_RTMP_URL = "rtmp://" + bizid + ".liveplay.myqcloud.com/live/"+ bizid + "_";
    /**
     * app拉流地址
     */
    public static final String PULL_URL = "http://" + bizid + ".liveplay.myqcloud.com/live/"+ bizid + "_";


    /**
     * 这是推流防盗链的生成 KEY+ streamId + txTime
     * @param key
     *            防盗链使用的key
     * @param streamId
     *            通常为直播码.示例:bizid+房间id
     * @param txTime
     *            到期时间
     */
    public static String getSafeUrl(String key, String streamId, long txTime) {
        String input = new StringBuilder().append(key).append(streamId).append(Long.toHexString(txTime).toUpperCase()).toString();

        String txSecret = null;

        txSecret = MD5.string2MD5(input);

        return txSecret == null ? "" : new StringBuilder().append("txSecret=").append(txSecret).append("&").append("txTime=").append(Long.toHexString(txTime).toUpperCase()).toString();
    }

    /**
     * 推流地址生成
     */
    public static String getPushUrl(String roomId) {
        Long now = System.currentTimeMillis() + 60L * 60L * 24L * 30L * 1000L;// 要转成long类型，不然为负数
        // 当前毫秒数+需要加上的时间毫秒数 = 过期时间毫秒数
        Long txTime = now / 1000;// 推流码过期时间秒数
        String safeUrl = getSafeUrl(key, bizid + "_" + roomId, txTime);
        String realPushUrl = PUSH_URL + roomId + "?bizid=" + bizid + "&" + safeUrl;
        return realPushUrl;
    }

    /**
     * APP拉流地址获得
     */
    public static String getPullUrl(String owenrId) {
        String appPullUrl = PULL_URL + owenrId + ".flv";
        return appPullUrl;
    }

    /**
     * PC拉流地址获得
     */
    public static String getPullRtmpUrl(String owenrId) {
        String pullRtmpUrl = PULL_RTMP_URL + owenrId;
        return pullRtmpUrl;
    }

    /**
     * 获取关闭直播的url关闭直播 需要发送请求给腾讯服务器,然后返回结果
     */
    public static String getCloseLiveUrl(String id) {
        // 此请求的有效时间
        Long current = System.currentTimeMillis() / 1000 + 10;
        // 生成sign签名
        String sign = MD5.string2MD5(new StringBuffer().append(API_KEY).append(current).toString());
        // 生成需要关闭的直播码
        String code = bizid + "_" + id;
        // 生成关闭的参数列表
        String params = new StringBuffer().append("&interface=Live_Channel_SetStatus").append("&Param.s.channel_id=").append(code).append("&Param.n.status=0").append("&t=").append(current).append("&sign=").append(sign).toString();
        // 拼接关闭URL
        String url = API_ADDRESS + "?appid=" + APPID + params;
        return url;
    }

    /**
     * 获取录播查询请求地址
     */
    public static String getRecordUrl(String id) {
        Long current = (System.currentTimeMillis() + 60 * 60 * 24 * 1000) / 1000;
        String sign = MD5.string2MD5(new StringBuffer().append(API_KEY).append(current).toString());
        String code = bizid + "_" + id;
        String params = new StringBuffer().append("&interface=Live_Tape_GetFilelist").append("&Param.s.channel_id=").append(code).append("&t=").append(current).append("&sign=").append(sign).toString();
        // 拼接URL
        String url = API_ADDRESS + "?appid=" + APPID + params;
        return url;
    }
}
