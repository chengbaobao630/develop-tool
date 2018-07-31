package cc.develop.tool.config.mybatis.plus;


import cc.develop.tool.config.mybatis.AbstractMybatisPlugins;
import cc.develop.tool.config.mybatis.CcMybatisPlusProperties;
import cc.develop.tool.config.mybatis.MybatisProfileConfig;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.mapper.LogicSqlInjector;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author chengcheng
 */
@AutoConfigureAfter({DataSourceAutoConfiguration.class,MybatisProfileConfig.class})
@EnableConfigurationProperties(CcMybatisPlusProperties.class)
@ConditionalOnProperty(prefix = "cc",name = "use-mybatis-plus",havingValue = "true")
public class MybatisPlusConfig {

    private  final AbstractMybatisPlugins abstractMybatisPlugins;

    private final CcMybatisPlusProperties ccMybatisPlusProperties;

    public MybatisPlusConfig(AbstractMybatisPlugins abstractMybatisPlugins,
                             CcMybatisPlusProperties ccMybatisPlusProperties) {
        this.abstractMybatisPlugins = abstractMybatisPlugins;
        this.ccMybatisPlusProperties = ccMybatisPlusProperties;
    }

    @Bean
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean(DataSource dataSource){
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean
                = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSource);
        mybatisSqlSessionFactoryBean.setGlobalConfig(globalConfiguration());
        mybatisSqlSessionFactoryBean.setMapperLocations(resolveMapperLocations());
        mybatisSqlSessionFactoryBean.setPlugins(abstractMybatisPlugins.getInterceptors());
        return mybatisSqlSessionFactoryBean;
    }


    private GlobalConfiguration globalConfiguration() {
        GlobalConfiguration conf = new GlobalConfiguration(new LogicSqlInjector());
        conf.setLogicDeleteValue("1");
        conf.setLogicNotDeleteValue("0");
        conf.setIdType(2);
        conf.setDbColumnUnderline(true);
        conf.setRefresh(true);
        return conf;
    }


    public Resource[] resolveMapperLocations() {
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        List<Resource> resources = new ArrayList<Resource>();
        if (ccMybatisPlusProperties.getMapperLocations() != null) {
            for (String mapperLocation : ccMybatisPlusProperties.getMapperLocations()) {
                try {
                    Resource[] mappers = resourceResolver.getResources(mapperLocation);
                    resources.addAll(Arrays.asList(mappers));
                } catch (IOException e) {
                    // ignore
                }
            }
        }
        return resources.toArray(new Resource[resources.size()]);
    }

}
