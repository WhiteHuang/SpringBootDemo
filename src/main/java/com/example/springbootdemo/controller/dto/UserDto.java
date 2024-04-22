package com.example.springbootdemo.controller.dto;

import com.example.springbootdemo.entity.Menu;
import lombok.Data;

import java.util.List;

/**
 * 登录数据接收类
 */
@Data
public class UserDto {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;
    private String token;
    private String role;
    private List<Menu> menus;
}
