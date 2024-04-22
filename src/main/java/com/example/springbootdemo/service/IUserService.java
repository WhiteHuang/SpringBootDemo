package com.example.springbootdemo.service;

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
}
