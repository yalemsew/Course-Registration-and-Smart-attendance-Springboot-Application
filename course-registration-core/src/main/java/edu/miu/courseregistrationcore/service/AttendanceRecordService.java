package edu.miu.courseregistrationcore.service;

import edu.miu.courseregistrationcommon.dto.AttendanceRecordBarcodeDTO;
import edu.miu.courseregistrationcore.domain.AttendanceRecord;
import edu.miu.courseregistrationcore.domain.Location;
import edu.miu.courseregistrationcore.domain.Student;
import edu.miu.courseregistrationcore.integration.exception.LocationNotFoundException;
import edu.miu.courseregistrationcore.integration.exception.StudentNotFoundException;
import edu.miu.courseregistrationcore.repository.AttendanceRecordRepository;
import edu.miu.courseregistrationcore.repository.LocationRepository;
import edu.miu.courseregistrationcore.repository.StudentRepository;
import edu.miu.courseregistrationcore.service.dto.AttendanceRecordDTO;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AttendanceRecordService {

    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private LocationRepository locationRepository;

    public List<AttendanceRecordDTO> getAttendaceRecords(String studentId) {
        List<AttendanceRecord> records = attendanceRecordRepository.findAllAttendancerecordOfStudent(studentId);
        return records.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public AttendanceRecordDTO convertToDTO(AttendanceRecord record) {
        AttendanceRecordDTO dto = new AttendanceRecordDTO();
        dto.setScanDateTime(record.getScanDateTime());
        dto.setLocationName(record.getLocation().getName());
        dto.setLocationType(record.getLocation().getLocationType().getType());
        dto.setStudentName(record.getStudent().getFirstName() + " " + record.getStudent().getLastName());
        return dto;
    }

    public void generateAttendanceExcel(Integer offeringId, String studentId, HttpServletResponse response, ServletOutputStream outputStream) {
        List<AttendanceRecord> records = attendanceRecordRepository.findByCourseOfferingId(offeringId, studentId);
        System.out.println("In generateAttendance ");
        System.out.println(records);


    }

    public void createAttendancerecord(AttendanceRecordBarcodeDTO dto) {
        AttendanceRecord record = convertFromDTO(dto);
        attendanceRecordRepository.save(record);
    }

    public AttendanceRecord convertFromDTO(AttendanceRecordBarcodeDTO dto) {
        AttendanceRecord record = new AttendanceRecord();
        Student student = studentRepository.findByStudentID(dto.getStudentId())
                .orElseThrow(() -> new StudentNotFoundException("No student with this id"));
        Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new LocationNotFoundException("Location not found"));

        record.setScanDateTime(dto.getScanDateTime());
        record.setStudent(student);
        record.setLocation(location);

        return record;
    }

    public List<AttendanceRecord> getAttendance(String studentId) {
        return attendanceRecordRepository.findAllAttendanceRecordsByStudentId(studentId);
    }
}
