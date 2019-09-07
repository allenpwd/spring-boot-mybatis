package pwd.allen.springbootmybatis.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwd.allen.springbootmybatis.entity.Department;
import pwd.allen.springbootmybatis.entity.Employee;
import pwd.allen.springbootmybatis.service.DeptService;
import pwd.allen.springbootmybatis.service.EmpService;

import javax.sql.DataSource;

@RequestMapping("emp")
@RestController
public class EmpController {

    @Autowired
    EmpService empService;


    @GetMapping("/{id}")
    public Employee getEmp(@PathVariable("id") Integer id){
        Employee empById = empService.getEmpById(id);
        return empById;
    }

    @GetMapping("/lastName/{lastName}")
    public Employee getEmpByLastName(@PathVariable("lastName") String lastName){
        Employee employee = empService.getEmptByLastName(lastName);
        return employee;
    }

}
