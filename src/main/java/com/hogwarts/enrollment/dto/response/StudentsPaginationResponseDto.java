package com.hogwarts.enrollment.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentsPaginationResponseDto {
    @JsonProperty("metadata")
    PaginationMetadataResponseDto paginationMetadataResponseDto;

    @JsonProperty("enrolledStudents")
    List<StudentResponseDto> enrolledStudents;
}
