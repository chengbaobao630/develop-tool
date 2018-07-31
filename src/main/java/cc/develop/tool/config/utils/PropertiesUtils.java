package cc.develop.tool.config.utils;

import lombok.Getter;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/**
 * PropertiesUtils 请输入标题
 *
 * @Author kobePropertiesUtils
 * @CreateTime 16-7-24 下午11:43.
 */
public class PropertiesUtils implements EmbeddedValueResolverAware {
    @Getter
    private static StringValueResolver stringValueResolver;

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        stringValueResolver = resolver;
    }

    public static String getPropertyValue(String name) {
        return stringValueResolver.resolveStringValue(name);
    }
}