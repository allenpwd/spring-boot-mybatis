package pwd.allen.springbootmybatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import pwd.allen.springbootmybatis.entity.Employee;
import pwd.allen.springbootmybatis.mapper.EmployeeMapper;

/**
 * @author lenovo
 * @create 2019-09-05 20:45
 **/
@Service
@CacheConfig(cacheNames = "emp")    //指定类的公共缓存配置
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class EmpService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    /**
     * id大于0时缓存生效，但是除非id=2
     * 使用自定义的key生成器
     * 如果sync=true则采用异步模式，此时不支持unless，如果使用unless，调用方法时会报错：@Cacheable(sync=true) does not support unless attribute on 'Builder[public pwd.allen.springbootmybatis.entity.Employee pwd.allen.springbootmybatis.service.EmpService.getEmpById(java.lang.Integer)] caches=[emp] | key='' | keyGenerator='myKeyGenerator' | cacheManager='' | cacheResolver='' | condition='#a0 > 0' | unless='#p0 == 2' | sync='true''
     *
     * @param id
     * @return
     */
    @Cacheable(keyGenerator = "myKeyGenerator", condition = "#a0 > 0", unless = "#p0 == 2", sync = false)
    public Employee getEmpById(Integer id) {
        return employeeMapper.getEmpById(id);
    }

    @Caching(
            cacheable = {
                    @Cacheable(value = "emp", key = "#lastName")
            },
            put = {
                    @CachePut(cacheNames = "emp", key = "#result.id"),
                    @CachePut(value = "emp", key = "#result.email")
            }
    )
    public Employee getEmptByLastName(String lastName) {

        /** 手动添加缓存 begin **/
        //方式一：使用cacheManager
        Employee emp = cacheManager.getCache("emp").get("allen", Employee.class);
        System.out.println(emp);


        /*
        遇到的问题，获取不到缓存。
        原因：RedisTemplate和CacheManager设置的keySerializer要一致，
        如果redisTemplate用的是Jackson2JsonRedisSerializer，emp::allen左右两边会多双引号
        解决方式：template.setKeySerializer(new StringRedisSerializer())
        */
        //方式二：使用redisTemplate
        Object obj = redisTemplate.opsForValue().get("emp::allen");
        /** 手动添加缓存 end **/

        return employeeMapper.getEmptByLastName(lastName);
    }
}
