package pwd.allen.springbootmybatis.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pwd.allen.springbootmybatis.entity.Department;
import pwd.allen.springbootmybatis.service.DeptService;
import pwd.allen.springbootmybatis.service.EmpService;

import javax.sql.DataSource;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping("dept")
public class DeptController {

    @Autowired
    DeptService deptService;

    @Autowired
    EmpService empService;

    @Autowired
    DataSource dataSource;

    @Autowired
    CacheManager cacheManager;


    @GetMapping("/{id}")
    public Department getDepartment(@PathVariable("id") Integer id){
        return deptService.getDept(id);
    }

    @GetMapping("/")
    public Object insertDept(Department department){
        if (StringUtils.isEmpty(department.getDepartmentName())) {
            return "参数不全";
        }
        deptService.insertDept(department);
        return department;
    }

    @GetMapping("/update")
    public Object updateDept(Department department){
        if (StringUtils.isEmpty(department.getDepartmentName())) {
            return "参数不全";
        }
        deptService.updateDept(department);
        return department;
    }

    @GetMapping("/del/{id}")
    public Object deleteDept(@PathVariable("id") Integer id) {
        deptService.deleteEmp(id);
        return "success";
    }

    @GetMapping("/status/{type}")
    public Object dataSource(@PathVariable("type") String type) {
        if ("dataSource".equals(type)) {
            System.out.println(dataSource);
            return dataSource;
        }
        return "测试";
    }


}
