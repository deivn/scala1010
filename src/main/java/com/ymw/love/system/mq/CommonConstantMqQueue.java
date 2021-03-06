package com.ymw.love.system.mq;
/**
* @author  作者 ：suhua
* @date 创建时间：2019年12月6日 
*类说明：公共
*/
public interface CommonConstantMqQueue {
	public String SYS_ADMIN_LOG="sys.admin.log";
	
	/**
	 * 新手任务队列
	 */
	public String TOWN_NEW_TASK="town.new.task";

	/**
	 * 每日任务
	 */
	public String TOWN_DAILY_TASK = "town.daily.task";

	/**
	 * 完成后，能量发送统计
	 */
	public String TOWN_TASK_FINISH_ADD="town.task.finish.add";

	/**
	 * 完成任务后，设置能量过期
	 */
	public String TOWN_TASK_ENERGY_EXPIRED="town.task.energy.expired";

	/**
	 * 消息推送
	 */
	public String MESSAGE_PUSH = "message.push";

	/**
	 * 走路获取能量过期，往后2天8点
	 */
	public String TOWN_WALKING_ENERGY_EXPIRED = "town.walking.enegry.expired";

	/**
	 * 每日签到，24小时过期
	 */
	public String TOWN_SIGN_ENERGY_EXPIRED = "town.sign.enegry.expired";

	/**
	 * 能量过期消息推送，每天用户首次打开APP推送一次
	 */
	public String ENERGY_EXPIRED_SIGNAL = "energy.expired.signal";
	
	/**
	 * 抢红包能量（新用户注册）
	 */
	public String REGISTER_MOVE_ENERGY="register.move.energy";

}
