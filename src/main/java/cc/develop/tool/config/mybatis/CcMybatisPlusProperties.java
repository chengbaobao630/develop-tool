package cc.develop.tool.config.mybatis;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author chengcheng
 */
@Data
@ToString
@EqualsAndHashCode
@ConfigurationProperties(prefix = "cc.mybatis.plus")
public class CcMybatisPlusProperties {

    private String[] mapperLocations = {"classpath:mapper/*.xml"};

}
