package edu.miu.courseregistrationcommon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRecordBarcodeDTO {
    private LocalDateTime scanDateTime;
    private Long locationId;
    private String StudentId;
}
