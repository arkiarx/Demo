package com.akx.demo.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReserveStatusEnum {

    PROCESSING("processing", "申請中"),

    REJECTED("rejected", "被拒絕"),

    APPROVED("approved", "通過")

    ;

    /**
     * 數據庫中的狀態編碼
     */
    private String code;

    /**
     * 狀態釋義
     */
    private String desc;



}
