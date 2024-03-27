package edu.miu.courseregistrationcore.controller;

import edu.miu.courseregistrationcore.domain.CourseOffering;
import edu.miu.courseregistrationcore.domain.CourseRegistration;
import edu.miu.courseregistrationcore.integration.utils.JwtUtils;
import edu.miu.courseregistrationcore.service.AttendanceRecordService;
import edu.miu.courseregistrationcore.service.CourseOfferingService;
import edu.miu.courseregistrationcore.service.CourseRegistrationService;
import edu.miu.courseregistrationcore.service.CourseService;
import edu.miu.courseregistrationcore.service.dto.AttendanceRecordDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;

@RestController
@RequestMapping("/student-view")
@PreAuthorize("hasAnyRole('STUDENT')")
public class StudentController {

    private final CourseService courseService;
    private final CourseOfferingService courseOfferingService;
    private final CourseRegistrationService courseRegistrationService;
    private final AttendanceRecordService attendanceRecordService;
    private final JwtUtils jwtUtils;

    public StudentController(CourseService courseService, CourseOfferingService courseOfferingService,
                             CourseRegistrationService courseRegistrationService, AttendanceRecordService attendanceRecordService,
                             JwtUtils jwtUtils) {
        this.courseService = courseService;
        this.courseOfferingService = courseOfferingService;
        this.courseRegistrationService = courseRegistrationService;
        this.attendanceRecordService = attendanceRecordService;
        this.jwtUtils = jwtUtils;
    }

    private String getStuIDFromJwtToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            return jwtUtils.getStudentIdFromJwt(token);
        } else {
            return null;
        }
    }

    @GetMapping("/course-offerings/")
    public ResponseEntity<?> getCourseOffering(HttpServletRequest request) {
        String studentIdFromJwt = getStuIDFromJwtToken(request);
        List<CourseRegistration> courseRegistrations = courseRegistrationService.getRegisteredCourses(studentIdFromJwt);
        if (courseRegistrations.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No course registrations found for student ID: " + studentIdFromJwt);
        }
        return ResponseEntity.ok(courseRegistrations);
    }

    @GetMapping("/course-offerings/{offeringId}/attendance")
    public ResponseEntity<?> getAttendance(HttpServletRequest request, @PathVariable("offeringId") Integer offeringId) {
        String studentIdFromJwt = getStuIDFromJwtToken(request);
        CourseOffering courseOffering = courseService.getCourseOfferingById(offeringId);
        LocalDateTime today = LocalDateTime.now();
        if (courseOffering.getStartDate().isAfter(ChronoLocalDate.from(today))) {
            return ResponseEntity.badRequest().body("The course has not started yet.");
        }

        List<CourseService.SessionWithAttendance> sessionWithAttendanceList = courseService.getSessionsWithAttendance(studentIdFromJwt, offeringId);
        return ResponseEntity.ok(sessionWithAttendanceList);
    }

    @GetMapping("/student-view/attendance-records")
    public ResponseEntity<?> getAllAttendanceRecord(HttpServletRequest request) {
        String studentIdFromJwt = getStuIDFromJwtToken(request);
        if (studentIdFromJwt == null || studentIdFromJwt.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid student ID");
        }

        List<AttendanceRecordDTO> recordList = attendanceRecordService.getAttendaceRecords(studentIdFromJwt);
        if (recordList == null || recordList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No attendance records found for student ID: " + studentIdFromJwt);
        }

        return ResponseEntity.ok(recordList);
    }

    @GetMapping("/course-offerings/{offeringId}")
    public ResponseEntity<?> courseInformation(@PathVariable("offeringId") Integer offeringId) {
        CourseOffering courseOffering = courseOfferingService.getCourseOfferingsbyId(offeringId);
        return ResponseEntity.ok(courseOffering);
    }
}