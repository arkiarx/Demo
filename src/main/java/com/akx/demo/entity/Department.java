package com.akx.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("mr_dept")
public class Department {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 父級id，根是-1
     */
    private Long parentId;

    /**
     * 部門名稱
     */
    private String deptName;

    /**
     * 是否可用
     */
    private Boolean enable;

    private Long creatorId;

    private LocalDateTime createTime;

    private Long updaterId;

    private LocalDateTime updateTime;



}
