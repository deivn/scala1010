package com.ymw.love.system.mq.entity;

import java.io.Serializable;

import lombok.Data;

/**
* @author  作者 ：suhua
* @date 创建时间：2019年12月9日 
*类说明：小镇任务
*/
@Data
public class TownTask implements  Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4651066591314219454L;


	public TownTask(){}

	public TownTask(String uid, Integer subType){
		this.uid = uid;
		this.subType = subType;
	}

	public TownTask(String uid, Integer type, Integer subType, String name, Integer energy){
		this.uid = uid;
		this.type = type;
		this.subType = subType;
		this.name = name;
		this.energy = energy;
	}

	private String uid;

	/**
	 * 1.新手礼包 2.每日任务 3.挑战任务
	 */
	private Integer type;

	/**
	 * 1.分享小镇 2.邀请注册 3.评论文章 4.分享文章 5.点赞文章 6.注册就送 7.修改昵称 8.上传头像 9.实名认证 10.完善资料 11.关注公众号
	 */
	private Integer subType;

	/**
	 * 任务名
	 */
	private String name;
	/**
	 * 任务说明
	 */
	private String introduce;
	
	
    private Integer energy;

}
