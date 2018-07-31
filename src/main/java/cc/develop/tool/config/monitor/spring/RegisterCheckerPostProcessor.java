package cc.develop.tool.config.monitor.spring;

import cc.develop.tool.config.monitor.Checker;
import cc.develop.tool.config.monitor.annonation.CheckAll;
import cc.develop.tool.config.monitor.annonation.CheckFor;
import cc.develop.tool.config.monitor.container.CheckerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author chengcheng
 */
public class RegisterCheckerPostProcessor implements BeanPostProcessor, ApplicationContextAware {


    @Override
    public Object postProcessBeforeInitialization(Object bean, String name) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String name) throws BeansException {
        if (bean.getClass().isAnnotationPresent(CheckFor.class)
                && bean instanceof Checker){
            final CheckFor checkFor = bean.getClass().getAnnotation(CheckFor.class);
            CheckerFactory.getInstance().add(checkFor.value(), (Checker) bean);
        }
        if (bean.getClass().isAnnotationPresent(CheckAll.class)
                && bean instanceof Checker){
            CheckerFactory.getInstance().generalAdd((Checker) bean);
        }
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
