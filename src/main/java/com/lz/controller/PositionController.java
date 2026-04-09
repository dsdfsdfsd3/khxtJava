package com.lz.controller;

import com.lz.bean.Position;
import com.lz.service.PositionService;
import com.lz.tool.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/position")
public class PositionController {

    @Autowired
    private PositionService positionService;

    /**
     * 查询所有岗位列表
     */
    @GetMapping("/list")
    public Result list() {
        List<Position> list = positionService.findAll();
        return Result.success(list);
    }

    /**
     * 根据ID查询岗位详情
     */
    @GetMapping("/{id}")
    public Result getDetail(@PathVariable Long id) {
        Position position = positionService.findById(id);
        if (position == null) {
            return Result.error("岗位不存在");
        }
        return Result.success(position);
    }

    /**
     * 新增岗位
     */
    @PostMapping
    public Result add(@RequestBody Position position) {
        String result = positionService.insert(position);
        if ("新增成功".equals(result)) {
            return Result.success(result);
        } else {
            return Result.error(result);
        }
    }

    /**
     * 更新岗位
     */
    @PutMapping
    public Result update(@RequestBody Position position) {
        String result = positionService.update(position);
        if ("更新成功".equals(result)) {
            return Result.success(result);
        } else {
            return Result.error(result);
        }
    }

    /**
     * 删除岗位
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        String result = positionService.deleteById(id);
        if ("删除成功".equals(result)) {
            return Result.success(result);
        } else {
            return Result.error(result);
        }
    }
}
