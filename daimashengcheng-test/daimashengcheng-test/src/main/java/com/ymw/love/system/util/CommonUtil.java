package com.ymw.love.system.util;

import com.ymw.love.system.config.excep.MissRequiredParamException;
import com.ymw.love.system.immobile.Constant;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用工具类
 */
public class CommonUtil {

    /**
     * 1 今天  2 昨天 3 最近3天 4 最近7天 5 最近1个月
     * @param dateValue
     * @return
     */
    public static Map<String, String> getFinishEndDateByDateValue(Integer dateValue){
        Map<String, String> dateMap = new HashMap<String, String>();
        String startDate = "";
        String endDate = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD);
        switch(dateValue){
            case 1:
                //今天
                startDate = endDate;
                break;
            case 2:
                //昨天
                startDate = DateUtil.currentDateBefore(1);
                endDate = startDate;
                break;
            case 3:
                //最近3天
                startDate = DateUtil.currentDateBefore(3);
                break;
            case 4:
                //最近7天
                startDate = DateUtil.currentDateBefore(7);
                break;
            case 5:
                //最近1个月
                startDate = DateUtil.currentDateBeforeMonths(1);
                break;
        }
        dateMap.put("startDate", startDate);
        dateMap.put("endDate", endDate);
        return dateMap;
    }

    /**
     * 1 今天  2 明天 3 未来3天 4 未来7天 5 未来1个月
     * @param dateValue
     * @return
     */
    public static Map<String, String> getPreEndDateByDateValue(Integer dateValue){
        Map<String, String> dateMap = new HashMap<String, String>();
        String endDate = "";
        String startDate = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD);
        switch(dateValue){
            case 1:
                //今天
                endDate = startDate;
                break;
            case 2:
                //明天
                endDate = DateUtil.currentDateAfter(1);
                startDate = endDate;
                break;
            case 3:
                //未来三天
                endDate = DateUtil.currentDateAfter(3);
                break;
            case 4:
                //未来7天
                endDate = DateUtil.currentDateAfter(7);
                break;
            case 5:
                //未来一个月
                endDate = DateUtil.currentDateAfterMonths(1);
                break;
        }
        dateMap.put("startDate", startDate);
        dateMap.put("endDate", endDate);
        return dateMap;
    }

    public static Map<String, String> getQueryDate(Integer dateValue, String startDate, String endDate){
        Map<String, String> ruleMap = null;
        //优先级，先按固定值查询
        if(dateValue != null){
            if(dateValue<1 || dateValue > 5){
                throw new MissRequiredParamException(Constant.QUERY_PARAM_INVALID);
            }
            ruleMap = CommonUtil.getFinishEndDateByDateValue(dateValue);
        }else{
            ruleMap = getDateQueryMethod(startDate, endDate);
        }
        return ruleMap;
    }

    public static Map<String, String> getDateQueryMethod(String startDate, String endDate){
        Map<String, String> ruleMap = new HashMap<String, String>();
        if(StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)){
            startDate = null;
            endDate = null;
        }
        if(StringUtils.isEmpty(startDate) && StringUtils.isNotEmpty(endDate)){
            startDate = null;
        }
        if(StringUtils.isNotEmpty(startDate) && StringUtils.isEmpty(endDate)){
            endDate = null;
        }
        if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)){
            Long dateDistance = DateUtil.getDistance(endDate, startDate);
            if(dateDistance < 0){
                throw new MissRequiredParamException(Constant.ENDDATE_INVALID);
            }
        }
        ruleMap.put("startDate", startDate);
        ruleMap.put("endDate", endDate);
        return ruleMap;
    }


    /**
     * 对字符串处理:将指定位置到指定位置的字符以星号代替
     *
     * @param content
     *            传入的字符串
     * @param begin
     *            开始位置
     * @param end
     *            结束位置
     * @return
     */
    public static String getStarString(String content, int begin, int end) {

        if (begin >= content.length() || begin < 0) {
            return content;
        }
        if (end >= content.length() || end < 0) {
            return content;
        }
        if (begin >= end) {
            return content;
        }
        String starStr = "";
        for (int i = begin; i < end; i++) {
            starStr = starStr + "*";
        }
        return content.substring(0, begin) + starStr + content.substring(end, content.length());
    }

    /**
     * 名字加密
     * @param content
     * @param begin
     * @return
     */
    public static String nameEncryption(String content, int begin) {

        if (begin >= content.length() || begin < 0) {
            return content;
        }
        String starStr = "";
        for (int i = 0; i < content.length()-1; i++) {
            starStr = starStr + "*";
        }
        return content.substring(0, begin) + starStr;
    }

    /**
     * 去除P标签，取标签内的文本内容
     * @param content 带标签的文本内容
     * @param regex 正则匹配规则
     * @return
     */
    public static String tag2content(String content, String regex){
        final List<String> list = new ArrayList<String>();
        StringBuffer sb = new StringBuffer();
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(content);
        while(matcher.find()){
            list.add(matcher.group());
        }
        if(list != null && list.size() > 0){
            for(int i =0; i< list.size(); i++){
                sb.append(outTag(list.get(i))).append(" ");
            }
        }else{
            sb.append(content);
        }
        return sb.toString().trim();
    }

    public static String outTag(final String s) {
        return s.replaceAll("<.*?>", "");
    }

    public static void main(String[]args){
        String name = "乌兰巴托";
        System.out.println(nameEncryption(name, 1) );
//        Integer type = 6;
//        全平台消息推送
//        if(type == Constant.OPTION_MESSAGE_ACTIVITY_PUSH){
//            System.out.println("xxxx");
//        }
//        final List<String> list = new ArrayList<String>();
//        StringBuffer sb = new StringBuffer();
//        String s = "<p></p><div class=\"media-wrap image-wrap\"><img src=\"https://lfshz.oss-cn-shenzhen.aliyuncs.com/lfshz_images/2019083022019e9603.jpeg\"/></div><p></p>";
//        String s1 = "<p>1231231</p><div class=\"media-wrap image-wrap\"><img class=\"media-wrap image-wrap\" src=\"https://lfshz.oss-cn-shenzhen.aliyuncs.com/lfshz_images/2019083023342b3f74.jpeg\"/></div><p></p><p>25789www</p>";
//        String regex="<p>(.*?)</p>";
//        Pattern compile = Pattern.compile(regex);
//        Matcher matcher = compile.matcher(s1);
//        while(matcher.find()){
//            list.add(matcher.group());
//        }
//        for(int i =0; i< list.size(); i++){
//             sb.append(outTag(list.get(i))).append(" ");
//        }
//        System.out.println(sb.toString().trim());

//        String num = "18219148102";
//        System.out.println(getStarString(num, num.length()-8, num.length()-4));
//        List<Object> list = new ArrayList<>();
//        String str = "欢迎加入类风湿互助社区，你的元救助券已发送。   查看详情";
////        String[]values = {"100", "link"};
//        list.add("100");
//        list.add("linked");
////        String[] array=list.toArray(new String[list.size()]);
//        String newStr = MessageFormat.format(str, list);
//        System.out.println(newStr);
//        System.out.println(-1 << Integer.SIZE - 3);
//        AtomicInteger ctl = new AtomicInteger(-536870912);
//        System.out.println(ctl.get() & (1 << Integer.SIZE)-1);
//
//        System.out.println(ctl.get() & ~0);
//
//        System.out.println(0 << 29);
//        System.out.println(ctl.get());
//        System.out.println(ctlOf(-1 << Integer.SIZE - 3, 0));
    }

}
