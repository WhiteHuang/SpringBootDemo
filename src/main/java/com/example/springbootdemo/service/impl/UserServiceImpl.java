package com.example.springbootdemo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springbootdemo.common.Constants;
import com.example.springbootdemo.controller.dto.UserDto;
import com.example.springbootdemo.entity.Menu;
import com.example.springbootdemo.entity.Role;
import com.example.springbootdemo.entity.User;
import com.example.springbootdemo.exception.ServiceException;
import com.example.springbootdemo.mapper.UserMapper;
import com.example.springbootdemo.service.IMenuService;
import com.example.springbootdemo.service.IRoleService;
import com.example.springbootdemo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootdemo.utils.TokenUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author white
 * @since 2024-04-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private IRoleService roleService;
    @Resource
    private IMenuService menuService;

    @Override
    public UserDto login(UserDto userDto) {
        User one = getUser(userDto);

        if (one != null) {
            BeanUtil.copyProperties(one, userDto, true);
            userDto.setToken(TokenUtil.genToken(one.getId().toString(), one.getPassword()));

            String role = userDto.getRole();
            List<Menu> reusltMenus = getRoleMenus(role);
            userDto.setMenus(reusltMenus);
            return userDto;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名密码错误");
        }

    }

    private List<Menu> getRoleMenus(String role) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("key_name", role);
        Role roleBean = roleService.getOne(queryWrapper);

        List<Integer> roleMenus = roleService.getRoleMenu(roleBean.getId());

        List<Menu> menus = menuService.getMenus("");
        List<Menu> reusltMenus = new ArrayList<>();
        for (Menu menu : menus) {
            if (roleMenus.contains(menu.getId())) {

                reusltMenus.add(menu);

                List<Menu> children = menu.getChildren();
                children.removeIf(child -> !roleMenus.contains(child.getId()));
            }

        }
        return reusltMenus;
    }

    @Override
    public UserDto register(UserDto userDto) {
        User one = getUser(userDto);

        if (one == null) {
            one = new User();
            BeanUtil.copyProperties(userDto, one, true);
            save(one);
            return userDto;
        } else {
            throw new ServiceException(Constants.CODE_600, "用户名已存在");
        }
    }

    private User getUser(UserDto userDto) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDto.getUsername());
        queryWrapper.eq("password", userDto.getPassword());
        User one;
        try {
            one = getOne(queryWrapper);

        } catch (Exception e) {
            throw new ServiceException(Constants.CODE_500, "系统错误");
        }
        return one;
    }
}
