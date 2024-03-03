package edu.miu.courseregistrationcore.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttendanceRecordDTO {
    private LocalDateTime scanDateTime;
    private String locationName;
    private String locationType;
    private String studentName;
}
