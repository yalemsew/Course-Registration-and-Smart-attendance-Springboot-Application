package edu.miu.courseregistrationcore.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseStudentDto {
    private String email;
    private LocalDate courseStartDate;
    private String CourseName;
}

