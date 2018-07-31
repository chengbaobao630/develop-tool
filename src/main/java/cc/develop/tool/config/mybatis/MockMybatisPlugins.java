package cc.develop.tool.config.mybatis;

import org.apache.ibatis.plugin.Interceptor;

/**
 * @author chengcheng
 */
public class MockMybatisPlugins extends AbstractMybatisPlugins {


    @Override
    public Interceptor[] getInterceptors() {

        return new Interceptor[]{paginationInterceptor(),performanceInterceptor()};
    }


}
