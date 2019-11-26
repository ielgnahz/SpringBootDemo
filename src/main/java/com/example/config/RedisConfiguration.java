package com.example.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * RedisConfiguration
 *
 * @author zz
 * @date 2018/5/7
 */
//@Configuration
//@EnableCaching
public class RedisConfiguration extends CachingConfigurerSupport {

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.afterPropertiesSet();
        return jedisConnectionFactory;
    }

    @Bean
    public KeyGenerator wiselyKeyGenerator() {
        return new KeyGenerator() {
            private static final String SEPARATE = ":";

            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(SEPARATE).append(method);
                for (Object obj : params) {
                    sb.append(SEPARATE).append(obj);
                }
                return sb.toString();
            }
        };
    }

    /**
     * https://www.jianshu.com/p/9255b2484818
     *
     * TODO: 对 Spring @CacheXXX 注解进行扩展：注解失效时间 + 主动刷新缓存
     */
    @Bean
    public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisConnectionFactory connectionFactory) {
//        RedisCacheManager redisCacheManager =new RedisCacheManager(redisTemplate);
//        redisCacheManager.setTransactionAware(true);
//        redisCacheManager.setLoadRemoteCachesOnStartup(true);
//
//        // 最终在 Redis 中的 key = @Cacheable 注解中 'cacheNames' + 'key'
//        redisCacheManager.setUsePrefix(true);
//
//        // 所有 key 的默认过期时间，不设置则永不过期
//        // redisCacheManager.setDefaultExpiration(6000L);
//
//        // 对某些 key 单独设置过期时间
//        // 这里的 key 是 @Cacheable 注解中的 'cacheNames'
//        Map<String, Long> expires = new HashMap<>(10);
//        // expires.put("feedCategoryDto", 5000L);
//        // expires.put("feedDto", 5000L);
//        redisCacheManager.setExpires(expires);

        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        //设置CacheManager的值序列化方式为 fastJsonRedisSerializer,但其实RedisCacheConfiguration默认使用StringRedisSerializer序列化key，
        ClassLoader loader = this.getClass().getClassLoader();

        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer<>(loader.getClass());
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(fastJsonRedisSerializer);
        RedisCacheConfiguration defaultCacheConfig= RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);
        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
        return cacheManager;
    }


    // value serializer

    private Jackson2JsonRedisSerializer getJackson2JsonRedisSerializer() {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        return jackson2JsonRedisSerializer;
    }

    private GenericJackson2JsonRedisSerializer getGenericJackson2JsonRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    /**
     *
     * Once configured, the template is thread-safe and can be reused across multiple instances.
     * -- https://docs.spring.io/spring-data/data-redis/docs/current/reference/html/
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);

        // key serializer
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();


        RedisSerializer valueRedisSerializer;
        // -- 1 Jackson2JsonRedisSerializer
        // valueRedisSerializer = getJackson2JsonRedisSerializer();

        // -- 2 GenericJackson2JsonRedisSerializer
        valueRedisSerializer = getGenericJackson2JsonRedisSerializer();

        // set serializer
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(valueRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(valueRedisSerializer);

        template.afterPropertiesSet();
        return template;
    }
}


