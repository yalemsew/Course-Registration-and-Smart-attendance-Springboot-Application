package edu.miu.courseregistrationcore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.miu.courseregistrationcore.domain.AttendanceRecord;
import edu.miu.courseregistrationcore.domain.CourseRegistration;
import edu.miu.courseregistrationcore.domain.Student;
import edu.miu.courseregistrationcore.dto.CourseStudentDto;
import edu.miu.courseregistrationcore.dto.StudentToAttendanceDTO;
import edu.miu.courseregistrationcore.integration.exception.KafkaSendException;
import edu.miu.courseregistrationcore.repository.AttendanceRecordRepository;
import edu.miu.courseregistrationcore.repository.CourseRegistrationRepository;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Transactional
public class CourseRegistrationService {

    @Autowired
    private CourseRegistrationRepository courseRegistrationRepository;

    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;

    @Autowired
    private AttendanceRecordService attendanceRecordService;
    @Autowired
    private KafkaTemplate<String, CourseStudentDto> kafkaTemplate;


    public List<CourseRegistration> getRegisteredCourses(String studentId) {
        return courseRegistrationRepository.findByStudentId(studentId);
    }

    public List<CourseRegistration> getAttendance(Integer offeringId, String studentId) {
        return courseRegistrationRepository.findByCourseOfferingIdAndStudentId(offeringId, studentId);
    }

    public List<Student> getDistinctStudentsByCourseOfferingId(int offeringId) {
        return courseRegistrationRepository.findDistinctStudentsByCourseOfferingId(offeringId);
    }

    public List<CourseRegistration> getAllCourseRegistrations() {
        return courseRegistrationRepository.findAll();
    }

    public List<CourseRegistration> getCourseRegistrationsByCourseOfferingId(int offeringId) {
        return courseRegistrationRepository.findAllByCourseOfferingId(offeringId);
    }

    public List<StudentToAttendanceDTO> getStudentsAttendanceByCourseOfferingId(int offeringId) {
        List<StudentToAttendanceDTO> studentToAttendanceDTOList = new ArrayList<>();

        List<Student> students = getDistinctStudentsByCourseOfferingId(offeringId);
        for (Student student : students) {
            List<AttendanceRecord> attendanceRecords = attendanceRecordService.getAttendance(student.getStudentID());
            studentToAttendanceDTOList.add(new StudentToAttendanceDTO(student, attendanceRecords.stream().map(AttendanceRecord::toAttendanceDTO).collect(Collectors.toList())));
        }
        return studentToAttendanceDTOList;
    }

    public void writeStudentsAttendanceToExcel(int offeringId) throws IOException {
        List<StudentToAttendanceDTO> studentToAttendanceDTOList = getStudentsAttendanceByCourseOfferingId(offeringId);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Students Attendance");

        //header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Student ID");
        headerRow.createCell(1).setCellValue("Attendance Records");

        //data rows
        for (int i = 0; i < studentToAttendanceDTOList.size(); i++) {
            StudentToAttendanceDTO dto = studentToAttendanceDTOList.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(dto.getStudent().getStudentID());
            row.createCell(1).setCellValue(dto.getAttendanceRecords().toString());
        }

        // Write to a file
        try (FileOutputStream fileOut = new FileOutputStream("students_attendance.xlsx")) {
            workbook.write(fileOut);
        }

        workbook.close();
    }

}
