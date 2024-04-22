package com.example.springbootdemo.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springbootdemo.entity.Menu;
import com.example.springbootdemo.entity.Role;
import com.example.springbootdemo.entity.RoleMenu;
import com.example.springbootdemo.mapper.RoleMapper;
import com.example.springbootdemo.mapper.RoleMenuMapper;
import com.example.springbootdemo.service.IMenuService;
import com.example.springbootdemo.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author white
 * @since 2024-04-16
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService menuService;

    @Transactional
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
        QueryWrapper<RoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        roleMenuMapper.delete(wrapper);

        // FIXME demo ,不要在循环中进行数据库操作，费时
        List<Integer> parentMenu = new ArrayList<>();//临时保存以插入得父级菜单
        for (Integer menuId : menuIds) {
            Menu menu = menuService.getById(menuId);
            if (menu.getPId() != null && !menuIds.contains(menu.getPId()) && !parentMenu.contains(menu.getPId())) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menu.getPId());
                roleMenuMapper.insert(roleMenu);
                parentMenu.add(menu.getPId());
            }

            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        QueryWrapper<RoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        List<RoleMenu> roleMenus = roleMenuMapper.selectList(wrapper);
        List<Integer> menus = new ArrayList<>();
        for (RoleMenu menu : roleMenus) {
//            Menu menuData = menuService.getById(menu.getMenuId());
//            if(StrUtil.isBlank(menuData.getPath())){
//                continue;
//            }
            menus.add(menu.getMenuId());
        }
        return menus;
    }
}
