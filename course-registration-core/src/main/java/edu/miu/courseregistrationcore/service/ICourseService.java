package edu.miu.courseregistrationcore.service;

import edu.miu.courseregistrationcore.domain.Course;

import java.util.List;

public interface ICourseService {
    Course getCourseByCourseCode(String courseCode);

    List<Course> getAllCourses();

    Course addCourse(Course course);

    void deleteCourseByCourseCode(String courseCode);

    Course updateCourseByCourseCode(String courseCode, Course updatedCourse);
}
