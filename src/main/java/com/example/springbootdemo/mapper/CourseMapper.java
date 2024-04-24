package com.example.springbootdemo.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootdemo.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author white
 * @since 2024-04-23
 */
public interface CourseMapper extends BaseMapper<Course> {

    Page<Course> findPage(Page<Course> objectPage,@Param("name") String name);

    boolean setStudentCourse(@Param("courseId") Integer courseId, @Param("studentId") Integer studentId);
    boolean deleteStuCourse(@Param("courseId") Integer courseId, @Param("studentId") Integer studentId);
}
