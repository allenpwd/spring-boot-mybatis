package pwd.allen.springbootmybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import pwd.allen.springbootmybatis.entity.Employee;
import pwd.allen.springbootmybatis.mapper.EmployeeMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMybatisApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Test
    public void contextLoads() throws SQLException {
        Connection connection = dataSource.getConnection();
    }

    @Test
    public void redis01() {
        stringRedisTemplate.opsForValue().set("msg", "pwd", Duration.ofSeconds(10));
        System.out.println(stringRedisTemplate.opsForValue().get("msg"));
        stringRedisTemplate.opsForValue().append("msg", " hello");
        System.out.println(stringRedisTemplate.opsForValue().get("msg"));
    }

    @Test
    public void redis02() {
        Employee emp = employeeMapper.getEmpById(1);
        redisTemplate.opsForValue().set("emp-01", emp);

        //如果redisTemplate没有配置ObjectMapper，获取json格式对象时返回的是LinkedHashMap；加上这个就能正常返回对象实例
        Object obj = redisTemplate.opsForValue().get("emp-01");
        System.out.println(obj);
    }

}

