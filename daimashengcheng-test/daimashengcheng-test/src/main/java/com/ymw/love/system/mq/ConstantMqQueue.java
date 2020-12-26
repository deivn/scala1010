package com.ymw.love.system.mq;
/**
* @author  作者 ：suhua
* @date 创建时间：2019年8月12日 上午10:39:24
*类说明：队列名字
*/
public interface ConstantMqQueue {
	
	/**
	 * 新用户注册
	 */
	public String USER_ENROLL="user.enroll";

	/**
	 * 用户预约挂号号源减操作
	 */
	public String SEE_DOCTOR_NUMBER_OPTION_SUBTRACT = "number.option.subtract";

	/**
	 * 用户预约挂号号源加操作
	 */
	public String SEE_DOCTOR_NUMBER_OPTION_ADD = "number.option.add";

	/**
	 * 问答查看源数加操作
	 */
	 String DOCTOR_ADD_FAQ_VIEW = "faq.view.add";


	/**
	 * 问答分享记录添加操作
	 */
	 String DOCTOR_ADD_FAQ_SHARE = "faq.share.add";


	/**
	 * 咨询分享记录添加操作
	 */
	String DOCTOR_ADD_ADVISORY_SHARE = "advisory.share.add";

	/**
	 * 用户预约挂号密码短信发送
	 */
	public String SEE_DOCTOR_MESSAGE_SEND = "see.doctor.message.send";


	public String SYS_ADMIN_LOG="sys.admin.log";

	/**
	 * 海报分享统计
	 */
	public String POSTERS_SHARED_CACULATE = "posters.shared.caculate";
	/**
	 * 咨询查看源数加操作
	 */
	 String DOCTOR_ADD_ADVISORY_VIEW = "advisory.view.add";

	/**
	 * 广告点击量统计
	 */
	 String BANNER_CLICK_COUNT = "banner.click.count";


	/**
	 * 消息推送
	 */
	public String MESSAGE_PUSH = "message.push";




}
