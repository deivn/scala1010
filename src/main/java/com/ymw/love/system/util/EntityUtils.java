package com.ymw.love.system.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date : 2018/11/21 14:54
 * @Describe: 描述
 */
public class EntityUtils {
    /**
     * @author :xutang
     * @Project: ebuyhouse
     * @Date : 2018/11/21 15:40
     * @Describe: map去空
     */
    public  Map<String, Object> mapNull(Map<String, Object> map){
            for (Map.Entry<String, Object> map1:map.entrySet()) {
                if(StringUtils.isEmpty(map1.getValue())){
                    map.put(map1.getKey(),"");
                }
            }
        return map;
    }
    /**
     * 实体类转Map
     * @param object
     * @return
     */
    public  Map<String, Object> entityToMapNotNull(Object object) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (Field field : object.getClass().getDeclaredFields()){
            try {
                boolean flag = field.isAccessible();
                field.setAccessible(true);
                Object o = field.get(object);
                if(StringUtils.isNotEmpty(o)){
                    map.put(field.getName(), o);
                }
                field.setAccessible(flag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }
    /**
     * 实体类转Map
     * @param object
     * @return
     */
    public  Map<String, Object> entityToMap(Object object) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (Field field : object.getClass().getDeclaredFields()){
            try {
                boolean flag = field.isAccessible();
                field.setAccessible(true);
                Object o = field.get(object);
                map.put(field.getName(), o);
                field.setAccessible(flag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * Map转实体类
     * @param map 需要初始化的数据，key字段必须与实体类的成员名字一样，否则赋值为空
     * @param entity  需要转化成的实体类
     * @return
     */
    public  <T> T mapToEntity(Map<String, Object> map, Class<T> entity) {
        T t = null;
        try {
            t = entity.newInstance();
            for(Field field : entity.getDeclaredFields()) {
                if (map.containsKey(field.getName())) {
                    boolean flag = field.isAccessible();
                    field.setAccessible(true);
                    Object object = map.get(field.getName());
                    if (object!= null && field.getType().isAssignableFrom(object.getClass())) {
                        field.set(t, object);
                    }
                    field.setAccessible(flag);
                }
            }
            return t;
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return t;
    }
    /**
     * 实体转实体类
     * @param object 需要初始化的数据，key字段必须与实体类的成员名字一样，否则赋值为空
     * @param entity  需要转化成的实体类
     * @return
     */
    public  <T> T  toEntity(Object object, Class<T> entity) {
        Map<String, Object> map = entityToMap(object);
        return mapToEntity(map,entity);
    }
    /**
     * 实体转实体类有去空的
     * @param object 需要初始化的数据，key字段必须与实体类的成员名字一样，否则赋值为空
     * @param entity  需要转化成的实体类
     * @return
     */
    public <T> T  toEntityNull(Object object, Class<T> entity) {
        Map<String, Object> map = entityToMap(object);
        mapNull(map);
        return mapToEntity(map,entity);
    }
}
