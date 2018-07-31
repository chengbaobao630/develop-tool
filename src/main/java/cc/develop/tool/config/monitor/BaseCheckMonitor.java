package cc.develop.tool.config.monitor;

import com.google.common.collect.Lists;
import lombok.Setter;

import java.util.List;

/**
 * @author chengcheng
 */
public class BaseCheckMonitor<T> {

    @Setter
    private List<Checker>  checkerList
            = Lists.newArrayList();

    public void addChecher(Checker<T> checker){
        this.checkerList.add(checker);
    }

    public void doCheck(T t){
        checkerList.forEach(checker -> {
            if (checker.check(t)) {
                checker.success(t);
            } else {
                throw checker.error();
            }
        });
    }


    public static <T> BaseCheckMonitor getInstance(){
        return new BaseCheckMonitor<T>();
    }


    public static <T> BaseCheckMonitor getInstance(List<Checker>  checkerList){
        final BaseCheckMonitor<T> tBaseCheckMonitor = new BaseCheckMonitor<>();
        tBaseCheckMonitor.setCheckerList(checkerList);
        return tBaseCheckMonitor;
    }


}
