package com.example.springbootdemo.service;

import com.example.springbootdemo.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author white
 * @since 2024-04-16
 */
public interface IMenuService extends IService<Menu> {
    List<Menu> getMenus(String name);
}
