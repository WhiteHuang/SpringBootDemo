package com.example.springbootdemo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootdemo.controller.dto.UserDto;
import com.example.springbootdemo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author white
 * @since 2024-04-13
 */
public interface IUserService extends IService<User> {

    UserDto login(UserDto userDto);

    UserDto register(UserDto userDto);

    Page<User> findPage(Page<User> objectPage,  String username, String email, String address);
}
