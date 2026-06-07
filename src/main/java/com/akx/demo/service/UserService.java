package com.akx.demo.service;

import com.akx.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface UserService extends IService<User> {

    List<User> getUserByIds(List<Long> ids);
}
