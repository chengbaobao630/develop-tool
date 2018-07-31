package cc.develop.tool.config.exception;

import cc.develop.tool.config.bo.BaseErrorInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author chengcheng
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public abstract class  AbstractException extends     RuntimeException{

    private Integer code;

    private String msg;

    private BaseErrorInfo errorInfo;

    public <E extends BaseErrorInfo>   AbstractException(E e){
        super(e.getMsg());
        this.code = e.getCode();
        this.msg = e.getMsg();
        this.errorInfo = e;

    }

    public AbstractException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

}
