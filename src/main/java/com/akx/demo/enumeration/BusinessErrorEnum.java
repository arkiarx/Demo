package com.akx.demo.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BusinessErrorEnum {

    SYSTEM_ERROR(9999, "系統異常!"),

    PARAM_ERROR(2000, "參數錯誤!"),

    DB_ERROR(3000, "數據操作失敗！"),

    ;

    /**
     * 狀態code
     */
    private int code;

    /**
     * 異常信息
     */
    private String errMsg;


}
