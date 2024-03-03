package edu.miu.courseregistrationcore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.courseregistrationcore.domain.Course;
import edu.miu.courseregistrationcore.domain.CourseOffering;
import edu.miu.courseregistrationcore.domain.Location;
import edu.miu.courseregistrationcore.domain.Student;
import edu.miu.courseregistrationcore.service.CourseOfferingService;
import edu.miu.courseregistrationcore.service.CourseService;
import edu.miu.courseregistrationcore.service.ILocationService;
import edu.miu.courseregistrationcore.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc(addFilters = false)

@WebMvcTest(AdminController.class)
@AutoConfigureDataJpa
public class AdminControllerTest {
    Course course;
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

    private Student student1;
    private Student student2;

    List<Student> students;

    private Course course1;
    private Course course2;

    private List<Course> courses;

    private List<Location> locations;
    private Location location1;
    private Location location2;

    @BeforeEach
    public void setup() {
        CourseOffering courseOffering1 = new CourseOffering();
        courseOffering1.setCourse(new Course());
        courseOffering1.setRoom("Room1");
        courseOffering1.setAuditFields("Instructor 1");
        courseOffering1.setStartDate(LocalDate.of(2024, 6, 15));
        courseOffering1.setEndDate(LocalDate.of(2024, 12, 15));

        CourseOffering courseOffering2 = new CourseOffering();
        courseOffering2.setCourse(new Course());
        courseOffering2.setRoom("Room2");
        courseOffering2.setAuditFields("Instructor 2");
        courseOffering2.setStartDate(LocalDate.of(2024, 6, 15));
        courseOffering2.setEndDate(LocalDate.of(2024, 12, 15));

        courseOfferings = Arrays.asList(courseOffering1, courseOffering2);

        course1 = new Course();
        course1.setCourseCode("CS544");
        course1.setCourseName("EA");

        course2 = new Course();
        course2.setCourseCode("CS545");
        course2.setCourseName("WAA");

        students = Arrays.asList(student1, student2);
        when(studentService.getAllStudents()).thenReturn(students);
        when(studentService.getStudentByStudentID("1001")).thenReturn(student1);
        when(studentService.getStudentByStudentID("1002")).thenReturn(student2);


    }


    @Test
    public void testGetCourseOfferingsSuccess() throws Exception {
        when(courseOfferingService.getCourseOfferingByDateBetween(any(LocalDate.class))).thenReturn(courseOfferings);

        mockMvc.perform(get("/course-offerings")
                        .param("date", "2024-06-15")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'courseName':'Course 1','instructor':'Instructor 1','date':'2024-06-15'},{'courseName':'Course 2','instructor':'Instructor 2','date':'2024-06-15'}]"));
    }

    @Test
    public void testGetCourseOfferingsInvalidDate() throws Exception {
        mockMvc.perform(get("/course-offerings")
                        .param("date", "invalid-date")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testGetCourseOfferingById() throws Exception {
        mockMvc.perform(get("/admin-view/course-offerings/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseCode").value("CS544"));
    }
}
