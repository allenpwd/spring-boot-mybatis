package pwd.allen.springbootmybatis.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Employee implements Serializable {

    private Integer id;
    private String lastName;
    private Integer gender;
    private String email;
    private Integer dId;
    private Date birth;

}
