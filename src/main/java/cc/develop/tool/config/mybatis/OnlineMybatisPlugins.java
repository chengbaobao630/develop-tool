package cc.develop.tool.config.mybatis;

import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Profile;

/**
 * @author chengcheng
 */

@Profile({"test","qa","pl","online"})
public class OnlineMybatisPlugins extends AbstractMybatisPlugins {

    @Override
    public Interceptor[] getInterceptors() {
        return new Interceptor[]{paginationInterceptor(),catMybatisPlugins()};
    }

}
