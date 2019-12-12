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
	 * 小镇动态消息(保留3天)
	 */
	public String TOWN_TASK_DYNAMIC_MESSAGE = "town.task.dynamic.message";
	
	
	
	
	
	
	
}
