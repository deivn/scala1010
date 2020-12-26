package com.ymw.love.system.mq;

import com.ymw.love.system.entity.seeDoctor.UTakeNumber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
/**
* @author  作者 ：suhua
* @date 创建时间：2019年4月10日 下午3:34:41
*类说明：声明队列
*/
@Configuration
public class RabbitConfig {
	/**
	 *新用户注册
	 */
	@Bean
    public Queue userEnroll() {
    	return new Queue(ConstantMqQueue.USER_ENROLL);
    }


	 /**
	  * 后台日志记录
	  * @return
	  */
	@Bean
   public Queue sysAdminLog() {
   	return new Queue(ConstantMqQueue.SYS_ADMIN_LOG);
   }


	/**
	 *用户预约挂号号源数减操作
	 */
	@Bean
	public Queue subTractNumber(){
		return new Queue(ConstantMqQueue.SEE_DOCTOR_NUMBER_OPTION_SUBTRACT);
	}

	/**
	 *用户取消预约挂号号源数加操作
	 */
	@Bean
	public Queue addNumber(){
		return new Queue(ConstantMqQueue.SEE_DOCTOR_NUMBER_OPTION_ADD);
	}


	/**
	 * 问答查看源数加操作
	 */
	@Bean
	public Queue addFaqView(){
		return new Queue(ConstantMqQueue.DOCTOR_ADD_FAQ_VIEW);
	}


	/**
	 *用户预约挂号密码短信发送
	 */
	@Bean
	public Queue seeDoctorMessageSend(){
		return new Queue(ConstantMqQueue.SEE_DOCTOR_MESSAGE_SEND);
	}

	/**
	 * 海报分享统计
	 */
	@Bean
	public Queue postersCaculate(){
		return new Queue(ConstantMqQueue.POSTERS_SHARED_CACULATE);
	}


	/**
	 * 问答分享记录添加操作
	 */
	@Bean
	public Queue addFaqShare(){
		return new Queue(ConstantMqQueue.DOCTOR_ADD_FAQ_SHARE);
	}

	/**
	 * 咨询分享记录添加操作
	 */
	@Bean
	public Queue addAdvisoryShare(){
		return new Queue(ConstantMqQueue.DOCTOR_ADD_ADVISORY_SHARE);
	}


	/**
	 * 咨询查看源数加操作
	 */
	@Bean
	public Queue addAdvisoryView(){
		return new Queue(ConstantMqQueue.DOCTOR_ADD_ADVISORY_VIEW);
	}

	/**
	 * 广告点击量统计
	 */
	@Bean
	public Queue bannerClickCount(){
		return new Queue(ConstantMqQueue.BANNER_CLICK_COUNT);
	}



	/**
	 * 消息推送
	 */
	@Bean
	public Queue messagePush(){
		return new Queue(ConstantMqQueue.MESSAGE_PUSH);
	}
}
