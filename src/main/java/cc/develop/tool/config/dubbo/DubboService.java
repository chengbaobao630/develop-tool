package cc.develop.tool.config.dubbo;

import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author chengcheng
 */
public class DubboService {


    private ApplicationConfig applicationConfig;
    private RegistryConfig registryConfig;
    private ProtocolConfig protocolConfig;
    private DubboInitProperties dubboProperties;

    public DubboService(ApplicationConfig applicationConfig, RegistryConfig registryConfig, ProtocolConfig protocolConfig, DubboInitProperties dubboProperties) {
        this.applicationConfig = applicationConfig;
        this.registryConfig = registryConfig;
        this.protocolConfig = protocolConfig;
        this.dubboProperties = dubboProperties;
    }

    public DubboService(ApplicationConfig applicationConfig, RegistryConfig registryConfig, DubboInitProperties dubboProperties) {
        this.applicationConfig = applicationConfig;
        this.registryConfig = registryConfig;
        this.dubboProperties = dubboProperties;
    }

    /**
     * 将服务暴露出去
     * @param service
     * @param serviceImpl
     * @param <T>
     */
    public <T> void export(Class<?> service, T serviceImpl) {
        ServiceConfig<T> serviceConfig = new ServiceConfig<T>();

        serviceConfig.setInterface(service);
        serviceConfig.setRef(serviceImpl);

        serviceConfig.setApplication(this.applicationConfig);
        serviceConfig.setRegistry(this.registryConfig);
        serviceConfig.setProtocol(this.protocolConfig);
        serviceConfig.setTimeout(this.dubboProperties.getTimeOut());
        serviceConfig.setRetries(this.dubboProperties.getRetries());

        serviceConfig.export();
    }

}
