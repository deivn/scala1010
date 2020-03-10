package com.ymw.love.system.redis;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ymw.love.system.util.StringUtils;

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
     * 获取key过期时间
     * @param key
     */
    public Long getExpire(String key) {
    	return redisTemplate.getExpire(key);
    }
    
    /**
     * 修改key的值
     * @param key
     * @param t
     */
    public void updateKey(String key, T t) {
    	set(key, t,getExpire(key));
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

    /**
     * 增加(自增长), 负数则为自减
     *
     * @param key
     * @return
     */
    public Long incr(String key, Long result) {
        try {
            result = redisTemplate.opsForValue().increment(key);
        } catch (Exception e) {
        }
        return result;
    }
    
    /**
     * 加锁
     * @param key   键
     * @param value 当前时间 + 超时时间
     * @param time  设置失效时间
     * @return 是否拿到锁
     */
    public boolean lock(String key, String value,Long time) {
        if (redisTemplate.opsForValue().setIfAbsent(key, value,time ,TimeUnit.SECONDS)) {
            return true;
        }
        String currentValue = (String) redisTemplate.opsForValue().get(key);
        //如果锁过期
        if (StringUtils.isNotEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            String oldValue = (String) redisTemplate.opsForValue().getAndSet(key, value);
            //是否已被别人抢占
            return StringUtils.isNotEmpty(oldValue) && oldValue.equals(currentValue);
        }
        return false;
    }
 
    /**
     * 解锁
     *
     * @param key   键
     * @param value 当前时间 + 超时时间
     */
    public void unlock(String key, String value) {   
            String currentValue = (String) redisTemplate.opsForValue().get(key);
            if (StringUtils.isNotEmpty(currentValue) && currentValue.equals(value)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
    }
    
    /**
     * 获取一个list队列
     * @return
     */
    public ListOperations<String, Object>  getListQueue() {
    	return redisTemplate.opsForList();
    }


    public void hset(String key, String item, Object value) {
        redisTemplate.opsForHash().put(key, item, value);
    }

    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }
}
