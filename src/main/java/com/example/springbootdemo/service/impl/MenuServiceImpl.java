package com.example.springbootdemo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springbootdemo.entity.Menu;
import com.example.springbootdemo.mapper.MenuMapper;
import com.example.springbootdemo.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author white
 * @since 2024-04-16
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<Menu> getMenus(String name) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if (StrUtil.isNotBlank(name))
            queryWrapper.eq("name", name);
        List<Menu> list = list(queryWrapper);
        List<Menu> parentMenus = list.stream().filter(menu -> menu.getPId() == null).collect(Collectors.toList());
        for (Menu menu : parentMenus) {
            List<Menu> childrenMenu = list.stream().filter(menu1 -> menu.getId().equals(menu1.getPId())).collect(Collectors.toList());
            menu.setChildren(childrenMenu);
        }
        return parentMenus;
    }
}
