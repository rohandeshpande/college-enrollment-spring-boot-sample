package com.hogwarts.enrollment.service;

import com.hogwarts.enrollment.dao.entity.Section;
import com.hogwarts.enrollment.dao.entity.User;
import com.hogwarts.enrollment.dao.repository.UserRepository;
import com.hogwarts.enrollment.dto.response.StudentResponseDto;
import com.hogwarts.enrollment.dto.response.StudentsPaginationResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private DBRecordValidatorService dbRecordValidatorService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MapperService mapperService;

    public StudentResponseDto enrollStudentInSection(Integer studentId, Integer sectionId) {
        User user = dbRecordValidatorService.validateAndGetStudent(studentId);
        Section section = dbRecordValidatorService.validateAndGetSection(sectionId);
        user.addSection(section);
        User userEntity = userRepository.save(user);
        return mapperService.convertStudentUserEntityToDto(userEntity);
    }

    public StudentResponseDto withdrawStudentFromSection(Integer studentId, Integer sectionId) {
        User user = dbRecordValidatorService.validateAndGetStudent(studentId);
        Section section = dbRecordValidatorService.validateAndGetSection(sectionId);
        user.removeSection(section);
        User userEntity = userRepository.save(user);
        return mapperService.convertStudentUserEntityToDto(userEntity);
    }

    public StudentsPaginationResponseDto getStudentsPaginated(Integer sectionId, Integer limit, Integer offset) {
        int total = userRepository.countAllBySections_SectionId(sectionId).orElse(0);
        List<User> enrolledStudents = userRepository.findAllBySections_SectionIdOrderByUserIdAsc(sectionId, PageRequest.of(offset, limit));
        return mapperService.convertStudentUserEntitiesToDto(total, limit, offset, enrolledStudents);
    }
}
