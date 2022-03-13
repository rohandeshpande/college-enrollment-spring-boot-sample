package com.hogwarts.enrollment.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseRequestDto {

    @JsonProperty("name")
    @NotNull
    @Size(min = 5, max = 45)
    private String name;

    @JsonProperty("teacherUserId")
    @NotNull
    @Min(1)
    @Max(Integer.MAX_VALUE)
    private Integer teacherUserId;

    @JsonProperty("sections")
    @NotNull
    @Size(min = 1, max = 15)
    private List<SectionRequestDto> sections;
}
