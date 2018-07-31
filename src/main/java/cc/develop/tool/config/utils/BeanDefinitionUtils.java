package cc.develop.tool.config.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.util.xml.XmlValidationModeDetector;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author chengcheng
 */

public class BeanDefinitionUtils {



    /**
     * 加载Xml-从输入流读取
     *  @param ctx         spring 上下文
     * @param inputStream 输入流
     */
    @SuppressWarnings("unchecked")
    public static void loadXmlByInputStream(ApplicationContext ctx, InputStream inputStream) {
        XmlBeanDefinitionReader beanReader = new XmlBeanDefinitionReader(getBeanDefinitionRegistry(ctx));
        // 不设置会报错
        beanReader.setValidationMode(XmlValidationModeDetector.VALIDATION_XSD);
        try {
            Set<String> nameSet = new HashSet<>(Arrays.asList(beanReader.getBeanFactory().getBeanDefinitionNames()));

            beanReader.loadBeanDefinitions(new InputSource(inputStream));

            Set<String> nameSetAfterDubboExport =
                    new HashSet<>(Arrays.asList(beanReader.getBeanFactory().getBeanDefinitionNames()));

            nameSetAfterDubboExport.removeAll(nameSet);
            // 发起刷新事件`
            ApplicationEvent event = null;
            Object bean;
            for (String beanName : nameSetAfterDubboExport) {
                if (nameSet.contains(beanName)) {
                    continue;
                }
                bean = ctx.getBean(beanName);
                if (ApplicationListener.class.isAssignableFrom(bean.getClass())) {
                    if (event == null) {
                        event = new ContextRefreshedEvent(ctx);
                    }
                    ((ApplicationListener) bean).onApplicationEvent(event);
                }
            }
        } catch (BeansException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * 获取bean定义注册对象
     *
     * @param ctx spring 上下文
     * @return bean定义注册对象
     */
    private static DefaultListableBeanFactory getBeanDefinitionRegistry(ApplicationContext ctx) {
        return (DefaultListableBeanFactory) ((AbstractApplicationContext) ctx).getBeanFactory();
    }



}
