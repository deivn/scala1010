package com.ymw.love.system.mq.sender;
import com.ymw.love.system.mq.CommonConstantMqQueue;
import com.ymw.love.system.mq.entity.TownTask;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     *
     *小镇每日任务完成记录
     */
    public void townDailyTask(TownTask task){
        rabbitTemplate.convertAndSend(CommonConstantMqQueue.TOWN_DAILY_TASK, task);
    }
    
    /**
	 * 新手任务
	 */
	public void townNewTask(TownTask arg) {
		rabbitTemplate.convertAndSend(CommonConstantMqQueue.TOWN_NEW_TASK,arg);	
	}
}
