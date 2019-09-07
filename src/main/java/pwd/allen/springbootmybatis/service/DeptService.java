package pwd.allen.springbootmybatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pwd.allen.springbootmybatis.entity.Department;
import pwd.allen.springbootmybatis.mapper.DepartmentMapper;

/**
 * @author pwd
 * @create 2019-02-15 17:17
 **/
@Service
@CacheConfig(cacheNames = "dept")
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class DeptService {


    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * Cacheable注释：
     * key 和 keyGenerator 二选一,#id相当于#0或#p0或#a0
     * cacheManager指定缓存管理器;cacheResolver指定缓存解析器;二选一
     *
     * @param id
     * @return
     */
//    @Cacheable(key="#root.methodName + '[' + #id + ']'")
    @Cacheable(value = "dept")  //key默认使用SimpleKeyGenerator生成，为传参
    public Department getDept(Integer id) {
        return departmentMapper.getDeptById(id);
    }

    public int insertDept(Department department) {
        return departmentMapper.insertDept(department);
    }

    /**
     * CachePut：既执行方法，又更新缓存
     * 如果要更新上面getDept的缓存，这里的cacheName和key要和上面的方法一致
     * CachePut设置缓存是在方法执行后，所以key属性可以使用result获取方法结果
     * @param dept
     * @return
     */
    @CachePut(cacheNames = "dept", key="#result.id")
    public Department updateDept(Department dept) {
        departmentMapper.updateDept(dept);
        return dept;
    }

    /**
     * CacheEvict：缓存清除
     * allEntries：是否清除这个缓存中所有数据，默认为false
     * beforeInvocation：缓存清除是否在方法之前执行，默认为false，此时如果方法出错这不会清除缓存
     *
     * @param id
     */
    @CacheEvict(allEntries = true, beforeInvocation = true)
    public void deleteEmp(Integer id) {
//        departmentMapper.deleteDept(id);
    }
}
