package com.hogwarts.enrollment.service;

import com.hogwarts.enrollment.dao.entity.Course;
import com.hogwarts.enrollment.dao.entity.Section;
import com.hogwarts.enrollment.dao.entity.User;
import com.hogwarts.enrollment.dao.repository.CourseRepository;
import com.hogwarts.enrollment.dto.request.CourseRequestDto;
import com.hogwarts.enrollment.dto.response.CourseResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private DBRecordValidatorService dbRecordValidatorService;

    @Autowired
    private MapperService mapperService;

    @Transactional
    public CourseResponseDto createCourse(CourseRequestDto courseRequestDto) {
        User teacher = dbRecordValidatorService.validateAndGetTeacher(courseRequestDto.getTeacherUserId());
        Course newCourse = Course.builder().courseName(courseRequestDto.getName()).teacher(teacher).build();
        courseRequestDto.getSections().forEach(sectionRequest -> {
            newCourse.addSection(Section.builder().sectionName(sectionRequest.getName()).build());
        });
        Course courseEntity = courseRepository.save(newCourse);
        return mapperService.convertCourseEntityToDto(courseEntity);
    }

    @Transactional
    public CourseResponseDto updateCourse(Integer courseId, CourseRequestDto courseRequestDto) {
        User teacher = dbRecordValidatorService.validateAndGetTeacher(courseRequestDto.getTeacherUserId());
        Course course = dbRecordValidatorService.validateAndGetCourse(courseId);
        dbRecordValidatorService.validateUserSectionAssociation(course);
        course.setCourseName(courseRequestDto.getName());
        course.setTeacher(teacher);
        if (courseRequestDto.getSections() != null) {
            course.getSections().clear();
            courseRequestDto.getSections().forEach(sectionRequest -> {
                course.addSection(Section.builder().sectionName(sectionRequest.getName()).build());
            });
        }
        Course courseEntity = courseRepository.save(course);
        return mapperService.convertCourseEntityToDto(courseEntity);
    }
}
