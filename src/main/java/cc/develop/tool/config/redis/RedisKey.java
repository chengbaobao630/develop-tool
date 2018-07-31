package cc.develop.tool.config.redis;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author chengcheng
 */
@Data
@ToString
@EqualsAndHashCode
public abstract class RedisKey {

    private String key;

    private String value;

    public RedisKey(String key,String value) {
        this.key = key;
        this.value = value;
    }

    public RedisKey(String value) {
        this.value = value;
    }

    public abstract String getUnResolvedKey();

}
