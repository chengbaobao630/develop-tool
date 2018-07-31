package cc.develop.tool.config.redis;

import cc.develop.tool.config.redis.param.VariableParseStrategy;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author chengcheng
 */
@Data
@ToString
@EqualsAndHashCode
public class RedisKeyFactory {

    private String prefix;

    private VariableParseStrategy variableParseStrategy;

    public String get(RedisKey redisKey){
        return prefix + variableParseStrategy.resolve(redisKey);
    }



}
