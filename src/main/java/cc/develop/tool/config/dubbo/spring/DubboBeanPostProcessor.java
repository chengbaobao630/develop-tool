package cc.develop.tool.config.dubbo.spring;

import cc.develop.tool.config.dubbo.DubboService;
import cc.develop.tool.config.dubbo.annonation.DubboExporter;
import cc.develop.tool.config.utils.BeanDefinitionUtils;
import cc.develop.tool.config.utils.StreamUtils;
import com.weimob.soa.common.proxy.service.ProxyInvokeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author chengcheng
 */

public class DubboBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    private DubboService dubboService;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(DubboExporter.class)) {
            final DubboExporter exporter = bean.getClass().getAnnotation(DubboExporter.class);
            dubboService.export(exporter.interfaceClass(), bean);
        }
        return bean;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        dubboService = applicationContext.getBean(DubboService.class);
    }


}
