package com.akx.demo.service;

import com.akx.demo.entity.Reservation;
import com.akx.demo.model.dto.RejectApplyDTO;
import com.akx.demo.model.dto.ReserveRoomDTO;
import com.akx.demo.model.vo.RoomResvInfoVO;
import com.akx.demo.model.vo.StatusRsvnInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService extends IService<Reservation> {

    /**
     * 預約會議室，預約申請，——》 processing
     * @param dto
     * @return
     */
    Reservation reserveRoom(ReserveRoomDTO dto);


    /**
     * 退回申請 processing -》 rejected
     * @param dto
     * @return
     */
    Boolean rejectApply(RejectApplyDTO dto);

    /**
     * 當日會議室預約人員列表
     * @param date
     * @return
     */
    List<RoomResvInfoVO> getResvUserInfoByDay(LocalDate date);


    /**
     * 輸入當月第一天的日期，查詢當月分狀態的預約信息列表
     * @param month
     * @return
     */
    List<StatusRsvnInfoVO> getMonthStatusStat(LocalDate month);
}
