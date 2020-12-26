package com.ymw.love.system.mq.sender;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ymw.love.system.mq.ConstantMqQueue;

/**
* @author  作者 ：suhua
* @date 创建时间：2019年8月12日 上午10:47:04
*类说明：用户基本 生产者
*/
@Component
public class UUserSender {
	
	@Autowired
    private AmqpTemplate rabbitTemplate;
	
	/**
	 * 新用户注册异步处理逻辑
	 * 1、创建环信id
	 */
	public void uUserEnroll(String userId,String nickName) {
		Map<String, String> map =new HashMap<String, String>();
		map.put("userId",userId );
		map.put("nickName", nickName);
		rabbitTemplate.convertAndSend(ConstantMqQueue.USER_ENROLL,map);
	}
	
	/**
	 * 用户添加积分
	 * 
	 */
	public void uUserAddScore() {
		
	}
	
	
	

}
