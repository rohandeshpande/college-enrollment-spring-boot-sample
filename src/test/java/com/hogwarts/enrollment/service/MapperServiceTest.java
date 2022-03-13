package com.hogwarts.enrollment.service;

import com.hogwarts.enrollment.constant.UserRole;
import com.hogwarts.enrollment.dao.entity.Course;
import com.hogwarts.enrollment.dao.entity.Role;
import com.hogwarts.enrollment.dao.entity.Section;
import com.hogwarts.enrollment.dao.entity.User;
import com.hogwarts.enrollment.dto.response.CourseResponseDto;
import com.hogwarts.enrollment.dto.response.StudentResponseDto;
import com.hogwarts.enrollment.dto.response.StudentsPaginationResponseDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@SpringBootTest
public class MapperServiceTest {
    @Autowired
    MapperService mapperService;

    @Test
    public void test_convertCourseEntityToDto() {
        Course course = Course.builder().courseId(1).courseName("Physics")
                .teacher(User.builder().name("ABC")
                        .role(Role.builder().type(UserRole.TEACHER.name()).build())
                        .build())
                .build();
        CourseResponseDto courseResponseDto = mapperService.convertCourseEntityToDto(course);
        Assertions.assertEquals(courseResponseDto.getName(), "Physics");
    }

    @Test
    public void test_convertStudentUserEntityToDto() {
        User student = User.builder().name("student1").userId(1)
                        .role(Role.builder().type(UserRole.TEACHER.name()).build()).build();
        student.addSection(Section.builder().sectionName("section-1").users(new HashSet<>()).build());
        student.addSection(Section.builder().sectionName("section-2").users(new HashSet<>()).build());
        StudentResponseDto studentResponseDto = mapperService.convertStudentUserEntityToDto(student);
        Assertions.assertEquals(studentResponseDto.getName(), "student1");
        Assertions.assertEquals(studentResponseDto.getStudentId(), 1);
        Assertions.assertEquals(studentResponseDto.getEnrolledSections().size(), 2);
    }

    @Test
    public void test_convertStudentUserEntitiesToDto() {
        List<User> students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User student = User.builder().name("student"+i).userId(i)
                    .role(Role.builder().type(UserRole.TEACHER.name()).build()).build();
            student.addSection(Section.builder().sectionName("section-1").users(new HashSet<>()).build());
            student.addSection(Section.builder().sectionName("section-2").users(new HashSet<>()).build());
            students.add(student);
        }

        StudentsPaginationResponseDto studentsPaginationResponseDto = mapperService.convertStudentUserEntitiesToDto(10,11,2, students);
        Assertions.assertEquals(studentsPaginationResponseDto.getPaginationMetadataResponseDto().getCount(), students.size());
        Assertions.assertEquals(studentsPaginationResponseDto.getPaginationMetadataResponseDto().getLimit(), 11);
        Assertions.assertEquals(studentsPaginationResponseDto.getPaginationMetadataResponseDto().getOffset(), 2);
        Assertions.assertEquals(studentsPaginationResponseDto.getPaginationMetadataResponseDto().getTotal(), 10);
        Assertions.assertEquals(studentsPaginationResponseDto.getEnrolledStudents().size(), 10);
    }
}
