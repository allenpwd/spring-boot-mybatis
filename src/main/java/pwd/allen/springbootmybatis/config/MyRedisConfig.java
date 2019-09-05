package pwd.allen.springbootmybatis.config;


import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
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

//    @Bean
//    public RedisTemplate<Object, Department> deptRedisTemplate(
//            RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<Object, Department> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactory);
//        Jackson2JsonRedisSerializer<Department> ser = new Jackson2JsonRedisSerializer<>(Department.class);
//        template.setDefaultSerializer(ser);
//        return template;
//    }
//
//    @Bean
//    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
//        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofHours(1)); // 设置缓存有效期一小时
//        return RedisCacheManager
//                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
//                .cacheDefaults(redisCacheConfiguration).build();
//    }

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
