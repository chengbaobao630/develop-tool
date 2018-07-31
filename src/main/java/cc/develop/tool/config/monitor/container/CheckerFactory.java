package cc.develop.tool.config.monitor.container;

import cc.develop.tool.config.monitor.Checker;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Assert;

import java.util.*;

import static java.util.Collections.addAll;

/**
 * @author chengcheng
 */
public final class CheckerFactory {


    private static CheckerFactory checkerFactory =
            new CheckerFactory();

    private CheckerFactory() {}

    public static  CheckerFactory getInstance(){
        return checkerFactory;
    }

    private final  Map<Class<?>, List<Checker>> checkerMap
            = Maps.newConcurrentMap();

    public  List<Checker> get(Class<?> clazz) {
        final ArrayList<Checker> checkers = Lists.newArrayList(generalChecker);
        final List<Checker> checkerList = checkerMap.get(clazz);
        if (Objects.nonNull(checkerList)) {
            checkers.addAll(checkerList);
        }
        return checkers;
    }

    private final List<Checker> generalChecker = Lists.newArrayList();

    public  void  add(Class<?> clazz, Checker checker) {
        Assert.assertTrue("Checker can not be null",!Objects.isNull(checker));
        List<Checker> checkerList = checkerMap.get(clazz);
        if (!Objects.isNull(checkerList)){
            checkerList.add(checker);
        }else {
            checkerList = Lists.newArrayList(checker);
            checkerMap.put(clazz,checkerList);
        }
    }



    public  void  add(Class<?>[] clazz, Checker checker) {
        for (Class<?> aClass : clazz) {
            add(aClass,checker);
        }
    }


    public  void  generalAdd(Checker checker) {
        generalChecker.add(checker);
    }



}
