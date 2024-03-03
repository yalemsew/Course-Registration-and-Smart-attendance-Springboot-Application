package edu.miu.courseregistrationcore.domain;

import edu.miu.courseregistrationcore.dto.AttendanceDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "AttendanceRecord")
public class AttendanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ScanDateTime")
    private LocalDateTime scanDateTime;

    @ManyToOne
    @JoinColumn(name = "LocationId")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "StudentId")
    private Student student;

    public AttendanceDTO toAttendanceDTO() {
        return new AttendanceDTO(this.id, this.scanDateTime, this.location.getId());
    }
}
