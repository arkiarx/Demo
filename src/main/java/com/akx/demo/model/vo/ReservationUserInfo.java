package com.akx.demo.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationUserInfo {

    private Long resvId;

    private Long userId;

    private String realName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
