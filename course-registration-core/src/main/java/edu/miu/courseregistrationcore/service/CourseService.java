package edu.miu.courseregistrationcore.service;

import edu.miu.courseregistrationcore.domain.*;
import edu.miu.courseregistrationcore.repository.AttendanceRecordRepository;
import edu.miu.courseregistrationcore.repository.CourseOfferingRepository;
import edu.miu.courseregistrationcore.repository.CourseRepository;
import edu.miu.courseregistrationcore.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import edu.miu.courseregistrationcore.integration.exception.CourseOfferingNotFoundException;
import edu.miu.courseregistrationcore.integration.exception.StudentNotFoundException;

@Service
@Transactional
public class CourseService implements ICourseService{
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Course getCourseByCourseCode(String courseCode) {
        return courseRepository.findByCourseCode(courseCode);
    }
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }
    public void deleteCourseByCourseCode(String courseCode) {
        courseRepository.deleteCourseByCourseCode(courseCode);
    }
    public Course updateCourseByCourseCode(String courseCode, Course updatedCourse) {
        Course existingCourse = courseRepository.findByCourseCode(courseCode);
        if (updatedCourse.getCourseName() != null) {
            existingCourse.setCourseName(updatedCourse.getCourseName());
        }
        if(updatedCourse.getCourseCode() != null){
            existingCourse.setCourseCode(updatedCourse.getCourseCode());
        }
        courseRepository.save(existingCourse);
        return existingCourse;
    }

    public CourseOffering getCourseOfferingById(Integer offeringId) {
        return courseOfferingRepository.findById(offeringId)
                .orElseThrow(() -> new CourseOfferingNotFoundException("CourseOffering not found with ID: " + offeringId));
    }

    public List<SessionWithAttendance> getSessionsWithAttendance(String studentId, Integer courseOfferingId) {
        CourseOffering courseOffering = getCourseOfferingById(courseOfferingId);

        Student student = studentRepository.findByStudentID(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + studentId));

        List<Session> sessions = courseOffering.generateSessions();

        List<AttendanceRecord> attendanceRecords = attendanceRecordRepository
                .findByStudentIdAndScanDateTimeBetween(studentId, courseOffering.getStartDate().atStartOfDay(), courseOffering.getEndDate().atTime(23, 59, 59));

        return sessions.stream()
                .map(session -> new SessionWithAttendance(session, attendanceRecords.stream()
                        .filter(record -> record.getScanDateTime().equals(session.getDate()))
                        .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    public static class SessionWithAttendance {
        private Session session;
        private List<AttendanceRecord> attendanceRecords;

        public SessionWithAttendance(Session session, List<AttendanceRecord> attendanceRecords) {
            this.session = session;
            this.attendanceRecords = attendanceRecords;
        }

        // Getters and setters
        public Session getSession() {
            return session;
        }

        public void setSession(Session session) {
            this.session = session;
        }

        public List<AttendanceRecord> getAttendanceRecords() {
            return attendanceRecords;
        }

        public void setAttendanceRecords(List<AttendanceRecord> attendanceRecords) {
            this.attendanceRecords = attendanceRecords;
        }
    }
}
