package com.akx.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("mr_user")
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 登陸用戶名
     */
    private String username;

    /**
     * 登陸密碼
     */
    private String password;

    /**
     * 郵箱
     */
    private String email;

    /**
     * 用戶編碼
     */
    private String userCode;

    /**
     * 真實姓名
     */
    private String realName;

    /**
     * 手機號碼
     */
    private String mobile;

    /**
     * 是否可用
     */
    private Boolean enable;

    /**
     * 是否是審核者身分
     */
    private Boolean audit;

    /**
     * 所屬部門id
     */
    private Long deptId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
