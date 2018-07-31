package cc.develop.tool.config.dubbo;

import cc.develop.tool.config.dubbo.spring.DubboBeanPostProcessor;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.weimob.soa.common.proxy.service.ProxyInvokeService;
import com.weimob.soa.common.proxy.service.impl.ProxyInvokeServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author chengcheng
 */
@Configuration
@ConditionalOnProperty(prefix = "cc", name = "use-dubbo", havingValue = "true")
@EnableConfigurationProperties(DubboInitProperties.class)
public class DubboInitializer {

    private final DubboInitProperties dubboProperties;

    @Value("${dubbo.registry.address:}")
    private String dubboRegistryAddress;

    public DubboInitializer(DubboInitProperties dubboInitProperties) {
        this.dubboProperties = dubboInitProperties;
    }

    @Bean
    public ReferenceConfigCache referenceConfigCache() {
        return ReferenceConfigCache.getCache();
    }

    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig =
                new ApplicationConfig(this.dubboProperties.getName());
        applicationConfig.setOwner(dubboProperties.getOwner());
        return applicationConfig;
    }

    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig(StringUtils.isBlank(this.dubboProperties.getAddress())?
                dubboRegistryAddress : this.dubboProperties.getAddress());
        registryConfig.setCheck(this.dubboProperties.isCheck());
        registryConfig.setRegister(this.dubboProperties.isRegister());
        registryConfig.setProtocol(this.dubboProperties.getProtocol());

        return registryConfig;
    }

    @Bean
    public ProtocolConfig protocolConfig() {
        if (null == this.dubboProperties.getProtocolName() || null == this.dubboProperties.getPayload()) {
            return null;
        }
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setPort(-1);
        protocolConfig.setPayload(this.dubboProperties.getPayload());
        return protocolConfig;
    }


    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE + 1)
    public DubboBeanPostProcessor dubboBeanPostProcessor(){
        return new DubboBeanPostProcessor();
    }

    @Bean
    public ProxyInvokeService proxyInvokeService(){
        return  new ProxyInvokeServiceImpl();
    }


    @Bean
    public DubboService dubboService(ApplicationConfig applicationConfig,
                                     RegistryConfig registryConfig,
                                     ProtocolConfig protocolConfig) {
        if (null == protocolConfig) {
            return new DubboService(applicationConfig, registryConfig, this.dubboProperties);
        }
        return new DubboService(applicationConfig, registryConfig, protocolConfig, this.dubboProperties);
    }
}
