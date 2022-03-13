package com.hogwarts.enrollment.service;

import com.hogwarts.enrollment.dao.entity.Course;
import com.hogwarts.enrollment.dao.entity.User;
import com.hogwarts.enrollment.dto.response.CourseResponseDto;
import com.hogwarts.enrollment.dto.response.PaginationMetadataResponseDto;
import com.hogwarts.enrollment.dto.response.SectionResponseDto;
import com.hogwarts.enrollment.dto.response.StudentResponseDto;
import com.hogwarts.enrollment.dto.response.StudentsPaginationResponseDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MapperService {

    public CourseResponseDto convertCourseEntityToDto(Course course) {
        List<SectionResponseDto> sections = new ArrayList<>();
        course.getSections().forEach(s -> sections.add(SectionResponseDto.builder()
                .sectionId(s.getSectionId()).name(s.getSectionName()).build()));

        return CourseResponseDto.builder().courseId(course.getCourseId()).name(course.getCourseName())
                .sections(sections).build();
    }

    public StudentResponseDto convertStudentUserEntityToDto(User user) {
        List<SectionResponseDto> enrolledSections = new ArrayList<>();
        user.getSections().forEach(s -> enrolledSections.add(SectionResponseDto.builder()
                .sectionId(s.getSectionId()).name(s.getSectionName()).build()));

        return StudentResponseDto.builder().studentId(user.getUserId())
                .name(user.getName()).enrolledSections(enrolledSections).build();
    }

    public StudentsPaginationResponseDto convertStudentUserEntitiesToDto(int total, int limit, int offset, List<User> students) {
        PaginationMetadataResponseDto paginationMetadata = PaginationMetadataResponseDto.builder()
                .total(total).limit(limit).offset(offset).count(students.size()).build();
        List<StudentResponseDto> enrolledStudents = new ArrayList<>();
        students.forEach(s -> enrolledStudents.add(StudentResponseDto.builder()
                .name(s.getName()).studentId(s.getUserId()).build()));
        return StudentsPaginationResponseDto.builder().paginationMetadataResponseDto(paginationMetadata)
                .enrolledStudents(enrolledStudents).build();
    }
}
