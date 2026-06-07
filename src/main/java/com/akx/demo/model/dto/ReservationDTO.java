package com.akx.demo.model.dto;

import com.akx.demo.model.vo.ReservationVO;
import lombok.Data;

@Data
public class ReservationDTO extends ReservationVO {

    private String status;
}
