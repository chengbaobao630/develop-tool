package cc.develop.tool.config.monitor.aop;

import cc.develop.tool.config.monitor.BaseCheckMonitor;
import cc.develop.tool.config.monitor.annonation.Monitored;
import cc.develop.tool.config.monitor.container.CheckerFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;

/**
 * @author chengcheng
 */
@Aspect
public class BusinessMonitorAspect {

    @Pointcut(value = "@annotation(cc.develop.tool.config.monitor.annonation.BusinessMonitored)")
    private void businessMonitored() {}


    @Before(value = "businessMonitored()")
    @SuppressWarnings("unchecked")
    public void permissionCheck(JoinPoint joinPoint) {
        final Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        final Object[] args = joinPoint.getArgs();
        final Parameter[] parameters = methodSignature.getMethod().getParameters();
        for (int a = 0 ; a < parameters.length ; a++ ){
            final Parameter parameter = parameters[a];
            if (parameter.isAnnotationPresent(Monitored.class)){
                final Object arg = args[a];
                final Class<?> type = parameter.getType();
                BaseCheckMonitor checkMonitor
                        = BaseCheckMonitor.getInstance(CheckerFactory.getInstance().get(type));
                checkMonitor.doCheck(arg);
            }
        }
    }
    
}
