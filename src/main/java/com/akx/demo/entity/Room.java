package com.akx.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("mr_room")
public class Room {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 會議室名稱
     */
    private String roomName;

    /**
     * 容量
     */
    private Integer capacity;

    /**
     * 是否可用
     */
    private Boolean enable;

    private Long creatorId;

    private LocalDateTime createTime;

    private Long updaterId;

    private LocalDateTime updateTime;

}
