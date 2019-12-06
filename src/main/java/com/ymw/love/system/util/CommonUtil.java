package com.ymw.love.system.util;

import com.ymw.love.system.config.HintTitle;
import com.ymw.love.system.config.excep.MissRequiredParamException;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
                throw new MissRequiredParamException(HintTitle.Constant.QUERY_PARAM_INVALID);
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
                throw new MissRequiredParamException(HintTitle.Constant.ENDDATE_INVALID);
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
     * 判断字符串中是否含有表情
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        boolean isEmoji = false;
        for (int i = 0; i < len; i++) {
            char hs = source.charAt(i);
            if (0xd800 <= hs && hs <= 0xdbff) {
                if (source.length() > 1) {
                    char ls = source.charAt(i + 1);
                    int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
                    if (0x1d000 <= uc && uc <= 0x1f77f) {
                        return true;
                    }
                }
            } else {
                // non surrogate
                if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
                    return true;
                } else if (0x2B05 <= hs && hs <= 0x2b07) {
                    return true;
                } else if (0x2934 <= hs && hs <= 0x2935) {
                    return true;
                } else if (0x3297 <= hs && hs <= 0x3299) {
                    return true;
                } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d
                        || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c
                        || hs == 0x2b1b || hs == 0x2b50 || hs == 0x231a) {
                    return true;
                }
                if (!isEmoji && source.length() > 1 && i < source.length() - 1) {
                    char ls = source.charAt(i + 1);
                    if (ls == 0x20e3) {
                        return true;
                    }
                }
            }
        }
        return isEmoji;
    }

    /**
     * 判断当前时间是否在指定时间内
     * @param startDate 开始时间  HH:mm
     * @param endDate 结束时间 HH:mm
     * @return
     */
    public static boolean isOut(Date startDate, Date endDate){
        //HH:mm 每日开始时间
        String startTimeStr = DateUtil.parseDateToStr(startDate, DateUtil.HH_MI);
        //HH:mm  每日结束时间
        String endTimeStr = DateUtil.parseDateToStr(endDate, DateUtil.HH_MI);

        String currentDay = DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYY_MM_DD);
        String start = currentDay + " "+startTimeStr;
        String end = currentDay + " "+endTimeStr;
        //yyyy-MM-dd
        Date startTime = DateUtil.parseStrToDate(start, DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI);
        Date endTime = DateUtil.parseStrToDate(end, DateUtil.DATE_TIME_FORMAT_YYYY_MM_DD_HH_MI);
        return DateUtil.belongCalendar(new Date(), new Date(startTime.getTime()), new Date(endTime.getTime()));
    }

    /**
     * 去除括号中的特殊字符
     * @param s
     * @return
     */
    public static String assistanceDescDeal(String s, String regx){
        Pattern p = Pattern.compile(regx);
        Matcher m = p.matcher(s);
        StringBuffer sb = new StringBuffer();
        String temp = null;
        while(m.find()){
            temp = m.group().replaceAll(" ", "");
            m.appendReplacement(sb, temp);
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 获取取值范围的随机数，保留scale位数
     * @param min  区间最小值
     * @param max  区间最大值
     * @param scale 保留的小数位
     * @return
     */
    public static String genRandoFloatFromRange(Float min, Float max, int scale){
        /**
         * setScale(1,BigDecimal.ROUND_DOWN)直接删除多余的小数位，如2.35会变成2.3
         * setScale(1,BigDecimal.ROUND_UP)进位处理，2.35变成2.4
         * setScale(1,BigDecimal.ROUND_HALF_UP)四舍五入，2.35变成2.4
         * setScaler(1,BigDecimal.ROUND_HALF_DOWN)四舍五入，2.35变成2.3，如果是5则向下舍
         */
        if(min == max) return min+"";
        BigDecimal num = new BigDecimal(Math.random() * (max - min) + min)
                .setScale(scale,BigDecimal.ROUND_DOWN);
        return num.toString();
    }

    public static void main(String[]args){
        System.out.println(genRandoFloatFromRange(1000.00f, 1000.00f, 2));

//        //取值范围为[0.7,1)
////区间最大值
//        float max = 0.70f;
////区间最小值
//        float min = 0.70f;
////保留的小数位
//        int scale = 2;
//        BigDecimal num = new BigDecimal(Math.random() * (max - min) + min)
//                .setScale(scale,BigDecimal.ROUND_DOWN);
//        System.out.println(Float.parseFloat(num.toString()));
//        String res = "{\"country\":\"中国\",\"province\":\"浙江\",\"city\":\"杭州\",\"openid\":\"oDnHxt9hy5GG2QBZy8qrBIjNzgWc\",\"sex\":2,\"nickname\":\"社会主义好青年\",\"headimgurl\":\"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eokGiaoo4A70wlpUho8HTqf24SKRMU6H1eq4QiaXia3aAs2ZNicLTfL7icibhmdMVxebpohCbricQbHxw4sg/132\",\"language\":\"zh_CN\",\"privilege\":[]}";
//        JSONObject jsonObject = JSONObject.parseObject(res);
        

//        String s = "我是(　西门庆　)来自（ 流浪  ）村（ 8  ）组（街道/社区），（   1）年确诊为类风湿关节炎，到（  2 ）年彻底恶化，全身关节剧烈疼痛，不能走路，（提示：家里情况）睡眠缺失精神萎靡，基本的工作能力也丧失了。\n" ;
//                "（提示：特别是在某地受骗经历或者吃错药详细经过）\n" +
//                "　　为了治病，跑遍全国各大医院风湿免疫科，中西医各种药物都试过，钱全部消耗在疾病的治疗上面，但是病情却迟迟没能好转。\n" +
//                "　　（   3）月份通过病友（易峰   ）介绍了解到胡泽民类风湿病医院，胡泽民医院的医生根据我的实际情况制定了一套中医营养免疫疗法。\n" +
//                "　　到（   8）月份，（   8）多月的胡氏营养治疗，（提示：现在情况）西药全部停止使用，全身关节也不痛了，整个人的状态也逐步回升，饭也能吃多了，好疗效让我信心百倍，现在我希望在胡泽民医院达到彻底临床治愈因为家里贫困希望得到家人朋友们的爱心帮助，只要您轻轻一点，把我的经历，分享到自己的朋友圈，就可以为我筹集十元的治疗费用，上不封顶，这次活动时间只有七天，请朋友们尽快帮我助力。\n" +
//                "　　忠诚感谢您的爱心善举，帮助我见证我战胜疾病，树立治好病的信心。\n" +
//                "　　其他有需要帮助的类风湿、强直性脊柱炎、银屑病等免疫性疾病的病友也可以联系我，我们一起共抗病魔，尽快走入健康生活行列。\n" +
//                "　　再次在这里感谢亲朋好友的善举，并见证我战胜这个不死的癌症。";
//        Pattern p = Pattern.compile("（.*?）");
//        Matcher m = p.matcher(s);
//        StringBuffer sb = new StringBuffer();
//        String temp = "";
//        while(m.find()){
//            temp = m.group().replaceAll(" ", "");
//            m.appendReplacement(sb, temp);
//        }
//        m.appendTail(sb);
//        System.out.println(sb.toString());
//        String s = "\xF0\x9F\x98\x97\xF0\x9F";
//        System.out.println(containsEmoji(s));
//        String name = "乌兰巴托";
//        System.out.println(nameEncryption(name, 1) );
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
