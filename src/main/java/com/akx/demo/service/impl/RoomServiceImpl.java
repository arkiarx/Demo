package com.akx.demo.service.impl;

import com.akx.demo.entity.Room;
import com.akx.demo.mapper.RoomMapper;
import com.akx.demo.service.RoomService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl
        extends ServiceImpl<RoomMapper, Room>
        implements RoomService {

    @Override
    public List<Room> getAllEnableRoom() {
        return baseMapper.selectList(new LambdaQueryWrapper<Room>().eq(Room::getEnable , true));
    }
}
