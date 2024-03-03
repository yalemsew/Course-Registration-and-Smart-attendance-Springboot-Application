package edu.miu.courseregistrationcore.service;

import edu.miu.courseregistrationcore.CourseRegistrationCoreApplication;
import edu.miu.courseregistrationcore.domain.CourseOffering;
import edu.miu.courseregistrationcore.domain.CourseRegistration;
import edu.miu.courseregistrationcore.domain.Student;
import edu.miu.courseregistrationcore.repository.CourseRegistrationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import java.nio.file.Files;
import java.nio.file.Paths;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = CourseRegistrationCoreApplication.class)
public class CourseRegistrationServiceTest {

    @MockBean
    CourseRegistrationRepository courseRegistrationRepository;
    @InjectMocks
    CourseRegistrationService courseRegistrationService;

    @MockBean
    private AttendanceRecordService attendanceRecordService;

    @InjectMocks
    CourseOfferingService courseOfferingService;

    private Student student1;
    private Student student2;
    private Student student3;
    List<Student> students;

    CourseOffering courseOffering1;


    @BeforeEach
    public void setUp() {
        CourseRegistration courseRegistration1 = new CourseRegistration();
        CourseRegistration courseRegistration2 = new CourseRegistration();
        CourseRegistration courseRegistration3 = new CourseRegistration();

        courseOffering1 = new CourseOffering();
        courseOffering1.setId(1);
        courseOffering1.setRoom("M105");

        courseRegistration1.setCourseOffering(courseOffering1);
        courseRegistration1.setId(1);
        courseRegistration2.setCourseOffering(courseOffering1);
        courseRegistration2.setId(2);
        courseRegistration3.setCourseOffering(courseOffering1);
        courseRegistration3.setId(3);

        student1 = new Student();
        student1.setStudentID("1001");
        student1.setFirstName("Alex");

        student2 = new Student();
        student2.setStudentID("1002");
        student2.setFirstName("John");

        student3 = new Student();
        student3.setStudentID("1003");
        student3.setFirstName("Doe");

        students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);


        courseRegistration1.setStudent(student1);
        courseRegistration2.setStudent(student2);
        courseRegistration3.setStudent(student3);

        when(courseRegistrationService.getDistinctStudentsByCourseOfferingId(1)).thenReturn(students);
            when(courseRegistrationRepository.findAllByCourseOfferingId(1)).thenReturn(List.of(courseRegistration1, courseRegistration2, courseRegistration3));

        when(attendanceRecordService.getAttendance(anyString())).thenReturn(new ArrayList<>());

    }

    @Test
    public void testGetDistinctStudentsByCourseOfferingId() {

        int courseOfferingId = 1;
        List<Student> studentList = courseRegistrationService.getDistinctStudentsByCourseOfferingId(courseOfferingId);
        assertThat(studentList).hasSize(3);
        assertThat(studentList).contains(student1, student2, student3);
    }

    @Test
    public void testGetCourseRegistrationsByCourseOfferingId() {
        int courseOfferingId = 1;
        List<CourseRegistration> courseRegistrations = courseRegistrationService.getCourseRegistrationsByCourseOfferingId(courseOfferingId);
        assertThat(courseRegistrations).hasSize(3);
    }

    @Test
    public void testWriteStudentsAttendanceToExcel() {
        int offeringId = 1;
        assertDoesNotThrow(() -> courseRegistrationService.writeStudentsAttendanceToExcel(offeringId));
        assertTrue(Files.exists(Paths.get("students_attendance.xlsx")));
    }
}
