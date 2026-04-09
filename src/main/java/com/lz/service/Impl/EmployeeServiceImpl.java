package com.lz.service.Impl;

import com.lz.bean.Employee;
import com.lz.mapper.EmployeeMapper;
import com.lz.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Map<String, Object> findByPage(int page, int size, String name, Long departmentId, Integer status) {
        Map<String, Object> result = new HashMap<>();
        
        // 计算偏移量
        int start = (page - 1) * size;
        
        // 查询数据列表
        List<Employee> list = employeeMapper.findByPage(start, size, name, departmentId, status);
        
        // 查询总数
        int total = employeeMapper.countByCondition(name, departmentId, status);
        
        result.put("list", list);
        result.put("total", total);
        result.put("page", page);
        result.put("size", size);
        result.put("totalPages", (total + size - 1) / size);
        
        return result;
    }

    @Override
    public Employee findById(Long id) {
        return employeeMapper.findById(id);
    }

    @Override
    public String insert(Employee employee) {
        try {
            // 校验工号是否已存在
            Employee existEmployee = employeeMapper.findByEmpNo(employee.getEmpNo());
            if (existEmployee != null) {
                return "工号已存在";
            }
            
            // 设置创建时间
            employee.setCreateTime(new Date());
            employee.setUpdateTime(new Date());
            
            // 默认状态为在职
            if (employee.getStatus() == null) {
                employee.setStatus(1);
            }
            
            employeeMapper.insert(employee);
            return "新增成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "新增失败：" + e.getMessage();
        }
    }

    @Override
    public String update(Employee employee) {
        try {
            // 校验员工是否存在
            Employee existEmployee = employeeMapper.findById(employee.getId());
            if (existEmployee == null) {
                return "员工不存在";
            }
            
            // 如果修改了工号，校验工号是否被其他员工使用
            if (!existEmployee.getEmpNo().equals(employee.getEmpNo())) {
                Employee employeeByEmpNo = employeeMapper.findByEmpNo(employee.getEmpNo());
                if (employeeByEmpNo != null && !employeeByEmpNo.getId().equals(employee.getId())) {
                    return "工号已被其他员工使用";
                }
            }
            
            // 设置更新时间
            employee.setUpdateTime(new Date());
            
            employeeMapper.update(employee);
            return "更新成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "更新失败：" + e.getMessage();
        }
    }

    @Override
    public String deleteById(Long id) {
        try {
            Employee employee = employeeMapper.findById(id);
            if (employee == null) {
                return "员工不存在";
            }
            
            employeeMapper.deleteById(id);
            return "删除成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "删除失败：" + e.getMessage();
        }
    }

    @Override
    public int countActiveEmployees() {
        return employeeMapper.countActiveEmployees();
    }

    @Override
    public List<Map<String, Object>> countByDepartment() {
        return employeeMapper.countByDepartment();
    }
}


/*
*
* 11*/