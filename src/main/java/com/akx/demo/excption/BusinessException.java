package com.akx.demo.excption;

import com.akx.demo.enumeration.BusinessErrorEnum;
import lombok.Data;

@Data
public class BusinessException extends RuntimeException{

    private int errorCode;

    private String errorMsg;

    public BusinessException(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BusinessException(BusinessErrorEnum errorEnum){
        this.errorCode = errorEnum.getCode();
        this.errorMsg = errorEnum.getErrMsg();
    }
}
