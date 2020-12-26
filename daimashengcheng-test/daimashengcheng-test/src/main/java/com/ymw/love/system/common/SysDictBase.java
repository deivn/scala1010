package com.ymw.love.system.common;

/**
 * @author 作者 ：suhua
 * @date 创建时间：2019年8月13日 类说明：字典
 */
public interface SysDictBase {
	
     //大类
	public interface SysType {
		/**
		 * 积分类型
		 */
		Integer score = 1;
		/**
		 * 职称
		 */
		Integer position=2;
		
		/**
		 * 部门
		 */
		Integer sector =3;

	}
	
	

	 //积分类型
	public interface ScoreType {
		Integer convey_article = 1;
	}

}
