package edu.miu.courseregistrationcore.service;

import edu.miu.courseregistrationcore.domain.CourseRegistration;
import edu.miu.courseregistrationcore.domain.Student;
import edu.miu.courseregistrationcore.repository.CourseRegistrationRepository;
import edu.miu.courseregistrationcore.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentService implements IStudentService {

    private final StudentRepository studentRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;

    public StudentService(StudentRepository studentRepository, CourseRegistrationRepository courseRegistrationRepository) {
        this.studentRepository = studentRepository;
        this.courseRegistrationRepository = courseRegistrationRepository;
    }

    @Override
    public Student getStudentByStudentID(String studentID) {
        return studentRepository.findByStudentID(studentID)
                .orElseThrow(() -> new EntityNotFoundException("Student with ID: " + studentID + " not found"));
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudentById(String id) {
        studentRepository.deleteStudentByStudentID(id);
    }

    @Override
    public List<CourseRegistration> getCourseRegistrationsByStudentID(String studentID) {
        return courseRegistrationRepository.findByStudent_StudentID(studentID);
    }

    public Student updateStudentById(String id, Student updatedStudent) {
        Student existingStudent = studentRepository.findByStudentID(id)
                .orElseThrow(() -> new EntityNotFoundException("Student with ID: " + id + " not found"));

        if (updatedStudent.getFirstName() != null) {
            existingStudent.setFirstName(updatedStudent.getFirstName());
        }
        if (updatedStudent.getLastName() != null) {
            existingStudent.setLastName(updatedStudent.getLastName());
        }
        if (updatedStudent.getStudentID() != null) {
            existingStudent.setStudentID(updatedStudent.getStudentID());
        }

        return studentRepository.save(existingStudent);
    }
}