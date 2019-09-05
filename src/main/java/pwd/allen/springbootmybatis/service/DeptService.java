package pwd.allen.springbootmybatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
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
    @Cacheable(key="#root.methodName + '[' + #id + ']'")
    public Department getDept(Integer id) {
        return departmentMapper.getDeptById(id);
    }

    public int insertDept(Department department) {
        return departmentMapper.insertDept(department);
    }
}
