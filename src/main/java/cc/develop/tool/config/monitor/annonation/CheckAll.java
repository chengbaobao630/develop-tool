package cc.develop.tool.config.monitor.annonation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author chengcheng
 */
@Inherited //cglib代理问题
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface CheckAll {
}
