package edu.miu.courseregistrationcore.service;


import edu.miu.courseregistrationcore.CourseRegistrationCoreApplication;
import edu.miu.courseregistrationcore.domain.Student;
import edu.miu.courseregistrationcore.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CourseRegistrationCoreApplication.class)

public class StudentServiceTest {

    @Autowired
    StudentService studentService;
    @MockBean
    StudentRepository studentRepository;


    @BeforeEach
    public void setUp() {
        String studentID = "34546";
        Student student = new Student();
        student.setEntry("Entry1");
        student.setAlternateID("AltID1");
        student.setApplicantID("AppID1");
        student.setStudentID(studentID);
        Mockito.when(studentRepository.findByStudentID(studentID)).thenReturn(Optional.of(student));
    }

    @Test
    public void getStudentTest() {
        String studentID = "34546";
        Student studentResponse = studentService.getStudentByStudentID(studentID);

        assertThat(studentResponse.getStudentID()).isEqualTo(studentID);
    }

    @Test
    public void updateStudentTest() {
        // Arrange
        String studentID = "34546";
        Student initialStudent = new Student();
        initialStudent.setEntry("Entry1");
        initialStudent.setAlternateID("AltID1");
        initialStudent.setApplicantID("AppID1");
        initialStudent.setStudentID(studentID);
        Mockito.when(studentRepository.findByStudentID(studentID)).thenReturn(Optional.of(initialStudent));

        Student updatedStudent = new Student();
        updatedStudent.setFirstName("UpdatedFirstName");
        updatedStudent.setLastName("UpdatedLastName");
        updatedStudent.setStudentID(studentID);

        // Act
        studentService.updateStudentById(studentID, updatedStudent);

        // Assert
        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        Mockito.verify(studentRepository).save(studentCaptor.capture());
        Student savedStudent = studentCaptor.getValue();

        assertThat(savedStudent.getFirstName()).isEqualTo(updatedStudent.getFirstName());
        assertThat(savedStudent.getLastName()).isEqualTo(updatedStudent.getLastName());
    }


}
