package com.msb.easypoi_boot.service.impl;

import com.msb.easypoi_boot.entity.Course;
import com.msb.easypoi_boot.mapper.CourseMapper;
import com.msb.easypoi_boot.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> findAll() {
        return courseMapper.findAll();
    }

    @Override
    public void save(List<Course> courses) {
        courses.forEach(course -> {
            course.setCid(null);    // 自动生成id，不需要使用excel中的编号
            courseMapper.save(course);
        });
    }
}
