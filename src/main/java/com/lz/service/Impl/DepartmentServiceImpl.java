package com.lz.service.Impl;

import com.lz.bean.Department;
import com.lz.mapper.DepartmentMapper;
import com.lz.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> findAll() {
        return departmentMapper.findAll();
    }

    @Override
    public Department findById(Long id) {
        return departmentMapper.findById(id);
    }

    @Override
    public String insert(Department department) {
        try {
            // 校验部门名称是否已存在
            Department existDepartment = departmentMapper.findByName(department.getDeptName());
            if (existDepartment != null) {
                return "部门名称已存在";
            }
            
            // 设置创建时间
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            
            // 默认状态为启用
            if (department.getStatus() == null) {
                department.setStatus(1);
            }
            
            // 默认父部门ID为0（顶级部门）
            if (department.getParentId() == null) {
                department.setParentId(0L);
            }
            
            // 默认排序为0
            if (department.getSortOrder() == null) {
                department.setSortOrder(0);
            }
            
            departmentMapper.insert(department);
            return "新增成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "新增失败：" + e.getMessage();
        }
    }

    @Override
    public String update(Department department) {
        try {
            // 校验部门是否存在
            Department existDepartment = departmentMapper.findById(department.getId());
            if (existDepartment == null) {
                return "部门不存在";
            }
            
            // 如果修改了部门名称，校验名称是否被其他部门使用
            if (!existDepartment.getDeptName().equals(department.getDeptName())) {
                Department departmentByName = departmentMapper.findByName(department.getDeptName());
                if (departmentByName != null && !departmentByName.getId().equals(department.getId())) {
                    return "部门名称已被其他部门使用";
                }
            }
            
            // 不能设置自己为父部门
            if (department.getId().equals(department.getParentId())) {
                return "不能设置自己为父部门";
            }
            
            // 设置更新时间
            department.setUpdateTime(new Date());
            
            departmentMapper.update(department);
            return "更新成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "更新失败：" + e.getMessage();
        }
    }

    @Override
    public String deleteById(Long id) {
        try {
            // 校验部门是否存在
            Department department = departmentMapper.findById(id);
            if (department == null) {
                return "部门不存在";
            }
            
            // 检查是否有子部门
            int childCount = departmentMapper.countByParentId(id);
            if (childCount > 0) {
                return "该部门下还有子部门，无法删除";
            }
            
            departmentMapper.deleteById(id);
            return "删除成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "删除失败：" + e.getMessage();
        }
    }
}
