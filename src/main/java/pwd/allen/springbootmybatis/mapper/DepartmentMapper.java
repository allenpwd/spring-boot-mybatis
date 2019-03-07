package pwd.allen.springbootmybatis.mapper;

import org.apache.ibatis.annotations.*;
import pwd.allen.springbootmybatis.entity.Department;


/**
 注解映射方式：@Mapper或者@MapperScan将接口扫描装配到容器中
 */
//@Mapper
public interface DepartmentMapper {

    @Select("select * from department where id=#{id}")
    public Department getDeptById(Integer id);

    @Delete("delete from department where id=#{id}")
    public int deleteDeptById(Integer id);

    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into department(department_name) values(#{departmentName})")
    public int insertDept(Department department);

    @Update("update department set department_name=#{departmentName} where id=#{id}")
    public int updateDept(Department department);
}
