package com.lz.service;

import com.lz.bean.Department;

import java.util.List;

public interface DepartmentService {
    
    /**
     * 查询所有部门
     */
    List<Department> findAll();
    
    /**
     * 根据ID查询部门
     */
    Department findById(Long id);
    
    /**
     * 新增部门
     */
    String insert(Department department);
    
    /**
     * 更新部门
     */
    String update(Department department);
    
    /**
     * 删除部门
     */
    String deleteById(Long id);
}
