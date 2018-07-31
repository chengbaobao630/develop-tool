package cc.develop.tool.config.utils;

import java.io.Serializable;
import java.util.List;

public class ParseFileResult<T,E> implements Serializable{

    private static final long serialVersionUID = -7860699693689303782L;
    private List<T> success;

    private List<E>  error;

    public List<T> getSuccess() {
        return success;
    }

    public void setSuccess(List<T> success) {
        this.success = success;
    }

    public List<E> getError() {
        return error;
    }

    public void setError(List<E> error) {
        this.error = error;
    }
}
