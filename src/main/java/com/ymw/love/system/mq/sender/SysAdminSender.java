package com.ymw.love.system.mq.sender;

import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ymw.love.system.mq.CommonConstantMqQueue;
import com.ymw.love.system.mq.entity.SysLog;

/**
* @author  作者 ：suhua
* @date 创建时间：2019年8月20日 下午5:55:17
*类说明：
*/
@Component
public class SysAdminSender {

	@Autowired
    private AmqpTemplate rabbitTemplate;
	
	/**
	 * 后台系统操作日志
	 */
	public void sysAdminLog(String aUserId,String describe) {
		SysLog sysLog =new SysLog();
		sysLog.setType(1);
		sysLog.setAuserId(aUserId);
		sysLog.setCreatesTime(new Date());
		sysLog.setActionDescribe(describe);
		rabbitTemplate.convertAndSend(CommonConstantMqQueue.SYS_ADMIN_LOG,sysLog);
		
	}
	
	
}
