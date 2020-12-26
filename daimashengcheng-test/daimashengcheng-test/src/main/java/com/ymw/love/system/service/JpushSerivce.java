package com.ymw.love.system.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import com.alibaba.fastjson.JSON;
import com.ymw.love.system.common.JpushNotification;
import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.push.model.notification.PlatformNotification;
import lombok.extern.slf4j.Slf4j;

/**
* @author  作者 ：suhua
* @date 创建时间：2019年8月2日 下午5:31:56
*类说明：推送极光
*/
@Repository
@Slf4j
public class JpushSerivce {

	@Value("${jpush.secret}")
	private String secret;
	
	@Value("${jpush.app-key}")
	private String appKey;
	
	@Value("jpush.ios.apn")
	private String apnFlag;
	  
//  /**
//   * 构建推送对象：所有平台，推送目标是别名为 alias是生成的32位UUID，通知内容为 ALERT。
//   * @param alias  别名
//   * @return
//   */
//  public static PushPayload buildPushObject_all_alias_alert(String alias) {
//      return PushPayload.newBuilder()
//              .setPlatform(Platform.all())
//              .setAudience(Audience.alias(alias))
//              .setNotification(Notification.alert(ALERT))
//              .build();
//  }

	
	
	
	
	/**
	 * 别名推送
     * @param type 推送方式：1 全部设备 ，2：别名
	 * @param notificationExtras
	 * @return
	 */
	public  Map<String, Object> sendPushAlias(JpushNotification notificationExtras, Integer type){
	   return	sendPush(notificationExtras, type);
	}
	
	
  /**
   * 极光推送 消息发送
   * @param type 推送方式：1 全部设备 ，2：别名
   * @param notificationExtras
   * @return
   */
  private  Map<String, Object> sendPush(JpushNotification notificationExtras,Integer type){
      Map<String, Object>  map = new HashMap<String, Object>();
      JPushClient jpushClient = new JPushClient(secret, appKey, null, ClientConfig.getInstance());
      PushPayload pushPayload = buildPushObject(notificationExtras,type);
      int responseCode = 500;
      try {
          PushResult pushResult = jpushClient.sendPush(pushPayload);
          responseCode = pushResult.getResponseCode();
         
          map.put("msgId",pushResult.msg_id);
          jpushClient.close();
      } catch (APIConnectionException e) {
          e.printStackTrace();
          map.put("pushFailInfo",e.getMessage());
          
      } catch (APIRequestException e) {
          e.printStackTrace();
          map.put("pushFailInfo",e.getErrorMessage());
      }
      map.put("responseCode", responseCode);
      
     log.info("极光推送返回:"+JSON.toJSONString(map)+"  提交参数："+JSON.toJSONString(notificationExtras));
     return map;
  }


  /**
   * 
   *.setApnsProduction(true) 默认为false，只针对IOS，IOS在测试环境的时候为FASE，在生产环境时需要改成true
   * @param notificationExtras
   * @param type 推送方式：1 全部设备 ，2：别名
   * @return
   */
  private  PushPayload buildPushObject(JpushNotification notificationExtras,Integer type) {
	  
	  if(type==1) {
		  return PushPayload.alertAll(notificationExtras.getAlert());
	  }
	  
     
	  Builder builder=  PushPayload.newBuilder();
	    builder.setPlatform(Platform.all())
	    .setNotification(buildPushObject_all_notification(notificationExtras))
        .setOptions(Options.newBuilder()
                //IOS线上环境使用的时候要注意，这里要放开，推送至苹果服务器
                .setApnsProduction(Boolean.valueOf(apnFlag))
                .build());
        
	   if(type==2) {//别名推送
		   builder.setAudience(Audience.alias(notificationExtras.getAliases()));
	   }
	  
	   
	  
//	  return PushPayload.newBuilder()
//              .setPlatform(Platform.all())
//              .setAudience(Audience.alias(notificationExtras.getAliases()))
//              .setNotification(buildPushObject_all_notification(notificationExtras))
//              .setOptions(Options.newBuilder()
//                      //IOS线上环境使用的时候要注意，这里要放开，推送至苹果服务器
//                      .setApnsProduction(Boolean.valueOf(apnFlag))
//                      .build())
//              .build();
	    
	    return  builder.build();
  }
  
 


  /**
   * 构建全平台推送消息体 ios android
   * @param notificationExtras
   * @return
   */
  private  Notification buildPushObject_all_notification(JpushNotification notificationExtras) {
      return Notification.newBuilder()
              .setAlert(notificationExtras.getAlert())
              .addPlatformNotification(buildPushObject_android_notification(notificationExtras))
              .addPlatformNotification(buildPushObject_ios_notification(notificationExtras))
              .build();
  }

  /**
   * 构建 android 主体消息
   * @param notificationExtras
   * @return
   */
  private  PlatformNotification buildPushObject_android_notification(JpushNotification notificationExtras) {
      return AndroidNotification.newBuilder()
              .setTitle(notificationExtras.getTitle())
              .addExtras(notificationExtras.getExtras())
              .build();
  }

  /**
   * 构建 ios 主体消息
   * @param notificationExtras
   * @return
   */
  private  PlatformNotification buildPushObject_ios_notification(JpushNotification notificationExtras) {
      return IosNotification.newBuilder()
              .incrBadge(notificationExtras.getBadge())
              .addExtras(notificationExtras.getExtras())
              .build();
  }



}
