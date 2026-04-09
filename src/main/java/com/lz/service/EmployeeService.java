package com.lz.service;

import com.lz.bean.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    
    /**
     * 分页查询员工列表
     */
    Map<String, Object> findByPage(int page, int size, String name, Long departmentId, Integer status);
    
    /**
     * 根据ID查询员工
     */
    Employee findById(Long id);
    
    /**
     * 新增员工
     */
    String insert(Employee employee);
    
    /**
     * 更新员工
     */
    String update(Employee employee);
    
    /**
     * 删除员工
     */
    String deleteById(Long id);
    
    /**
     * 统计在职员工数量
     */
    int countActiveEmployees();
    
    /**
     * 统计各部门员工数量
     */
    List<Map<String, Object>> countByDepartment();
}
