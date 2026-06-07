package com.akx.demo.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.akx.demo.enumeration.BusinessErrorEnum;
import com.akx.demo.entity.Reservation;
import com.akx.demo.entity.Room;
import com.akx.demo.entity.User;
import com.akx.demo.enumeration.ReserveStatusEnum;
import com.akx.demo.excption.BusinessException;
import com.akx.demo.mapper.ReservationMapper;
import com.akx.demo.model.dto.RejectApplyDTO;
import com.akx.demo.model.dto.ReservationDTO;
import com.akx.demo.model.dto.ReserveRoomDTO;
import com.akx.demo.model.vo.ReservationUserInfo;
import com.akx.demo.model.vo.ReservationVO;
import com.akx.demo.model.vo.RoomResvInfoVO;
import com.akx.demo.model.vo.StatusRsvnInfoVO;
import com.akx.demo.service.ReservationService;
import com.akx.demo.service.RoomService;
import com.akx.demo.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl
        extends ServiceImpl<ReservationMapper, Reservation>
        implements ReservationService {

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;


    @Override
    public Reservation reserveRoom(ReserveRoomDTO dto) {
        // 進行相關的參數校驗
        validReserve(dto);
        // 根據user獲得部門信息
        User user = userService.getById(dto.getUserId());
        if(Objects.isNull(user) || !user.getEnable()){
            throw new BusinessException(BusinessErrorEnum.PARAM_ERROR);
        }
        // 創建申請物件
        Reservation resv = new Reservation();
        BeanUtils.copyProperties(dto, resv);
        resv.setDeptId(user.getDeptId());
        resv.setCreatorId(dto.getUserId());
        // 進行資料庫操作
        if(!this.save(resv)){
            throw new BusinessException(BusinessErrorEnum.DB_ERROR);
        }
        return resv;
    }

    // TODO 這裏最好使用redis看門狗，避免多服務間競爭
    @Override
    public Boolean rejectApply(RejectApplyDTO dto) {
        // TODO 這裏需要使用當前用戶登陸信息，進行校驗是否有權限進行預約退回操作；
        validRejected(dto);
        //
        Reservation rsvn = getById(dto.getReservationId());
        if(Objects.isNull(rsvn) || !rsvn.getStatus().equals(ReserveStatusEnum.PROCESSING.getCode())){
            throw new BusinessException(BusinessErrorEnum.PARAM_ERROR);
        }
        rsvn.setStatus(ReserveStatusEnum.REJECTED.getCode());
        rsvn.setRejectReason(dto.getRejectReason());
        rsvn.setRemark(dto.getRemark());
        // 當前用戶信息
//        rsvn.setUpdaterId();
        rsvn.setUpdateTime(LocalDateTime.now());
        UpdateWrapper<Reservation> updateWrapper =
                new UpdateWrapper<Reservation>()
                .eq("id", rsvn.getId())
                .eq("status", ReserveStatusEnum.PROCESSING.getCode());
        // execute the db
        if (baseMapper.update(rsvn, updateWrapper) < 1) {
            throw new BusinessException(BusinessErrorEnum.DB_ERROR);
        }
        return Boolean.TRUE;
    }

    @Override
    public List<RoomResvInfoVO> getResvUserInfoByDay(LocalDate date) {
        // TODO 這裏暫時不考慮會議室分頁的情形
        List<Room> roomList = roomService.getAllEnableRoom();
        if(CollectionUtil.isEmpty(roomList)){
            return new ArrayList<>();
        }
        // find the min time and max time of the date
        LocalDateTime dateStart = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime dateEnd = LocalDateTime.of(date, LocalTime.MAX);
        List<Reservation> rsvnList = baseMapper.selectList(new LambdaQueryWrapper<Reservation>()
                .eq(Reservation::getStatus, ReserveStatusEnum.APPROVED.getCode())
                .gt(Reservation::getStartTime, dateStart)
                .lt(Reservation::getEndTime, dateEnd)
        );
        // create result obj
        List<RoomResvInfoVO> results = new ArrayList<>();
        if(CollectionUtil.isEmpty(rsvnList)){
            // ONLY ROOM INFO
            for(Room r : roomList){
                RoomResvInfoVO vo = new RoomResvInfoVO();
                vo.setRoomName(r.getRoomName());
                vo.setResvUserInfoList(null);
                results.add(vo);
            }
            return results;
        }
        // get all userid of resvn list
        List<Long> userIds = rsvnList.stream()
                .map(Reservation::getUserId)
                .distinct()
                .collect(Collectors.toList());
        //
        List<User> users = userService.getUserByIds(userIds);
        // group the reservation by roomid
        Map<Long, List<Reservation>> mapByRoomId = rsvnList.stream()
                .collect(Collectors.groupingBy(Reservation::getRoomId));
        // construct the result
        for(Room r : roomList){
            RoomResvInfoVO vo = new RoomResvInfoVO();
            vo.setRoomName(r.getRoomName());
            // get the
            if(!mapByRoomId.containsKey(r.getId())){
                vo.setResvUserInfoList(null);
                results.add(vo);
                continue;
            }
            List<Reservation> itemList = mapByRoomId.get(r.getId()).stream()
                    .sorted(Comparator.comparing(Reservation::getStartTime,
                            Comparator.nullsFirst(Comparator.naturalOrder())))
                    .collect(Collectors.toList());
            List<ReservationUserInfo> infos = new ArrayList<>();
            itemList.forEach(t ->{
                ReservationUserInfo innerInfo = new ReservationUserInfo();
                BeanUtils.copyProperties(t, innerInfo);
                innerInfo.setResvId(t.getId());
                Optional<User> optUser = users.stream().filter(m -> m.getId().equals(t.getUserId())).findFirst();
                innerInfo.setRealName(optUser.isPresent() ? optUser.get().getRealName() : "");
                infos.add(innerInfo);
            });
            vo.setResvUserInfoList(infos);
            results.add(vo);
        }
        return results;
    }

    @Override
    public List<StatusRsvnInfoVO> getMonthStatusStat(LocalDate month) {
        // get the start and end time of the month
        month = month.withDayOfMonth(1);
        int daysInMonth = month.lengthOfMonth();
        LocalDateTime monthStartTime = LocalDateTime.of(month, LocalTime.MIN);
        month = month.plusDays(daysInMonth - 1);
        LocalDateTime monthEndTime = LocalDateTime.of(month, LocalTime.MAX);

        //
        List<ReservationDTO> dtoList = baseMapper.getFullRsvnInfo(monthStartTime, monthEndTime);

        // result list
        List<StatusRsvnInfoVO> results = new ArrayList<>();

        if(CollectionUtil.isEmpty(dtoList)){
            for(ReserveStatusEnum item : ReserveStatusEnum.values()) {
                StatusRsvnInfoVO vo = new StatusRsvnInfoVO();
                vo.setStatusCode(item.getCode());
                vo.setStatusDesc(item.getDesc());
                vo.setRsvnList(null);
                results.add(vo);
            }
            return results;
        }

        //
        Map<String, List<ReservationDTO>> mapByStatus =
                dtoList.stream().collect(Collectors.groupingBy(ReservationDTO::getStatus));



        for(ReserveStatusEnum item : ReserveStatusEnum.values()){
            StatusRsvnInfoVO vo = new StatusRsvnInfoVO();
            vo.setStatusCode(item.getCode());
            vo.setStatusDesc(item.getDesc());
            if(!mapByStatus.containsKey(item.getCode())){
                vo.setRsvnList(null);
                results.add(vo);
                continue;
            }
            List<ReservationVO> voList = new ArrayList<>();
            mapByStatus.get(item.getCode()).stream().forEach(t -> {
                ReservationVO innerVO = new ReservationVO();
                BeanUtils.copyProperties(t, innerVO);
                voList.add(innerVO);
            });
            vo.setRsvnList(voList);
            results.add(vo);
        }

        return results;
    }


    private void validReserve(ReserveRoomDTO dto){
        try{
            // 會議室id 不合法
            if(dto.getRoomId().longValue() < 1){
                throw new RuntimeException();
            }
            // 獲得當前的room信息
            Room room = roomService.getById(dto.getRoomId());
            if(Objects.isNull(room) || !room.getEnable()){
                throw new RuntimeException();
            }
            // 如果會議室容納人數達到上限
            if(room.getCapacity().intValue() < dto.getUserCount()){
                throw new RuntimeException();
            }
            // userid不合法
            if(dto.getUserId().longValue() < 1){
                throw new RuntimeException();
            }
            // 申請的會議的時間不能是過去
//            if(dto.getEndTime().isBefore(LocalDateTime.now())){
//                throw new RuntimeException();
//            }
            // 結束時間不能不能再開始時間之前。
            if(dto.getEndTime().isBefore(dto.getStartTime())){
                throw new RuntimeException();
            }
            if(Objects.isNull(dto.getTitle())){
                throw new RuntimeException();
            }
            if(Objects.isNull(dto.getUserCount()) || dto.getUserCount().intValue() < 1){
                throw new RuntimeException();
            }
            // TODO 工作時間段校驗，這邊先略過

            // TODO 直接從資料庫判斷是否有佔用，這個比較粗糙，應該先用redis將預定信息，鎖起來，
            //  先驗證redis中沒有相關預定佔用，然後db判斷，
            //  本方法完成後再進行redis key釋放，並添加超時釋放機制
            //  需要注意超時時間添加隨機值；

            // 這裏單純從資料庫中查詢
            LambdaQueryWrapper<Reservation> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper
                    .select(Reservation::getId)
                    .eq(Reservation::getRoomId, dto.getRoomId())
                    .in(Reservation::getStatus, "processing", "approved")
                    .and(qw -> qw
                            .le(Reservation::getStartTime, dto.getStartTime()).ge(Reservation::getEndTime, dto.getStartTime())
                            .or()
                            .le(Reservation::getStartTime, dto.getEndTime()).ge(Reservation::getEndTime, dto.getEndTime())
                            .or()
                            .and(subQw -> subQw
                                    .ge(Reservation::getStartTime, dto.getStartTime())  // start_time >= startTime
                                    .le(Reservation::getEndTime, dto.getEndTime())      // end_time <= endTime
                            )
                    );

            List<Reservation> list = baseMapper.selectList(queryWrapper);
            if(!CollectionUtil.isEmpty(list)){
                throw new RuntimeException();
            }
        }catch (Exception ex){
            // 兼容檢驗人員相關字段為null
            throw new BusinessException(BusinessErrorEnum.PARAM_ERROR);
        }
    }


    private void validRejected(RejectApplyDTO dto){
        if(Objects.isNull(dto)){
            throw new BusinessException(BusinessErrorEnum.PARAM_ERROR);
        }
        if(Objects.isNull(dto.getReservationId()) || dto.getReservationId().longValue() < 1){
            throw new BusinessException(BusinessErrorEnum.PARAM_ERROR);
        }
        if(StringUtils.isEmpty(dto.getRejectReason())){
            throw new BusinessException(BusinessErrorEnum.PARAM_ERROR);
        }
    }
}
