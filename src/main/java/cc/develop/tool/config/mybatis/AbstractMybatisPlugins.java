package cc.develop.tool.config.mybatis;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import com.weimob.cat.mybatis3.CatMybatisPlugins;
import org.apache.ibatis.plugin.Interceptor;

/**
 * @author chengcheng
 */
public abstract class AbstractMybatisPlugins {

    public abstract Interceptor[] getInterceptors();

    /**
     * mybatis-plus分页插件<br>
     * 文档：http://mp.baomidou.com<br>
     */
     PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

     CatMybatisPlugins catMybatisPlugins(){
        return new CatMybatisPlugins();
    }


    /**
     *  SQL 执行性能分析，开发环境使用
     *  线上不推荐。 maxTime 指的是 sql 最大执行时长
     */
     PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        performanceInterceptor.setFormat(true);
        performanceInterceptor.setMaxTime(500);
        return performanceInterceptor;
    }

}
