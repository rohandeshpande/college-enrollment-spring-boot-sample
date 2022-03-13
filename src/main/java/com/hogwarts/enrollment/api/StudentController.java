package com.hogwarts.enrollment.api;

import com.hogwarts.enrollment.dto.response.StudentResponseDto;
import com.hogwarts.enrollment.dto.response.StudentsPaginationResponseDto;
import com.hogwarts.enrollment.service.StudentService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/enrollment-svc/v1/students")
@Api(value = "Student/Sections Resource REST Endpoint", description = "Enrolle/Withdraw students from Course Sections")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping(path = "/{studentId}/sections/{sectionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentResponseDto> enrollStudentInSection(@PathVariable("studentId") Integer studentId,
                                                     @PathVariable("sectionId") Integer sectionId) {
        log.info("Received request to enroll StudentId: {} in SectionId: {}", studentId, sectionId);
        return ResponseEntity.ok(studentService.enrollStudentInSection(studentId, sectionId));
    }

    @DeleteMapping(path = "/{studentId}/sections/{sectionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentResponseDto> withdrawStudentFromSection(@PathVariable("studentId") Integer studentId,
                                                       @PathVariable("sectionId") Integer sectionId) {
        log.info("Received request to withdraw StudentId: {} from SectionId: {}", studentId, sectionId);
        return ResponseEntity.ok(studentService.withdrawStudentFromSection(studentId, sectionId));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentsPaginationResponseDto> getStudentsPaginated(@RequestParam("sectionId") Integer sectionId,
                                                                              @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                                                              @RequestParam(value = "offset", defaultValue = "0") Integer offset) {
        log.info("Received request to get students enrolled in SectionId: {}", sectionId);
        return ResponseEntity.ok(studentService.getStudentsPaginated(sectionId, limit, offset));
    }
}
