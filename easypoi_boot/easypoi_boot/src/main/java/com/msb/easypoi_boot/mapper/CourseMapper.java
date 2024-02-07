package com.msb.easypoi_boot.mapper;

import com.msb.easypoi_boot.entity.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {
    // 查询所有课程
    List<Course> findAll();

    // 插入记录
    void save(Course course);
}
