package pwd.allen.springbootmybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import pwd.allen.springbootmybatis.entity.Employee;

/**
 * xml配置文件映射方式
 */
//@Mapper
public interface EmployeeMapper {

    public Employee getEmpById(Integer id);

    public void insertEmp(Employee employee);
}
