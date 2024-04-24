package com.example.springbootdemo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootdemo.entity.Course;
import com.example.springbootdemo.mapper.CourseMapper;
import com.example.springbootdemo.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author white
 * @since 2024-04-23
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Resource
    private CourseMapper courseMapper;

    @Override
    public Page<Course> findPage(Page<Course> objectPage, String name) {

        return courseMapper.findPage(objectPage,name);
    }

    @Transactional
    @Override
    public boolean setStudentCourse(Integer courseId, Integer studentId) {
        courseMapper.deleteStuCourse(courseId,studentId);
        return courseMapper.setStudentCourse(courseId,studentId);
    }
}
