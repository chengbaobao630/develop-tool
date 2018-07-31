package cc.develop.tool.config.monitor.annonation;

import java.lang.annotation.*;

/**
 * @author chengcheng
 */
@Inherited //cglib代理问题
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BusinessMonitored {
}
