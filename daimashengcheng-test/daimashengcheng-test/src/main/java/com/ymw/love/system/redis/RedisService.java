package com.ymw.love.system.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Date : 15:12 2018/10/16
 * @Describe: redis公方法
 */
@Service
@Transactional
public class RedisService<T> {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * @Project: ebuyhouse
     * @author :xutang
     * @Date : 15:12 2018/10/16
     * @Describe: object类型值的set方法
     */
    public void set(String key, T t) {
        redisTemplate.opsForValue().set(key, t);
    }
    /**
     * @Project: ebuyhouse
     * @author :xutang
     * @Date : 15:13 2018/10/16
     * @Describe: 带有效期的object类型值的set方法
     */
    public void set(String key, T t,Long time) {
        redisTemplate.opsForValue().set(key, t, time ,TimeUnit.SECONDS);
    }
    /**
     * @Project: ebuyhouse
     * @author :xutang
     * @Date : 15:13 2018/10/16
     * @Describe: 获取object类型值的git方法
     */
    public T get(String key) {
        return (T) redisTemplate.boundValueOps(key).get();
    }
    /**
     * @Project: ebuyhouse
     * @author :xutang
     * @Date : 15:13 2018/10/16
     * @Describe: 判断Key是否存在
     */
    public Boolean gethas(String key) {
        return  redisTemplate.hasKey(key);
    }
    /**
     * @Project: ebuyhouse
     * @author :xutang
     * @Date : 15:19 2018/10/16
     * @Describe: 删除object类型key
     */
    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }
    
    /**
     * 模糊删除
     * 
     * @param key 以*结尾
     */
    public void deleteDedim(String key) {
        stringRedisTemplate.delete(stringRedisTemplate.keys(key));
    }
    
    /**
     * @Project: ebuyhouse
     * @author :xutang
     * @Date : 15:19 2018/10/16
     * @Describe: set字符串类型值
     */
    public void setCode(String key, String code) {
        stringRedisTemplate.opsForValue().set(key, code);
    }
    /**
     * @Project: ebuyhouse
     * @author :xutang
     * @Date : 15:20 2018/10/16
     * @Describe: set带时间的字符串类型
     */
    public void setCode(String key, String code,Long time){
        stringRedisTemplate.opsForValue().set(key, code, time ,TimeUnit.SECONDS);
    }
    /**
     * @Project: ebuyhouse
     * @author :xutang
     * @Date : 15:20 2018/10/16
     * @Describe: git字符串值
     */
    public String getCode(String key) {
        return stringRedisTemplate.boundValueOps(key).get();
    }
    /**
     * @Project: ebuyhouse
     * @author :xutang
     * @Date : 15:13 2018/10/16
     * @Describe: 判断Key是否存在
     */
    public Boolean getCodeHas(String key) {
        return  stringRedisTemplate.hasKey(key);
    }
    /**
     * @Project: ebuyhouse
     * @author :xutang
     * @Date : 15:20 2018/10/16
     * @Describe: 删除字符串key
     */
    public void deleteCode(String key) {
        stringRedisTemplate.delete(key);
    }
    
 
}
