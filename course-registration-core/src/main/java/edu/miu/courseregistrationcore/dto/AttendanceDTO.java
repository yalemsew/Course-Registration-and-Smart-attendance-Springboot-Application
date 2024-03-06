package edu.miu.courseregistrationcore.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class AttendanceDTO {
    private Long id;
    private LocalDateTime scanDateTime;
    private Long locationId;


    public AttendanceDTO(Long id, LocalDateTime scanDateTime, Long locationId) {
        this.id = id;
        this.scanDateTime = scanDateTime;
        this.locationId = locationId;
    }


}
