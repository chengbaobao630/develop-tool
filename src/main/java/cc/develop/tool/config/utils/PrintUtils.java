package cc.develop.tool.config.utils;

/**
 * @author chengcheng
 */
public class PrintUtils {

    public static void  debugPrint(Object content){
        System.out.println("------------------DEBUG------------------");
        System.out.println(content);
        System.out.println("-------------------END-------------------");
    }

    public static void  errorPrint(Object content){
        System.err.println("------------------ERROR------------------");
        System.err.println(content);
        System.err.println("-------------------END-------------------");
    }

}
