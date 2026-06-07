package com.akx.demo.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class StatusRsvnInfoVO {

    private String statusCode;

    private String statusDesc;

    private List<ReservationVO> rsvnList;
}
