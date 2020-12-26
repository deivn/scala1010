package com.ymw.love.system.mq.receiver;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ymw.love.system.entity.SysLog;
import com.ymw.love.system.entity.UUser;
import com.ymw.love.system.mapper.sys.SysLogMapper;
import com.ymw.love.system.mq.ConstantMqQueue;
import com.ymw.love.system.util.SnowFlakeUtil;

import lombok.extern.slf4j.Slf4j;

/**
* @author  作者 ：suhua
* @date 创建时间：2019年8月20日 
*类说明：
*/
@Component
@Slf4j
public class SysAdminReceiver {

	@Autowired
	private SysLogMapper sysLogMapper;
	
	
	/**
	 * 后台操作日志
	 * @param map
	 */
	@RabbitListener(queues = ConstantMqQueue.SYS_ADMIN_LOG)
	public void sysAdminLog(SysLog arg) {
		sysLogMapper.insert(arg);
	}
	
}
