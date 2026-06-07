package com.akx.demo.controller;

import com.akx.demo.entity.Reservation;
import com.akx.demo.excption.BusinessException;
import com.akx.demo.model.common.ResponseResult;
import com.akx.demo.model.dto.RejectApplyDTO;
import com.akx.demo.model.dto.ReserveRoomDTO;
import com.akx.demo.model.vo.RoomResvInfoVO;
import com.akx.demo.model.vo.StatusRsvnInfoVO;
import com.akx.demo.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/meetingRoom")
@Slf4j
public class MeetingRoomController {

    @Autowired
    private ReservationService reservationService;

//    @ApiOperation("預約會議室功能")
    @PostMapping("/reserve")
    public ResponseResult<Reservation> reserveRoom(@RequestBody ReserveRoomDTO dto){
        try{
            return ResponseResult.success(reservationService.reserveRoom(dto));
        }catch(BusinessException bex){
            log.error(bex.getMessage());
            return ResponseResult.error(bex);
        }catch(Exception ex){
            log.error(ex.toString());
            return ResponseResult.systemError();
        }
    }

//    @ApiOperation("退回申請")
    @PostMapping("/applyReject")
    public ResponseResult<Boolean> rejectApply(@RequestBody RejectApplyDTO dto){
        try{
            return ResponseResult.success(reservationService.rejectApply(dto));
        }catch(BusinessException bex){
            log.error(bex.getMessage());
            return ResponseResult.error(bex);
        }catch(Exception ex){
            log.error(ex.toString());
            return ResponseResult.systemError();
        }
    }

//    @ApiOperation("當日會議室預約情況")
    @GetMapping("/getResvInfoByDate/{date}")
    public ResponseResult<List<RoomResvInfoVO>> getResvUserInfoByDay(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){
        return ResponseResult.success(reservationService.getResvUserInfoByDay(date));
    }


//    @ApiOperation("月份下分狀態預約情況匯總")
    @GetMapping("/statusStat/{month}")
    public ResponseResult<List<StatusRsvnInfoVO>> getMonthStat(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate month){
        return ResponseResult.success(reservationService.getMonthStatusStat(month));
    }

}
