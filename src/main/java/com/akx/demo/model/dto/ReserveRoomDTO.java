package com.akx.demo.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReserveRoomDTO {

    /**
     * 房間id
     */
    private Long roomId;

    /**
     * 預約用戶id
     */
    private Long userId;

    /**
     * 會議主題
     */
    private String title;

    /**
     * 餐會人數
     */
    private Integer userCount;

    /**
     * 開始時間
     */
    private LocalDateTime startTime;

    /**
     * 結束時間
     */
    private LocalDateTime endTime;
}
