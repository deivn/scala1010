package com.ymw.love.system.mq.receiver;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ymw.love.system.entity.UUser;
import com.ymw.love.system.mapper.UUserMapper;
import com.ymw.love.system.mq.ConstantMqQueue;
import com.ymw.love.system.service.BaseService;
import com.ymw.love.system.service.HuanxiService;
import com.ymw.love.system.util.SnowFlakeUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 作者 ：suhua
 * @date 创建时间：2019年8月12日 上午10:48:10 类说明：用户消费者
 */
@Component
@Slf4j
public class UUserReceiver extends BaseService{

	
	/**
	 * 新用户注册异步处理逻辑
	 *  1、创建环信id
	 */
	@RabbitListener(queues = ConstantMqQueue.USER_ENROLL)
	public void uUserEnroll(Map<String, String> map) {
		UUser u =new UUser();
		u.setHuanxinId("ax-"+SnowFlakeUtil.nextId());
		u.setId(map.get("userId"));
		//创建
		sf.getHuanxiService().creationUser(u.getHuanxinId(),map.get("nickName"));
		mf.getUUserMapper().updateById(u);
	}
	
	
	
	
	
	
	
	
	

}
