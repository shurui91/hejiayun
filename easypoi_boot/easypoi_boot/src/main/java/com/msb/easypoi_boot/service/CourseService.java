package com.msb.easypoi_boot.service;

import com.msb.easypoi_boot.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> findAll();

    void save(List<Course> courses);
}
