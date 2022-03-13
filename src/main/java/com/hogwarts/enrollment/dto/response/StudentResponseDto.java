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
public class StudentResponseDto {
    @JsonProperty("studentId")
    private Integer studentId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("enrolledSections")
    private List<SectionResponseDto> enrolledSections;
}
