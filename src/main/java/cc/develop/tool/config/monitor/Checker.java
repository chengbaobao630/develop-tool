package cc.develop.tool.config.monitor;

import cc.develop.tool.config.exception.AbstractException;

/**
 * @author chengcheng
 */
public interface Checker<T> {

    boolean check(T t);

    AbstractException error();

    void success(T t);
}
