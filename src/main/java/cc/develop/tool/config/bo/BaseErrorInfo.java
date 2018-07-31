package cc.develop.tool.config.bo;

/**
 * @author chengcheng
 */
public interface BaseErrorInfo {

    default BaseErrorInfo instance(){
        return  new BaseErrorInfo() {
            @Override
            public Integer getCode() {
                return 0;
            }

            @Override
            public String getMsg() {
                return "success";
            }
        };
    }

    Integer getCode();

    String getMsg();
}
