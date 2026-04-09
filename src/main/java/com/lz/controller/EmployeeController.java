package com.lz.controller;

import com.lz.bean.Employee;
import com.lz.service.EmployeeService;
import com.lz.tool.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 分页查询员工列表
     */
    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(required = false) String name,
                       @RequestParam(required = false) Long departmentId,
                       @RequestParam(required = false) Integer status) {
        Map<String, Object> data = employeeService.findByPage(page, size, name, departmentId, status);
        return Result.success(data);
    }

    /**
     * 根据ID查询员工详情
     */
    @GetMapping("/{id}")
    public Result getDetail(@PathVariable Long id) {
        Employee employee = employeeService.findById(id);
        if (employee == null) {
            return Result.error("员工不存在");
        }
        return Result.success(employee);
    }

    /**
     * 新增员工
     */
    @PostMapping
    public Result add(@RequestBody Employee employee) {
        String result = employeeService.insert(employee);
        if ("新增成功".equals(result)) {
            return Result.success(result);
        } else {
            return Result.error(result);
        }
    }

    /**
     * 更新员工
     */
    @PutMapping
    public Result update(@RequestBody Employee employee) {
        String result = employeeService.update(employee);
        if ("更新成功".equals(result)) {
            return Result.success(result);
        } else {
            return Result.error(result);
        }
    }

    /**
     * 删除员工
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        String result = employeeService.deleteById(id);
        if ("删除成功".equals(result)) {
            return Result.success(result);
        } else {
            return Result.error(result);
        }
    }
}
