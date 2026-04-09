package com.lz.controller;

import com.lz.bean.Department;
import com.lz.service.DepartmentService;
import com.lz.tool.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /**
     * 查询所有部门列表
     */
    @GetMapping("/list")
    public Result list() {
        List<Department> list = departmentService.findAll();
        return Result.success(list);
    }

    /**
     * 根据ID查询部门详情
     */
    @GetMapping("/{id}")
    public Result getDetail(@PathVariable Long id) {
        Department department = departmentService.findById(id);
        if (department == null) {
            return Result.error("部门不存在");
        }
        return Result.success(department);
    }

    /**
     * 新增部门
     */
    @PostMapping
    public Result add(@RequestBody Department department) {
        String result = departmentService.insert(department);
        if ("新增成功".equals(result)) {
            return Result.success(result);
        } else {
            return Result.error(result);
        }
    }

    /**
     * 更新部门
     */
    @PutMapping
    public Result update(@RequestBody Department department) {
        String result = departmentService.update(department);
        if ("更新成功".equals(result)) {
            return Result.success(result);
        } else {
            return Result.error(result);
        }
    }

    /**
     * 删除部门
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        String result = departmentService.deleteById(id);
        if ("删除成功".equals(result)) {
            return Result.success(result);
        } else {
            return Result.error(result);
        }
    }
}
