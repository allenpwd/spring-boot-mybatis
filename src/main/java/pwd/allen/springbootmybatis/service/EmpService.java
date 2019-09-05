package pwd.allen.springbootmybatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pwd.allen.springbootmybatis.entity.Employee;
import pwd.allen.springbootmybatis.mapper.EmployeeMapper;

/**
 * @author lenovo
 * @create 2019-09-05 20:45
 **/
@Service
@CacheConfig(cacheNames = "emp")
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class EmpService {

    @Autowired
    private EmployeeMapper employeeMapper;

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
}
