package cc.develop.tool.config.cat;

import com.weimob.soa.configcenter.WccPropertyPlaceholderConfigurer;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * @author chengcheng
 */
public class MyWccExtendConfigurer extends WccPropertyPlaceholderConfigurer{

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        try {
            Field field
                    = WccPropertyPlaceholderConfigurer.class.getDeclaredField("globalProperties");
            field.setAccessible(true);
            ResourceLoader loader = new ClassPathXmlApplicationContext();
            final Resource resource = loader.getResource("/wcc/dubbo.properties");
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            String dubboApplicationName = "dubbo.application.name";
            if ("{{current.application}}".equals(properties.getProperty(dubboApplicationName))) {
                String systemApplicationName = "currentApplication";
                properties.setProperty(dubboApplicationName, System.getProperty(systemApplicationName));
            }
            field.set(this, properties);
        }catch (Exception e){
            System.err.print("请创建/wcc/dubbo.properties文件");
        }
    }

}
