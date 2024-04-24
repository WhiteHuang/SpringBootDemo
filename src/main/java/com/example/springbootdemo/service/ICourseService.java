package com.example.springbootdemo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootdemo.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author white
 * @since 2024-04-23
 */
public interface ICourseService extends IService<Course> {

    Page<Course> findPage(Page<Course> objectPage, String name);

    boolean setStudentCourse(Integer courseId, Integer studentId);
}
