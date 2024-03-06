package edu.miu.courseregistrationcore.dto;

import edu.miu.courseregistrationcore.domain.Student;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@RequiredArgsConstructor
@Setter
@Getter
@ToString
public class StudentToAttendanceDTO {
    Student student;
    //    List<AttendanceRecord> attendanceRecords;
    List<AttendanceDTO> attendanceRecords;

    public StudentToAttendanceDTO(Student student, List<AttendanceDTO> records) {
        this.student = student;
        this.attendanceRecords = records;
    }

}
