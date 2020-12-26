package com.ymw.love.system.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.ymw.love.system.util.HttpsRequest;

import lombok.extern.slf4j.Slf4j;

/**
* @author  作者 ：suhua
* @date 创建时间：2019年8月2日 下午5:33:42
*类说明：环信
*/
@Repository
@Slf4j
public class HuanxiService {
	
	public static final String URL = "https://a1.easemob.com/1137181009253489/ebuyhouse";
	
    /**
     * 创建用户
     * @param hxId 环信id
     * @param nickName 别名
     */
	public Map<String, Object> creationUser(String hxId,String nickName) {
		Map<String, String> map =new HashMap<String, String>();
		map.put("username", hxId);//环信 ID ;也就是 IM 用户名的唯一登录账号
		map.put("password", "ab9874");//默认密码
		map.put("nickname", nickName);//昵称（可选），在 iOS Apns 推送时会使用的昵称
		String str=JSON.toJSONString(map); 
	    String param =  new HttpsRequest().httpsRequest(URL+"/users", "POST", str, null);
	    log.info("创建环信用户返回： {}",param);
	    return  JSON.parseObject(param, Map.class);
	}
	
	
	
	/**
	 * 更新别名
	 */
	public void updateUserNickName(String hxId,String nickName) {
		
		
		
	}
	
	
	
}
