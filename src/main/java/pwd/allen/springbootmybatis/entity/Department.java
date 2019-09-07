package pwd.allen.springbootmybatis.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Department implements Serializable {

    private Integer id;
    private String departmentName;

}
