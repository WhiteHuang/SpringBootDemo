package com.example.springbootdemo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootdemo.common.Constants;
import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.entity.Dict;
import com.example.springbootdemo.entity.Role;
import com.example.springbootdemo.service.IDictService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import com.example.springbootdemo.service.IMenuService;
import com.example.springbootdemo.entity.Menu;

import org.springframework.web.bind.annotation.RequestMapping;
import com.example.springbootdemo.common.Result;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author white
 * @since 2024-04-16
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Resource
    private IMenuService menuService;

    @Resource
    private IDictService dictService;

    @PostMapping
    public Result save(@RequestBody Menu menu) {
        return Result.success(menuService.saveOrUpdate(menu));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return Result.success(menuService.removeById(id));
    }

    @GetMapping
    public Result findAll(@RequestParam(defaultValue = "") String name,@RequestParam(defaultValue = "") String description) {
        return Result.success(menuService.getMenus(name,description));
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(menuService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam String name) {

        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        if (!name.isEmpty()) {
            wrapper.like("name", name);
        }

        wrapper.orderByDesc("sort");
        return Result.success(menuService.page(new Page<>(pageNum, pageSize),wrapper));
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        return Result.success(menuService.removeBatchByIds(ids));
    }


    @GetMapping("/icons")
    public Result getIcons(){
        QueryWrapper<Dict> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type", Constants.DICT_TYPE_ICON);
        return Result.success(dictService.list(queryWrapper));
    }

}
