package com.akx.demo.service.impl;

import com.akx.demo.entity.Department;
import com.akx.demo.mapper.DepartmentMapper;
import com.akx.demo.service.DepartmentService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl
        extends ServiceImpl<DepartmentMapper, Department>
        implements DepartmentService {

    @Override
    public List<Department> getAllDepts() {
        return list();
    }

    @Override
    public List<Department> getAllDeptIdNames() {
        Wrapper<Department> queryWrapper = new LambdaQueryWrapper<Department>()
                .select(Department::getId, Department::getDeptName)
                .eq(Department::getEnable, true);
        return list(queryWrapper);
    }
}
