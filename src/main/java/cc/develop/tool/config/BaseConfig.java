package cc.develop.tool.config;

import cc.develop.tool.config.cat.MyWccExtendConfigurer;
import cc.develop.tool.config.monitor.aop.BusinessMonitorAspect;
import cc.develop.tool.config.monitor.spring.RegisterCheckerPostProcessor;
import cc.develop.tool.config.web.CrossDomainFilter;
import com.dianping.cat.aop.CatTransactionAop;
import com.dianping.cat.servlet.CatRestFilter;
import com.weimob.soa.configcenter.WccPropertyPlaceholderConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.DispatcherType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author chengcheng
 */
public class BaseConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addRedirectViewController("/healthy.do","/healthy.json");
        registry.addViewController("healthy.do").setViewName("/healthy.json");
        super.addViewControllers(registry);
    }

    @Bean
    @Profile(value = {"test", "qa", "pl", "online"})
    public static WccPropertyPlaceholderConfigurer initDubboWccConfig() throws Exception {
        WccPropertyPlaceholderConfigurer configurer
                = new WccPropertyPlaceholderConfigurer();
        configurer.setIgnoreUnresolvablePlaceholders(true);
        configurer.setApplicationName("weimob.arch-common");
        List<String> locations = new ArrayList<String>() {
            {
                add("dubbo-common.properties");
            }
        };
        configurer.setLocations(locations);
        return configurer;
    }

    @Bean
    @Profile(value = {"test", "qa", "pl", "online"})
    public static WccPropertyPlaceholderConfigurer initCat() throws Exception {
        MyWccExtendConfigurer configurer
                = new MyWccExtendConfigurer();
        configurer.setApplicationName(System.getProperty("currentApplication"));
        List<String> locations = Collections.emptyList();
        configurer.setLocations(locations);
        configurer.setIgnoreUnresolvablePlaceholders(true);
        return configurer;
    }

    @Bean
    @Profile(value = {"test", "qa", "pl", "online"})
    public CatTransactionAop catTransactionAop() {
        return new CatTransactionAop();
    }


    @SuppressWarnings("unchecked")
    @Bean
    @ConditionalOnWebApplication
    @Profile(value = {"test", "qa", "pl", "online"})
    public FilterRegistrationBean catRestFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CatRestFilter());
        registration.addUrlPatterns("/*");
        registration.setDispatcherTypes(DispatcherType.FORWARD, DispatcherType.REQUEST);
        registration.setOrder(1);
        return registration;
    }



    @SuppressWarnings("unchecked")
    @Bean
    @ConditionalOnWebApplication
    public FilterRegistrationBean crossDomainFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CrossDomainFilter());
        registration.addUrlPatterns("/*");
        registration.setDispatcherTypes(DispatcherType.FORWARD, DispatcherType.REQUEST);
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public RegisterCheckerPostProcessor registerCheckerPostProcessor(){
        return new RegisterCheckerPostProcessor();
    }

    @Bean
    public BusinessMonitorAspect businessMonitorAspect(){
        return new BusinessMonitorAspect();
    }
}
