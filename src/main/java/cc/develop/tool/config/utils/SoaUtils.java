package cc.develop.tool.config.utils;

import cc.develop.tool.config.bo.BaseErrorInfo;
import cc.develop.tool.config.bo.SoaConstant;
import com.weimob.soa.common.response.SoaResponse;

public class SoaUtils {

    public static <T,E extends BaseErrorInfo> SoaResponse<T,E> successReturn(T t){
        SoaResponse<T,E> soaResponse = new SoaResponse<>();
        soaResponse.setResponseVo(t);
        soaResponse.setReturnCode(SoaConstant.DEFAULT_SUCCESS);
        return soaResponse;
    }

    public static <T> SoaResponse<T,String> successStringReturn(T t){
        SoaResponse<T,String> soaResponse = new SoaResponse<>();
        soaResponse.setResponseVo(t);
        soaResponse.setReturnCode(SoaConstant.DEFAULT_SUCCESS);
        return soaResponse;
    }



    public static <T,E extends BaseErrorInfo> SoaResponse<T, E> errorReturn(E errorInfo){
        SoaResponse<T,E> soaResponse = new SoaResponse<>();
        soaResponse.setErrT(errorInfo);
        soaResponse.setReturnCode(errorInfo.getCode().toString());
        soaResponse.setReturnMsg(errorInfo.getMsg());
        return soaResponse;
    }



    public static <T,E extends BaseErrorInfo> SoaResponse<T,String> errorStringReturn(E errorInfo){
        SoaResponse<T,String> soaResponse = new SoaResponse<>();
        soaResponse.setErrT(errorInfo.getMsg());
        soaResponse.setReturnCode(errorInfo.getCode().toString());
        soaResponse.setReturnMsg(errorInfo.getMsg());
        return soaResponse;
    }



}
