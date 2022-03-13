package com.hogwarts.enrollment.api;

import com.hogwarts.enrollment.dto.request.CourseRequestDto;
import com.hogwarts.enrollment.dto.response.CourseResponseDto;
import com.hogwarts.enrollment.service.CourseService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/enrollment-svc/v1/courses")
@Slf4j
@Api(value = "Course Resource REST Endpoint", description = "Create/Update courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@DBRecordValidatorService.isUserAdmin(#adminUserId)")
    public ResponseEntity<CourseResponseDto> createCourse(@RequestHeader("admin-user-id") Integer adminUserId,
                                                          @RequestBody @Valid CourseRequestDto courseRequestDto) {
      log.info("Received request to create new course");
      CourseResponseDto responseBody = courseService.createCourse(courseRequestDto);
      return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @PutMapping(path = "/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@DBRecordValidatorService.isUserAdmin(#adminUserId)")
    public ResponseEntity<CourseResponseDto> updateCourse(@RequestHeader("admin-user-id") Integer adminUserId,
                                                           @PathVariable("courseId") Integer courseId,
                                                           @RequestBody @Valid CourseRequestDto courseRequestDto) {
        log.info("Received request to update courseId: {}", courseId);
        CourseResponseDto responseBody = courseService.updateCourse(courseId, courseRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
