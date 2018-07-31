package cc.develop.tool.config.redis.param;

import cc.develop.tool.config.redis.RedisKey; /**
 * @author chengcheng
 */
public interface VariableParseStrategy {

    String resolve(String key, String value,String unResolvedString);

    String smartResolve( String value,String unResolvedString);

    String smartResolve( RedisKey redisKey);

    String resolve(RedisKey redisKey);
}
