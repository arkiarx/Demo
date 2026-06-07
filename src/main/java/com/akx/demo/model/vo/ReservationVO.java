package com.akx.demo.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationVO {

    /**
     * 預約id
     */
    private Long rsvnId;

    /**
     * 預約編號
     */
    private String rsvnCode;

    /**
     *開始時間
     */
    private LocalDateTime startTime;

    /**
     *結束時間
     */
    private LocalDateTime endTime;

    /**
     *房間id
     */
    private Long roomId;

    /**
     *房間編號
     */
    private String roomName;

    /**
     *容量
     */
    private Integer capacity;

    /**
     *預約用戶id
     */
    private Long userId;

    /**
     *用戶編號
     */
    private String userCode;

    /**
     *真實姓名
     */
    private String realName;

    /**
     *電話號碼
     */
    private String mobile;

    /**
     *部門id
     */
    private Long deptId;

    /**
     * 部門名稱
     */
    private String deptName;

    /**
     *會議主旨
     */
    private String title;

    /**
     * 使用人數
     */
    private Integer userCount;
}
