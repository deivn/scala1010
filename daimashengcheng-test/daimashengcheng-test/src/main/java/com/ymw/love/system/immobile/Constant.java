package com.ymw.love.system.immobile;


/**
 * 常量类定义
 */
public class Constant {

    //取消预约
    public static final String CANCEL_RESEVATION = "2";

    //去评价
    public static final String TO_EVALUATE  = "4";

    //待就诊
    public static final Integer PRE_SEE_DOCTOR = 1;

    //已就诊
    public static final Integer FINISH_SEE_DOCTOR = 2;

    //已取消
    public static final Integer CANCEL_SEE_DOCTOR = 3;

    //已失效
    public static final Integer EXPIRED_SEE_DOCTOR = 4;

    //号源管理
    public static final Integer NUMBER_MANAGER = 5;

    //---------------------消息推送
    //6.全平台用户活动消息推送
    public static final Integer OPTION_MESSAGE_ACTIVITY_PUSH = 6;

    //7系统消息推送
    public static final Integer OPTION_MESSAGE_SYSTEM_PUSH = 7;

    //8.动态消息推送
    public static final Integer OPTION_MESSAGE_DYNAMIC_PUSH = 8;

    //9.消息列表查询
    public static final Integer OPTION_MESSAGE_QUERY = 9;

    //10.消息分类统计未读数查询
    public static final Integer OPTION_MESSAGE_QUERY_UNREADS = 10;

    //11.消息按分类查询消息列表
    public static final Integer OPTION_MESSAGE_QUERY_BY_TYPE = 11;

    //12.消息删除
    public static final Integer OPTION_MESSAGE_DELETE_BY_Id = 12;

    //13.消息未读改已读操作
    public static final Integer OPTION_MESSAGE_UPDATE_BY_Id = 13;

    //14.后台活动消息待发送列表查询
    public static final Integer OPTION_MESSAGE_PRE_SEND = 14;

    //15.后台活动消息已发送列表查询
    public static final Integer OPTION_MESSAGE_SENT = 15;

    //-----------------------消息大类中的小类-------------------------------

    //20.救助券消息(系统消息)
    public static Integer MESSAGE_SYSTEM_SALAVATION = 20;

    //21.爱心券消息(系统消息)
    public static Integer MESSAGE_SYSTEM_LOVE = 21;

    //22.爱心券激活(系统消息)
    public static Integer MESSAGE_SYSTEM_LOVE_ACTIVATION = 22;

    //23.提现通知(系统消息)
    public static Integer MESSAGE_SYSTEM_WITHDRAW = 23;

    //24.预约成功(系统消息)
    public static Integer MESSAGE_SYSTEM_APPOINT_SUCCESS = 24;

    //25.取消预约(系统消息)
    public static Integer MESSAGE_SYSTEM_APPOINT_CANCEL = 25;

    //26.预约过期(系统消息)
    public static Integer MESSAGE_SYSTEM_APPOINT_OUTDATE = 26;

    //27.问答回复(动态消息)
    public static Integer MESSAGE_DYNAMIC_QUESTION_REPLY = 27;

    //28.评论回复(动态消息)
    public static Integer MESSAGE_DYNAMIC_COMMENT_REPLY = 28;

    //29.获得点赞(动态消息)
    public static Integer MESSAGE_DYNAMIC_LIKE = 29;

    //30.活动消息小类(活动消息)
    public static Integer MESSAGE_ACTIVITY = 30;
    

    //上午
    public static final Integer AM = 1;

   //下午
   public static final Integer PM = 2;

    public static final String SEEDOCTOR_TYPE_INVALID = "请选择type:1 待就诊 2 已就诊  3已取消 4.已失效!";


    public static final String HOSPITAL_NOT_EXIST = "医院不存在!";

    public static final String HOSPITAL_ID_EMPTY = "医院id不存在!";

    public static final String OPTION_INVALID = "操作有误或不合法！";

    public static final String CANCEL_DATE_INVALID = "取消预约日期不合法，请至少提前一天取消!";

    public static final String TREAT_DATE_INVALID = "就诊日期不合法!";

    public static final String APPOINT_DATE_EMPTY = "预约日期参数为空!";

    public static final String APPOINT_DATE_INVALID= "请预约今天以后的日期!";

    public static final String REPEAT_APPOINT_ON_ONDATE= "个人在所在医院只能按预约日期当天预约一次!";

    public static final String ORDER_NOT_EXIST = "订单号不存在或为空!";

    public static final String EVALUATE_IS_EMPTY = "评价参数为空!";

    public static final String EVALUATE_METHOD_INVALID = "只有已就诊才能评价!";

    public static final String CANCEL_METHOD_INVALID = "只有待就诊状态才能取消，其他情况取消不合法!";

    public static final String EVALUATE_GRADE = "评分为空或不合法!";

    public static final String SERVICE_EVALUATION_CHOOSE_NONE = "服务评价至少选一项!";

    public static final String NUMBER_ID_EMPTY = "号源id不存在!";

    public static final String SEE_DOCTOR_EMTPTY = "就诊记录不存在!";

    public static final String NUMBER_EDIT_ERROR = "修改号源日期不能到今天以前!";

    public static final String NUMBER_EDIT_DATE_INVALID = "号源编辑日期不能修改!";

    public static final String AM_NUMBER_NOTALLOWED_LT= "修改上午的号源数不能小于之前上午的号源";

    public static final String PM_NUMBER_NOTALLOWED_LT= "修改下午的号源数不能小于之前下午的号源";

    public static final String AM_PM_NUMBER_EMPTY= "上午和下午的号码不能都为空!";

    public static final String NUMBER_ORIGIN_EMPTY = "当天可预约号源已取完或当天号源还未分配!";

    public static final String AM_PM_NUMBERS_EMPTY = "上/下午号源不能为空!";

    public static final String AM_NUMBER_USE_UP = "当日上午号源已用完!";

    public static final String PM_NUMBER_USE_UP = "当日下午号源已用完!";

    public static final String TREATE_DATE_DISMITCH = "订单表中的就诊日期与号缘表中的日期不匹配!";

    public static final String ORDER_EXPLAIN = "订单已失效!";

    public static final String DAY_HALF_STATUS_ERROR = "请输入1上午，2下午";

    public static final String WEEKDAY_ERROR = "星期错误";

    public static final String QUERY_PARAM_INVALID = "日期选择参数不合法!";

    public static final String OPERATION_TREAT_WAY_PARAM_INVALID = "线上线下就诊操作选择参数不合法!";

    public static final String OPTIN_PARAM_EMPTY = "操作码为空!";

    public static final String OPTION_SUMBIT_OR_EDIT_EMPTY = "添加或编辑参数为空!";

    public static final String OPTION_PARAM_INVALID = "请选择 1添加 2编辑 3删除操作!";

    public static final String REQUIRED_PARAM_INVALID = "必填参数不能为空!";

    public static final String ACTIVITY_INFO_INVALID = "活动消息内容文本不能为空!";

    public static final String STARTDATE_EMPTY = "起始时间不能为空!";

    public static final String NUMBER_REPEAT_ON_HOSPITAL = "一家医院不能在同一个日期上添加多次号源!";

    public static final String ENDDATE_EMPTY = "结束时间不能为空!";

    public static final String STARTDATE_INVALID = "起始时间不能选今天及今天以前!";

    public static final String ENDDATE_INVALID = "结束时间不能小于起始时间!";

    //---------------------海报----------------------
    //----------操作--------------------------------
    //增加
    public static final Integer OPTION_ADD = 1;
    //修改
    public static final Integer OPTION_EDIT = 2;
    //删除
    public static final Integer OPTION_DELETE = 3;
    //发布
    public static final Integer OPTION_PUBLISH = 4;
    //停止发布
    public static final Integer OPTION_CANCEL_PUBLISH = 5;
    //------------------------------------------------------
    //-------------------状态------------------------
    //待发布
    public static final Integer PRE_PUBLISE = 1;

    //已发布
    public static final Integer PUBLISED = 2;

    //停止发布
    public static final Integer STOP_PUBLISH = 3;

    //---------------------------------------------

    public static final String POSTERS_NOT_EXIST = "海报不存在!";

    //-------------------消息-----------------

    public static final String MESSAGE_SUBMIT_EMPTY = "提交参数为空!";

    public static final String MESSAGE_ID_EMPTY = "消息ID为空!";

    public static final String MESSAGE_NOT_EXIST = "消息配置不存在!";

    public static final String MESSAGE_CATEGORY_NAME_EMPTY = "消息类别名称为空!";

    public static final String DEST_UID_EMPTY = "需求用户ID";

    public static final String MESSAGE_BUSINESS_ID_EMPTY = "业务ID为空!";

    public static final String MESSAGE_SYSTEM_PARAM_EMPTY = "系统推送消息参数为空!";

    public static final String MESSAGE_DYNAMIC_PARAM_EMPTY = "动态推送消息参数为空!";

    public static final String AMOUNT_EMPTY = "金额为空!";

    public static final String PHONE_EMPTY = "手机号为空!";

    public static final String WECHART_ACCOUNT_EMPTY = "微信账号为空.";

    public static final String APPOINT_DETAL_EMPTY = "预约详情为空";

    public static final String NAME_EMPTY = "名字为空";

    public static final String REPLY_CONTENT_EMPTY = "回复内容为空";

    public static final String IDS_EMPTY = "id为空！";

    public static final String NUMBER_REPEAT_DATE = "医院号源重复添加日期！";



}
