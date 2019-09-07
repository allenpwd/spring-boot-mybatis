package pwd.allen.springbootmybatis.config;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;
import pwd.allen.springbootmybatis.entity.Department;
import pwd.allen.springbootmybatis.entity.Employee;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;

/**
 * 使用缓存步骤：
 * 1）引入依赖（spring-boot-starter-cache），开启基于注解的缓存：@EnableCaching
 * 2）在要使用缓存的类（如@CacheConfig）和方法上标注缓存注解
 * -@Cacheable：将方法的运行结果进行缓存；如果缓存中已有则从缓存中获取数据，不会调用方法
 *  属性：
 *      cacheNames/value：指定缓存组件的名字
 *      key：缓存数据时使用的key；默认使用方法参数的值；支持SPEL表达式
 * -@CachePut：
 * -@CacheEvict：
 *
 * CacheManager管理多个Cache组件，对缓存的真正CRUD操作在Cache组件，每一个缓存组件有自己唯一一个名字；
 *
 *  CacheAutoConfiguration会按顺序匹配缓存
 * 若只引入spring-boot-starter-cache，默认SimpleCacheConfiguration生效
 *
 * SimpleCacheConfiguration分析：
 * 给容器注册一个CacheManager【ConcurrentMapCacheManager】,作用是获取和创建Cache【ConcurrentMapCache，基于ConCurrentMap保存数据】
 * @Cacheable分析：
 *      执行方法之前，先从缓存中查（从cacheManager按照cacheNames指定的名字获取缓存组件cache，从cache根据生产的key获取缓存）
 *      key是按照某种策略生成的，默认使用keyGenerator【SimpleKeyGenerator】，
 *      如果缓存中没有，则调用目标方法，并将返回值缓存起来
 *          1.
 *
 */
@Configuration
public class MyRedisConfig {

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Object> ser = new Jackson2JsonRedisSerializer<>(Object.class);

        //如果不加上这个，redisTemplate获取json格式对象时返回的是LinkedHashMap；加上这个就能正常返回对象实例
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        ser.setObjectMapper(om);

        template.setDefaultSerializer(ser);
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }


    /**
     * 自定义cacheManager，使redis以json格式缓存对象
     * usePrefix：使用前缀，默认会将cacheName作为可以的前缀；这个是必要的，否则无法区分不同cache
     *
     * @param factory
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        //必须加这个，否则缓存转换会java.lang.ClassCastException异常
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        // 配置序列化（解决乱码的问题）
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                .disableCachingNullValues();

        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
        return cacheManager;
    }

    /**
     * 自定义缓存key生成策略
     *
     * @return
     */
    @Bean("myKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                return method.getName() + "[" + Arrays.asList(params).toString() + "]";
            }
        };
    }

}
