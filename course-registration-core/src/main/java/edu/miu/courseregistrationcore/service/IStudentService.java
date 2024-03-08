package edu.miu.courseregistrationcore.service;

import edu.miu.courseregistrationcore.domain.CourseRegistration;
import edu.miu.courseregistrationcore.domain.Student;

import java.util.List;

public interface IStudentService {
    Student getStudentByStudentID(String studentID);

    List<Student> getAllStudents();

    Student updateStudentById(String id, Student student);

    Student addStudent(Student student);

    void deleteStudentById(String id);

    List<CourseRegistration> getCourseRegistrationsByStudentID(String studentID);
}
