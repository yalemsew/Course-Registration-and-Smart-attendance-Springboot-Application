package edu.miu.courseregistrationcore.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttendanceRecordDTO {
    // private long id;
    private LocalDateTime scanDateTime;
    private String locationName;
    //locationType: class room, lab, library, etc.
    private String locationType;
    private String studentName;
}
