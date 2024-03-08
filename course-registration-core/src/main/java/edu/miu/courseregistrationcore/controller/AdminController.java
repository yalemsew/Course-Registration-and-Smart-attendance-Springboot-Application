package edu.miu.courseregistrationcore.controller;

import edu.miu.courseregistrationcore.domain.CourseOffering;
import edu.miu.courseregistrationcore.domain.CourseRegistration;
import edu.miu.courseregistrationcore.dto.StudentToAttendanceDTO;
import edu.miu.courseregistrationcore.integration.DateFormatter;
import edu.miu.courseregistrationcore.repository.CourseOfferingRepository;
import edu.miu.courseregistrationcore.service.AttendanceRecordService;
import edu.miu.courseregistrationcore.service.CourseOfferingService;
import edu.miu.courseregistrationcore.service.CourseRegistrationService;
import edu.miu.courseregistrationcore.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin-view")
@PreAuthorize("hasAnyRole('SYS_ADMIN', 'STAFF', 'FACULTY')")
public class AdminController {
    @Autowired
    private CourseOfferingService courseOfferingService;
    @Autowired
    private CourseOfferingRepository courseOfferingRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private AttendanceRecordService attendanceRecordService;
    @Autowired
    private CourseRegistrationService courseRegistrationService;

    @GetMapping("/course-offerings/{date}")
    public ResponseEntity<List<CourseOffering>> getCourseOfferings(@PathVariable String date) {
        try {
            LocalDate localDate = DateFormatter.parseDate(date);
            List<CourseOffering> courseOfferings = courseOfferingService.getCourseOfferingByDateBetween(localDate);
            return new ResponseEntity<>(courseOfferings, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/course-offerings/{offeringId}")
    public ResponseEntity<CourseOffering> getCourseOffering(@PathVariable int offeringId) {
        CourseOffering courseOfferingById = courseOfferingService.getCourseOfferingsbyId(offeringId);
        return new ResponseEntity<>(courseOfferingById, HttpStatus.OK);
    }

    @GetMapping("/students/{studentID}")
    public ResponseEntity<List<CourseRegistration>> getStudentAndCoursesInfo(@PathVariable String studentID) {
        List<CourseRegistration> courseRegistration = studentService.getCourseRegistrationsByStudentID(studentID);
        return new ResponseEntity<>(courseRegistration, HttpStatus.OK);
    }

    @GetMapping("/course-offerings/{offeringId}/attendance")
    public ResponseEntity<?> getAttendanceByCourseOfferingId(@PathVariable Integer offeringId) throws IOException {
        List<StudentToAttendanceDTO> studentToAttendanceDTOList = courseRegistrationService.getStudentsAttendanceByCourseOfferingId(offeringId);
        courseRegistrationService.writeStudentsAttendanceToExcel(offeringId);
        return new ResponseEntity<>(studentToAttendanceDTOList, HttpStatus.OK);
    }

    @PostMapping("/course-offerings")
    public ResponseEntity<CourseOffering> createCourseOffering(@RequestBody CourseOffering courseOffering) {
        CourseOffering created = courseOfferingService.createCourseOffering(courseOffering);
        return new ResponseEntity<>(created, HttpStatus.OK);

    }

    @PutMapping("/course-offerings/{offeringId}")
    public ResponseEntity<CourseOffering> updatedCourseOffering(@PathVariable int offeringId, @RequestBody CourseOffering courseOffering) {
        CourseOffering updated = courseOfferingService.updateCourseOfferingById(offeringId, courseOffering);
        return new ResponseEntity<>(updated, HttpStatus.OK);

    }

    @DeleteMapping("/course-offerings/{offeringId}")
    public ResponseEntity<String> deleteCouseOffering(@PathVariable int offeringId) {
        courseOfferingService.deleteCourseOfferingById(offeringId);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }

}
