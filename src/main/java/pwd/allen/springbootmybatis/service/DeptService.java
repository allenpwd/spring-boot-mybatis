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
public class DeptService {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private DepartmentMapper departmentMapper;

    @Cacheable(key="#root.methodName + '[' + #id + ']'")
    public Department getDept(Integer id) {
        return departmentMapper.getDeptById(id);
    }

    public int insertDept(Department department) {
        return departmentMapper.insertDept(department);
    }
}
