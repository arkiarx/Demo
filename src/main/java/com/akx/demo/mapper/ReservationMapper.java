package com.akx.demo.mapper;

import com.akx.demo.entity.Reservation;
import com.akx.demo.model.dto.ReservationDTO;
import com.akx.demo.model.dto.ReserveRoomDTO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ReservationMapper extends BaseMapper<Reservation> {

//    List<Long> getOccupied(@Param("dto") ReserveRoomDTO dto);

    List<ReservationDTO> getFullRsvnInfo(@Param("startTime") LocalDateTime startTime,
                                         @Param("endTime") LocalDateTime endTime);
}
