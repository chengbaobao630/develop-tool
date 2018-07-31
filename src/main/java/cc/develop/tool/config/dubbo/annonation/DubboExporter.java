package cc.develop.tool.config.dubbo.annonation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author chengcheng
 */
@Inherited //cglib代理问题
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface DubboExporter{

    String value() default "";

    Class interfaceClass();
}
