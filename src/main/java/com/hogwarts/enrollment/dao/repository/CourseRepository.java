package com.hogwarts.enrollment.dao.repository;

import com.hogwarts.enrollment.dao.entity.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Integer> {

}
