package cc.develop.tool.config.dubbo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.UUID;

/**
 * @author chengcheng
 */
@Data
@ToString
@EqualsAndHashCode
@ConfigurationProperties(prefix = "cc.dubbo")
public class DubboInitProperties {

    /**
     *   name: jingxuan.cart-adapter
     check: false
     register: true
     protocol: zookeeper
     retries: 0
     timeout: 60000
     protocolName: dubbo
     payload: 10485760
     owner: cheng.cheng
     */

    private String name = "dubbo.unknown-"+ UUID.randomUUID().toString();
    private String address ;
    private boolean check = false;
    private boolean register = true;
    private String protocol = "zookeeper";
    private Integer retries = 0;
    private Integer timeOut = 500;
    private String protocolName = "dubbo";
    private Integer payload = 10485760;
    private String owner = "unknown";

}
