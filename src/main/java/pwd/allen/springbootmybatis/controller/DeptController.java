package pwd.allen.springbootmybatis.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pwd.allen.springbootmybatis.entity.Department;
import pwd.allen.springbootmybatis.entity.Employee;
import pwd.allen.springbootmybatis.mapper.EmployeeMapper;
import pwd.allen.springbootmybatis.service.DeptService;
import pwd.allen.springbootmybatis.service.EmpService;

import javax.sql.DataSource;

@RestController
public class DeptController {

    @Autowired
    DeptService deptService;

    @Autowired
    EmpService empService;

    @Autowired
    DataSource dataSource;


    @GetMapping("/dept/{id}")
    public Department getDepartment(@PathVariable("id") Integer id){
        return deptService.getDept(id);
    }

    @GetMapping("/dept")
    public Object insertDept(Department department){
        if (StringUtils.isEmpty(department.getDepartmentName())) {
            return "参数不全";
        }
        deptService.insertDept(department);
        return department;
    }

    @GetMapping("/emp/{id}")
    public Employee getEmp(@PathVariable("id") Integer id){
        System.out.println(dataSource);
        return empService.getEmpById(id);
    }


}
