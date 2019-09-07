package pwd.allen.springbootmybatis.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import pwd.allen.springbootmybatis.entity.Employee;

/**
 * xml配置文件映射方式
 * 如果xml和注解都用，Mapped Statements collection不能有一样的，否则会报：Mapped Statements collection already contains value for xxx
 */
//@Mapper
public interface EmployeeMapper {

//    @Select("SELECT * FROM employee where id = #{id}")
    public Employee getEmpById(Integer id);

    public void insertEmp(Employee employee);

    @Delete("delete from employee where id = #{id}")
    public int deleteEmp(Employee employee);

    public Employee getEmptByLastName(String lastName);
}
