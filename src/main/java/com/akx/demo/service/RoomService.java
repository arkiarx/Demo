package com.akx.demo.service;

import com.akx.demo.entity.Room;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface RoomService extends IService<Room> {

    /**
     * get all enable room
     * @return
     */
    List<Room> getAllEnableRoom();
}
