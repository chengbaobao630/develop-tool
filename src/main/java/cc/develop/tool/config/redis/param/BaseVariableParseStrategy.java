package cc.develop.tool.config.redis.param;

import cc.develop.tool.config.redis.RedisKey;
import org.apache.commons.lang3.StringUtils;

/**
 * @author chengcheng
 */
public class BaseVariableParseStrategy  implements VariableParseStrategy{

    @Override
    public String resolve(String key, String value,String unResolvedString) {
        return unResolvedString.replaceFirst("{"+key+"}",value);
    }

    @Override
    public String smartResolve(String value, String unResolvedString) {
        return unResolvedString.replaceAll("\\{\\w*}",value);
    }

    @Override
    public String smartResolve(RedisKey redisKey) {
        return smartResolve(redisKey.getValue(),redisKey.getUnResolvedKey());
    }


    @Override
    public String resolve(RedisKey redisKey) {
        if (StringUtils.isBlank(redisKey.getKey())){
            return  smartResolve(redisKey);
        }
        return resolve(redisKey.getKey(),redisKey.getValue(),redisKey.getUnResolvedKey());
    }


    public static void main(String[] args) {
        BaseVariableParseStrategy baseVariableParseStrategy
                = new BaseVariableParseStrategy();
        System.out.println(baseVariableParseStrategy.smartResolve("null","hahaa-ad"));
    }

}
