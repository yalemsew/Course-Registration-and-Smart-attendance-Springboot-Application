package edu.miu.courseregistrationcore.controller;

import edu.miu.courseregistrationcore.domain.Course;
import edu.miu.courseregistrationcore.domain.CourseOffering;
import edu.miu.courseregistrationcore.domain.Location;
import edu.miu.courseregistrationcore.domain.Student;
import edu.miu.courseregistrationcore.service.ICourseService;
import edu.miu.courseregistrationcore.service.ILocationService;
import edu.miu.courseregistrationcore.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sys-admin")
@PreAuthorize("hasAnyRole('SYS_ADMIN')")
public class SystemAdminController {
    @Autowired
    private IStudentService studentService;
    //    @Autowired
//    private ICourseService courseService;
//    @Autowired
//    private ICourseOfferingService courseOfferingService;
    @Autowired
    private ILocationService locationService;
    @Autowired
    private ICourseService courseService;


    // Student api
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents() {
        ResponseEntity<List<Student>> responseEntity = null;
        List<Student> students = studentService.getAllStudents();
        if (students != null) {
            responseEntity = ResponseEntity.ok(students);
        } else {
            responseEntity = ResponseEntity.notFound().build();
        }
        return responseEntity;
    }

    @GetMapping("/students/{StudentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable("StudentId") String studentId) {
        ResponseEntity<Student> responseEntity = null;
        Optional<Student> student = Optional.ofNullable(studentService.getStudentByStudentID(studentId));
        responseEntity = student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        return responseEntity;
    }

    @PostMapping("/students")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        ResponseEntity<Student> responseEntity = null;
        Student addedStudent = studentService.addStudent(student);
        responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(addedStudent);
        return responseEntity;
    }

    @DeleteMapping("/students/{StudentId}")
    public ResponseEntity<?> deleteStudentById(@PathVariable("StudentId") String studentId) {
        ResponseEntity<String> responseEntity = null;
        studentService.deleteStudentById(studentId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/students/{StudentId}")
    public ResponseEntity<Student> updateStudentById(@PathVariable("StudentId") String studentId, @RequestBody Student student) {
        ResponseEntity<Student> responseEntity = null;
        studentService.updateStudentById(studentId, student);
        responseEntity = ResponseEntity.ok(student);
        return responseEntity;
    }

    // Course api
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses() {
        ResponseEntity<List<Course>> responseEntity = null;
        List<Course> courses = courseService.getAllCourses();
        if (courses != null) {
            responseEntity = ResponseEntity.ok(courses);
        } else {
            responseEntity = ResponseEntity.notFound().build();
        }
        return responseEntity;
    }

    @GetMapping("/courses/{CourseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable("CourseId") String courseId) {
        ResponseEntity<Course> responseEntity = null;
        Optional<Course> course = Optional.ofNullable(courseService.getCourseByCourseCode(courseId));
        responseEntity = course.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        return responseEntity;

    }

    @PostMapping("/courses")
    public ResponseEntity<?> addCourse(@RequestBody Course course) {
        ResponseEntity<Course> responseEntity = null;
        Course addedCourse = courseService.addCourse(course);
        responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(addedCourse);
        return responseEntity;

    }

    @DeleteMapping("/courses/{CourseId}")
    public ResponseEntity<?> deleteCourseById(@PathVariable("CourseId") String courseId) {
        ResponseEntity<String> responseEntity = null;
        courseService.deleteCourseByCourseCode(courseId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/courses/{CourseId}")
    public ResponseEntity<?> updateCourseById(@PathVariable("CourseId") String courseId, Course course) {
        ResponseEntity<Course> responseEntity = null;
        Course updatedCourse = courseService.updateCourseByCourseCode(courseId, course);
        responseEntity = ResponseEntity.ok(updatedCourse);
        return responseEntity;

    }

    // CourseOffering api
    @GetMapping("/course-offerings")
    public ResponseEntity<List<CourseOffering>> getAllCourseOfferings() {
        return null;
    }

    @GetMapping("/course-offerings/{CourseOfferingId}")
    public ResponseEntity<CourseOffering> getCourseOfferingById(@PathVariable("CourseOfferingId") Long courseOfferingId) {
        return null;
    }

    @PostMapping("/course-offerings")
    public ResponseEntity<CourseOffering> addCourseOffering(CourseOffering courseOffering) {
        return null;
    }

    @DeleteMapping("/course-offerings/{CourseOfferingId}")
    public ResponseEntity<String> deleteCourseOfferingById(@PathVariable("CourseOfferingId") Long courseOfferingId) {
        return null;
    }

    @PutMapping("/course-offerings/{CourseOfferingId}")
    public ResponseEntity<CourseOffering> updateCourseOfferingById(@PathVariable("CourseOfferingId") Long courseOfferingId, CourseOffering courseOffering) {
        return null;
    }
    //Location api
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> allLocations = locationService.getAllLocations();
        return ResponseEntity.ok(allLocations);
    }

    @GetMapping("/locations/{LocationId}")
    public ResponseEntity<Location> getLocationById(@PathVariable("LocationId") Long locationId) {
        Location locationById = locationService.getLocationById(locationId);
        return ResponseEntity.ok(locationById);
    }

    @PostMapping("/locations")
    public ResponseEntity<Location> addLocation(Location location) {
        Location addedLocation = locationService.addLocation(location);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedLocation);
    }

    @DeleteMapping("/locations/{LocationId}")
    public ResponseEntity<Void> deleteLocationById(@PathVariable("LocationId") Long locationId) {
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/locations/{LocationId}")
    public ResponseEntity<Location> updateLocationById(@PathVariable("LocationId") Long locationId, Location location) {
        Location updatedLocation = locationService.updateLocation(locationId, location);
        return ResponseEntity.ok(updatedLocation);
    }
}
