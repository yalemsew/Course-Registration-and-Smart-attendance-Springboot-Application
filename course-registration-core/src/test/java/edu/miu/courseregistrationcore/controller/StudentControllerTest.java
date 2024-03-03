package edu.miu.courseregistrationcore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.courseregistrationcore.domain.*;
import edu.miu.courseregistrationcore.repository.CourseRegistrationRepository;
import edu.miu.courseregistrationcore.service.*;
import edu.miu.courseregistrationcore.service.dto.AttendanceRecordDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(StudentController.class)
@AutoConfigureDataJpa
public class StudentControllerTest {
    private final String URL = "/admin-view";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CourseOfferingService courseOfferingService;

    @MockBean
    private CourseService courseService;

    @MockBean
    private StudentService studentService;

    @MockBean
    private List<CourseOffering> courseOfferings;

    @MockBean
    private ILocationService locationService;

    @MockBean
    private AttendanceRecordService attendanceRecordService;
    @MockBean
    private CourseRegistrationRepository courseRegistrationRepository;

    private Student student1;
    private Student student2;

    private List<Student> students;

    private Course course1;
    private Course course2;

    private List<Course> courses;

    private List<Location> locations;
    private Location location1;
    private Location location2;

    private AttendanceRecord attendanceRecord1;
    private AttendanceRecord attendanceRecord2;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private CourseRegistrationService courseRegistrationService;

    @BeforeEach
    public void setup() {
        course1 = new Course();
        course1.setCourseCode("CS544");
        course1.setCourseName("EA");
        Mockito.when(courseService.getCourseByCourseCode("CS544")).thenReturn(course1);

        student1 = new Student();
        student1.setStudentID("1001");
        student1.setFirstName("John");
        student1.setLastName("Doe");

        student2 = new Student();
        student2.setStudentID("1002");
        student2.setFirstName("Jane");
        student2.setLastName("Doe");

        location1 = new Location();
        location1.setId(1L);
        location1.setName("Location1");

        location2 = new Location();
        location2.setId(2L);
        location2.setName("Location2");

        course1 = new Course();
        course1.setCourseCode("CS544");
        course1.setCourseName("EA");

        course2 = new Course();
        course2.setCourseCode("CS545");
        course2.setCourseName("WAA");

        attendanceRecord1 = new AttendanceRecord();
        attendanceRecord1.setStudent(student1);
        attendanceRecord1.setLocation(location1);
        attendanceRecord1.setScanDateTime(LocalDateTime.of(LocalDate.of(2024, 10, 12), LocalTime.of(10, 5, 0)));

        attendanceRecord2 = new AttendanceRecord();
        attendanceRecord2.setStudent(student1);
        attendanceRecord2.setLocation(location1);
        attendanceRecord2.setScanDateTime(LocalDateTime.of(LocalDate.of(2024, 10, 11), LocalTime.of(10, 5, 0)));


//        CourseRegistration courseRegistration1 = new CourseRegistration();
//        courseRegistration1.setCourseOffering(offering1);
//        courseRegistration1.setStudent(student1);
//        courseRegistration1.setGrade(Grade.A);
//        courseRegistrationRepository.save(courseRegistration1);
//
//        CourseRegistration courseRegistration2 = new CourseRegistration();
//        courseRegistration2.setCourseOffering(offering1);
//        courseRegistration2.setStudent(student1);
//        courseRegistration2.setGrade(Grade.B);
//        courseRegistrationRepository.save(courseRegistration1);

        students = Arrays.asList(student1, student2);
        when(studentService.getAllStudents()).thenReturn(students);
        when(studentService.getStudentByStudentID("1001")).thenReturn(student1);
        when(studentService.getStudentByStudentID("1002")).thenReturn(student2);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testgetallattendanceRecord() throws Exception {
        String studentId = "1001";
        // Convert attendance records to DTOs
        AttendanceRecordDTO attendanceRecordDTO1 = new AttendanceRecordDTO();
        attendanceRecordDTO1.setScanDateTime(attendanceRecord1.getScanDateTime());
        Location location1= new Location();
        location1.setName("Location1");
        attendanceRecordDTO1.setLocationName(location1.getName());
        attendanceRecordDTO1.setStudentName(student1.getFirstName() + " " + student1.getLastName());

        AttendanceRecordDTO attendanceRecordDTO2 = new AttendanceRecordDTO();
        attendanceRecordDTO2.setScanDateTime(attendanceRecord2.getScanDateTime());
        attendanceRecordDTO2.setLocationName(location1.getName());
        attendanceRecordDTO2.setStudentName(student1.getFirstName() + " " + student1.getLastName());

        List<AttendanceRecordDTO> recordDTOS = Arrays.asList(attendanceRecordDTO1, attendanceRecordDTO2);

        Mockito.when(attendanceRecordService.getAttendaceRecords("1001"))
                .thenReturn(recordDTOS);

        mockMvc.perform(get("/student-view/attendance-records/{studentId}",studentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].scanDateTime").value(attendanceRecordDTO1.getScanDateTime().toString()))
                .andExpect(jsonPath("$[0].locationName").value(attendanceRecordDTO1.getLocationName()))
                .andExpect(jsonPath("$[0].locationType").value(attendanceRecordDTO1.getLocationType()))
                .andExpect(jsonPath("$[0].studentName").value(attendanceRecordDTO1.getStudentName()))
                .andExpect(jsonPath("$[1].scanDateTime").value(attendanceRecordDTO2.getScanDateTime().toString()))
                .andExpect(jsonPath("$[1].locationName").value(attendanceRecordDTO2.getLocationName()))
                .andExpect(jsonPath("$[1].locationType").value(attendanceRecordDTO2.getLocationType()))
                .andExpect(jsonPath("$[1].studentName").value(attendanceRecordDTO2.getStudentName()));
    }


    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testGetAttendance() throws Exception {
        // Arrange
        String studentId = "STU001";
        Integer offeringId = 1;

        CourseOffering mockCourseOffering = new CourseOffering();
        mockCourseOffering.setStartDate(LocalDate.now().minusDays(1)); // Set start date to yesterday

        List<CourseService.SessionWithAttendance> mockSessionWithAttendanceList = new ArrayList<>();
        // Add sessions to the list...

        when(courseService.getCourseOfferingById(offeringId)).thenReturn(mockCourseOffering);
        when(courseService.getSessionsWithAttendance(studentId, offeringId)).thenReturn(mockSessionWithAttendanceList);

        // Act and Assert
        mockMvc.perform(get("/student-view/{studentId}/{offeringId}/attendance", studentId, offeringId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(mockSessionWithAttendanceList)));
    }
    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testGetCourseOffering() throws Exception {
        // Arrange
        String studentId = "STU001";

        CourseRegistration courseRegistration1 = new CourseRegistration();

        CourseRegistration courseRegistration2 = new CourseRegistration();
        // set properties of courseRegistration2

        List<CourseRegistration> courseRegistrations = Arrays.asList(courseRegistration1, courseRegistration2);

        Mockito.when(courseRegistrationService.getRegisteredCourses(studentId))
                .thenReturn(courseRegistrations);

        // Act and Assert
        mockMvc.perform(get("/student-view/course-offerings/{studentId}", studentId))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(courseRegistrations)));
    }
}
