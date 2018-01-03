package com.yunu;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;

/**
 * @author 刘江
 * @version 1.0
 * <p>实现redis缓存框架，采用cache便签实现具体的缓存流程</p>
 */

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport
{
    /**
     * 创建redis操作接口
     * @param redisConnectionFactory
     * @return redis操作接口
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory)
    {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        GenericFastJsonRedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        redisTemplate.setDefaultSerializer(fastJsonRedisSerializer);
        return redisTemplate;
    }

    /**
     * 生成缓存自定义Key产生器
     * @return 缓存Key产生器
     */
    @Bean(name = "KeyGenerator")
    public KeyGenerator keyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                if(objects[0] instanceof JSONObject) {
                    StringBuffer sb= new StringBuffer();
                    JSONObject data = (JSONObject) objects[0];
                    for(int i=1; i< objects.length; i++)
                    {
                        sb.append(String.valueOf(data.get(objects[i])));
                    }
                    return sb.toString();
                }
                else {
                    return objects[0];
                }
            }
        };
    }

    /**
     * 自定义缓存管理器的过期时间
     * @param redisTemplate
     * @param expireTime
     * @return CacheManager 缓存过期管理器
     */
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate, @Value("${expire_time}")long expireTime) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setDefaultExpiration(expireTime);
        return cacheManager;
    }
}
