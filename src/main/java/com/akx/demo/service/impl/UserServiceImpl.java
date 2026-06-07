package com.akx.demo.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.akx.demo.entity.User;
import com.akx.demo.mapper.UserMapper;
import com.akx.demo.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl
        extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Override
    public List<User> getUserByIds(List<Long> ids) {
        if(CollectionUtil.isEmpty(ids)){
            return null;
        }
        return baseMapper.selectList(new LambdaQueryWrapper<User>().in(User::getId, ids));
    }
}
