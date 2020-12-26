package com.ymw.love.system.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import cn.jpush.api.push.model.audience.Audience;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JpushNotification {

    //标题
    private String title;
   
    //内容
    private String alert;
    
    
    
  //------------------扩展属性------------------ 
    private String http;

    private String imageUrl;
    
    private int badge;

    private int jumpType;
    
    private String args;//参数
    
    private Map<String, String> extras;
    
    
    
    //------------------------发送方式------------------------------------------

    private List<String> aliases;//别名推送 一次推送最多 1000 个。

    private List<String> tags;// 数组。多个标签之间是 OR 的关系，即取并集。 一次推送最多 20 个

    private List<String> tagAnd;//数组。多个标签之间是 AND 关系，即取交集. 一次推送最多 20 个
    
    private List<String> tagNot;//数组。多个标签之间，先取多标签的并集，再对该结果取补集。一次推送最多 20 个
    
    private List<String> registrationId;//数组。多个注册 ID 之间是 OR 关系，即取并集,一次推送最多 1000 个
    




    public Map<String, String> getExtras() {
    	extras.put("http", this.http);
        extras.put("badge", ""+this.badge);
        extras.put("jumpType", ""+this.jumpType);
        extras.put("imageUrl", this.imageUrl);
        extras.put("args", this.args);
        return extras;
    }

  
}
