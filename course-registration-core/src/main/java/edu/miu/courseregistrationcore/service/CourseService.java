package edu.miu.courseregistrationcore.service;

import edu.miu.courseregistrationcore.domain.*;
import edu.miu.courseregistrationcore.integration.exception.CourseOfferingNotFoundException;
import edu.miu.courseregistrationcore.integration.exception.StudentNotFoundException;
import edu.miu.courseregistrationcore.repository.AttendanceRecordRepository;
import edu.miu.courseregistrationcore.repository.CourseOfferingRepository;
import edu.miu.courseregistrationcore.repository.CourseRepository;
import edu.miu.courseregistrationcore.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseService implements ICourseService {
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
        if (updatedCourse.getCourseCode() != null) {
            existingCourse.setCourseCode(updatedCourse.getCourseCode());
        }
        courseRepository.save(existingCourse);
        return existingCourse;
    }

    public CourseOffering getCourseOfferingById(Integer offeringId) {
        return courseOfferingRepository.findById(offeringId)
                .orElseThrow(() -> new CourseOfferingNotFoundException("CourseOffering not found with ID: " + offeringId));
    }




    public static class SessionWithAttendance {
        private Session session;
        private boolean isAttended;

        public SessionWithAttendance(Session session, boolean isAttended) {
            this.session = session;
            this.isAttended = isAttended;
            }
        public boolean getIsAttended() {
            return isAttended;
        }

        public Session getSession() {
            return session;
        }


    }
}
