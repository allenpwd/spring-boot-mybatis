package pwd.allen.springbootmybatis.config;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;

@MapperScan("pwd.allen.springbootmybatis.mapper")
@org.springframework.context.annotation.Configuration
public class MyBatisConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer(){
        /**
         * 自定义mybatis配置：实现ConfigurationCustomizer接口的customize(Configuration configuration)方法
         */
        return new ConfigurationCustomizer(){

            @Override
            public void customize(Configuration configuration) {
                //设置自动识别驼峰命名
                //也可以在配置文件里指定mybatis.configuration.map-underscore-to-camel-case=true
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
    }
}
