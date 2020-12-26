package com.ymw.love.system.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.ymw.love.system.common.SystemEnum;
import com.ymw.love.system.config.SmsConfig;
import com.ymw.love.system.config.excep.MissRequiredParamException;
import com.ymw.love.system.immobile.HintTitle;
import lombok.extern.slf4j.Slf4j;


/**
* @author  作者 ：suhua
*类说明：发送短信
*/
@Repository
@Slf4j
public class SMSSerivce {
    
	@Autowired
	private SmsConfig smsConfig;
	/**
	 * 发送验证码
	 * @param phone
	 * @param content
	 */
  public  Map<String, Object> SendSms(String phone, String  content,String TemplateId) {
	        try {
	            CloseableHttpClient client = null;
	            CloseableHttpResponse response = null;
	            try {
	                List<BasicNameValuePair> formparams = new ArrayList<>();
	                formparams.add(new BasicNameValuePair("Account",smsConfig.getAccount()));
				    formparams.add(new BasicNameValuePair("Pwd",smsConfig.getPwd()));//登录后台 首页显示
				    formparams.add(new BasicNameValuePair("Content",content));
				    formparams.add(new BasicNameValuePair("Mobile",smsConfig.getCountryCode()+phone));//手机号码
				    formparams.add(new BasicNameValuePair("SignId",smsConfig.getSignId()));//登录后台 添加签名获取id	
				    formparams.add(new BasicNameValuePair("TemplateId",TemplateId));
	                HttpPost httpPost = new HttpPost(smsConfig.getSmsUrl());
	                httpPost.setEntity(new UrlEncodedFormEntity(formparams,"UTF-8"));
	                client = HttpClients.createDefault();
	                response = client.execute(httpPost);
	                HttpEntity entity = response.getEntity();
	                String result = EntityUtils.toString( entity);
	                log.info("短信发送返回 "+phone+": "+result);
	                return JSON.parseObject(result, Map.class);
	            } finally {
	                if (response != null) {
	                    response.close();
	                }
	                if (client != null) {
	                    client.close();
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new MissRequiredParamException(SystemEnum.SYSTEM_ERROR, HintTitle.System.System_505_SMS);
	        }
	    }
	 
	
}
