package com.example.springbootdemo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootdemo.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import com.example.springbootdemo.service.IDictService;
import com.example.springbootdemo.entity.Dict;

import org.springframework.web.bind.annotation.RequestMapping;
import com.example.springbootdemo.common.Result;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author white
 * @since 2024-04-17
 */
@RestController
@RequestMapping("/dict")
        public class DictController {
    @Resource
private IDictService dictService;

@PostMapping
public Result save(@RequestBody Dict dict) {
    return Result.success(dictService.saveOrUpdate(dict));
}

@DeleteMapping("/{id}")
public Result delete(@PathVariable Integer id) {
    return Result.success(dictService.removeById(id));
}

@GetMapping
public Result findAll() {
    return Result.success(dictService.list());
}

@GetMapping("/{id}")
public Result findOne(@PathVariable Integer id) {
    return Result.success(dictService.getById(id));
}

@GetMapping("/page")
public Result findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize) {
    return Result.success(dictService.page(new Page<>(pageNum, pageSize)));
}

@PostMapping("/del/batch")
public Result deleteBatch(@RequestBody List<Integer> ids) {
    return Result.success(dictService.removeBatchByIds(ids));
}
}
