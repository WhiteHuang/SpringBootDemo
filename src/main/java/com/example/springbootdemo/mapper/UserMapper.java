package com.example.springbootdemo.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootdemo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author white
 * @since 2024-04-13
 */
public interface UserMapper extends BaseMapper<User> {

    Page<User> findPage(Page<User> objectPage, @Param("username") String username, @Param("email")String email, @Param("address")String address);
}
