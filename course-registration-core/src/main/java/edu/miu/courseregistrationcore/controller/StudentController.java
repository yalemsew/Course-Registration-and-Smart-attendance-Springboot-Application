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
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseOfferingService courseOfferingService;

    @Autowired
    private CourseRegistrationService courseRegistrationService;
    @Autowired
    private AttendanceRecordService attendanceRecordService;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * GetStuIDFromJwtToken, only for Student(base on the role)
     * @param request
     * @return
     */
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
        try {

            String studentIdFromJwt = getStuIDFromJwtToken(request);
            List<CourseRegistration> courseRegistrations = courseRegistrationService.getRegisteredCourses(studentIdFromJwt);
            if (courseRegistrations.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No course registrations found for student ID: " + studentIdFromJwt);
            }
            return ResponseEntity.ok(courseRegistrations);
        } catch (Exception e) {
            e.printStackTrace(); // This will print the stack trace to the console for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }

    }

    @GetMapping("/course-offerings/{offeringId}/attendance")
    public ResponseEntity<?> getAttendance(HttpServletRequest request, @PathVariable("offeringId") Integer offeringId) {
        try {
            String studentIdFromJwt = getStuIDFromJwtToken(request);
            CourseOffering courseOffering = courseService.getCourseOfferingById(offeringId);
            LocalDateTime today = LocalDateTime.now();
            if (courseOffering.getStartDate().isAfter(ChronoLocalDate.from(today))) {
                return ResponseEntity.badRequest().body("The course has not started yet.");
            }

            List<CourseService.SessionWithAttendance> sessionWithAttendanceList = courseService.getSessionsWithAttendance(studentIdFromJwt, offeringId);
            return ResponseEntity.ok(sessionWithAttendanceList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/student-view/attendance-records")
    public ResponseEntity<?> getAllAttendanceRecord(HttpServletRequest request) {
        String studentIdFromJwt = getStuIDFromJwtToken(request);
        if (studentIdFromJwt == null || studentIdFromJwt.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid student ID");
        }

        try {
            List<AttendanceRecordDTO> recordList = attendanceRecordService.getAttendaceRecords(studentIdFromJwt);

            if (recordList == null || recordList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No attendance records found for student ID: " + studentIdFromJwt);
            }

            return ResponseEntity.ok(recordList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }


    @GetMapping("/course-offerings/{offeringId}")
    public ResponseEntity<?> courseInformation(@PathVariable("offeringId") Integer offeringId) {
        try {
            CourseOffering courseOffering = courseOfferingService.getCourseOfferingsbyId(offeringId);
            System.out.println(courseOffering);
            return ResponseEntity.ok(courseOffering);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }

}
