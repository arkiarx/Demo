package com.akx.demo.model.dto;

import lombok.Data;

@Data
public class RejectApplyDTO {

    private Long reservationId;

    private String rejectReason;

    private String remark;
}
