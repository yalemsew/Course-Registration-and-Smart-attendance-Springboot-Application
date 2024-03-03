package edu.miu.courseregistrationcore.repository;

import edu.miu.courseregistrationcore.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    //find student by student id
    Optional<Student> findByStudentID(String studentId);

    void deleteStudentByStudentID(String id);
}
