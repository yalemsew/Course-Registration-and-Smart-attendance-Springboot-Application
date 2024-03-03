package edu.miu.courseregistrationcore.repository;

import edu.miu.courseregistrationcore.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    public Course findByCourseCode(String courseCode);

    public List<Course> findByCourseName(String courseName);

    public void deleteCourseByCourseCode(String courseCode);

}
