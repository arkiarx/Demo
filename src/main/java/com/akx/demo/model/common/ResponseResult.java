package com.akx.demo.model.common;

import com.akx.demo.enumeration.BusinessErrorEnum;
import com.akx.demo.excption.BusinessException;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseResult<T> implements Serializable {

    private static final int SUCCESS_CODE = 1000;
    private static final String SUCCESS_MSG = "Success";
    private static final int UNKNOWN_ERROR_CODE = 7777;


    /**
     * 狀態code 1000 是正常，其他都不正常
     */
    private int code;

    /**
     * 數據
     */
    private T data;

    /**
     * 異常信息
     */
    private String msg;

    public ResponseResult() {
    }

    public static <T> ResponseResult<T> success(T data){
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(SUCCESS_CODE);
        result.setData(data);
        result.setMsg(SUCCESS_MSG);
        return result;
    }

    public static <T> ResponseResult<T> success(){
        return success(null);
    }

    public static <T> ResponseResult<T> error(int code, String errMsg){
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(code);
        result.setData(null);
        result.setMsg(errMsg);
        return result;
    }

    public static <T> ResponseResult<T> error(BusinessException bex){
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(bex.getErrorCode());
        result.setData(null);
        result.setMsg(bex.getErrorMsg());
        return result;
    }

    public static <T> ResponseResult<T> systemError(){
        ResponseResult<T> result = new ResponseResult<>();
        result.setCode(BusinessErrorEnum.SYSTEM_ERROR.getCode());
        result.setMsg(BusinessErrorEnum.SYSTEM_ERROR.getErrMsg());
        return result;
    }

    public static <T> ResponseResult<T> error(String errMsg){
        return error(UNKNOWN_ERROR_CODE, errMsg);
    }
}
