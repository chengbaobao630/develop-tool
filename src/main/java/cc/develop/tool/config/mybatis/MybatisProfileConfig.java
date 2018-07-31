package cc.develop.tool.config.mybatis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author chengcheng
 */
@Configuration
@ConditionalOnProperty(prefix = "cc",name = "use-mybatis-plus",havingValue = "true")
public class MybatisProfileConfig {

    @Bean
    @Profile("dev")
    public AbstractMybatisPlugins mockPlugins(){
        return new MockMybatisPlugins();
    }


    @Bean
    @Profile({"test","qa","pl","online"})
    public AbstractMybatisPlugins onlinePlugins(){
        return new OnlineMybatisPlugins();
    }


}
