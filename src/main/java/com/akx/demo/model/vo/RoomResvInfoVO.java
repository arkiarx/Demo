package com.akx.demo.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class RoomResvInfoVO {

    private String roomName;

    private List<ReservationUserInfo> resvUserInfoList;
}
