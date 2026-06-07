package com.akx.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("mr_reservation")
public class Reservation {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 預約編碼，暫無用
     */
    private String code;

    /**
     * 開始時間
     */
    private LocalDateTime startTime;

    /**
     * 結束時間
     */
    private LocalDateTime endTime;

    /**
     * 預約會議室id
     */
    private Long roomId;

    /**
     * 使用人數
     */
    private Integer userCount;

    /**
     * 預約狀態：（processing申請中；rejected，拒絕；approved：通過）
     */
    private String status;

    /**
     * 會議主題
     */
    private String title;

    /**
     * 預約人id
     */
    private Long userId;

    /**
     * 會議部門
     */
    private Long deptId;

    /**
     * 預約場戶
     */
    private String orderFactory;

    /**
     * 拒絕原因
     */
    private String rejectReason;

    /**
     * 備注
     */
    private String remark;

    private Long creatorId;

    private LocalDateTime createTime;

    private Long updaterId;

    private LocalDateTime updateTime;
}
