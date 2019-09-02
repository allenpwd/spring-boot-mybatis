package pwd.allen.springbootmybatis.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import pwd.allen.springbootmybatis.entity.Department;

import java.time.Duration;

/**
 * 使用缓存步骤：
 * 1）开启基于注解的缓存：@EnableCaching
 * 2）在要使用缓存的方法上标注缓存注解
 * -@Cacheable：将方法的运行结果进行缓存；如果缓存中已有则从缓存中获取数据，不会调用方法
 *  属性：
 *      cacheNames/value：指定缓存组件的名字
 *      key：缓存数据时使用的key；默认使用方法参数的值；支持SPEL表达式
 * -@CachePut：
 * -@CacheEvict：
 *
 * CacheManager管理多个Cache组件，对缓存的真正CRUD操作在Cache组件，每一个缓存组件有自己唯一一个名字；
 *
 */
@Configuration
public class MyRedisConfig {

    @Bean
    public RedisTemplate<Object, Department> deptRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Department> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Department> ser = new Jackson2JsonRedisSerializer<>(Department.class);
        template.setDefaultSerializer(ser);
        return template;
    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(1)); // 设置缓存有效期一小时
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }

}
