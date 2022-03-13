package com.hogwarts.enrollment.service;

import com.hogwarts.enrollment.dao.entity.Course;
import com.hogwarts.enrollment.dao.entity.Role;
import com.hogwarts.enrollment.dao.entity.Section;
import com.hogwarts.enrollment.dao.entity.User;
import com.hogwarts.enrollment.dao.repository.CourseRepository;
import com.hogwarts.enrollment.dao.repository.SectionRepository;
import com.hogwarts.enrollment.dao.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class DBRecordValidatorServiceTest {
    @Autowired
    DBRecordValidatorService dbRecordValidatorService;
    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private SectionRepository sectionRepository;

    @Test
    public void test_validateAndGetStudent(Integer studentUserId) {
        User user1 = User.builder().userId(1).build();
        when(userRepository.findById(user1.getUserId())).thenReturn(Optional.of(user1));
        Assertions.assertEquals(dbRecordValidatorService.validateAndGetStudent(user1.getUserId()), user1);
    }

    public User validateAndGetTeacher(Integer teacherUserId) {
        return userRepository.findById(teacherUserId)
                .filter(user -> user.getRole().isTeacher())
                .orElseThrow(() -> new IllegalArgumentException(format("Cannot find user with role 'TEACHER' for teacherUserId: %d",
                        teacherUserId)));
    }

    public Course validateAndGetCourse(Integer courseId) {
        return courseRepository.findById(courseId).orElseThrow(() ->
                new IllegalArgumentException(format("Cannot find Course with courseId: %d", courseId))
        );
    }

    public Section validateAndGetSection(Integer sectionId) {
        return sectionRepository.findById(sectionId).orElseThrow(() ->
                new IllegalArgumentException(format("Cannot find Section with sectionId: %d", sectionId))
        );
    }

    public void validateUserSectionAssociation(Course course) {
        List<Integer> sectionIds = course.getSections() != null ?
                course.getSections().stream().map(Section::getSectionId).collect(Collectors.toList()) : new ArrayList<>();
        sectionIds.forEach(sectionId -> {
            Integer count = userRepository.countAllBySections_SectionId(sectionId).orElse(0);
            if (count > 0){
                throw new IllegalArgumentException("Course can't be updated as students have enrolled in its sections.");
            }
        });
    }

    public boolean isUserAdmin(Integer userId) {
        return userRepository.findById(userId).map(User::getRole).map(Role::isAdmin).orElse(false);
    }
}
