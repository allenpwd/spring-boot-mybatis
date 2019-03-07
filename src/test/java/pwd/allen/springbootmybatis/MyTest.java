package pwd.allen.springbootmybatis;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author pwd
 * @create 2019-02-26 11:37
 **/
public class MyTest {

    private Logger log = LoggerFactory.getLogger(MyTest.class);

    @Test
    public void test() {
        log.info("测试{}({}){}", "abc", "efg", "hij");
    }
}
