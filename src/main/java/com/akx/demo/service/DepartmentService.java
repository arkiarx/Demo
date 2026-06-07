package com.akx.demo.service;

import com.akx.demo.entity.Department;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface DepartmentService extends IService<Department> {

    List<Department> getAllDepts();

    List<Department> getAllDeptIdNames();
}
