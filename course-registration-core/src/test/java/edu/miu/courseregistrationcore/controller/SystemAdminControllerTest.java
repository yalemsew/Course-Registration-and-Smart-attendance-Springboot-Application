package edu.miu.courseregistrationcore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.courseregistrationcore.domain.*;
import edu.miu.courseregistrationcore.repository.RoleRepository;
import edu.miu.courseregistrationcore.service.CourseService;
import edu.miu.courseregistrationcore.service.ILocationService;
import edu.miu.courseregistrationcore.service.IStudentService;
import edu.miu.courseregistrationcore.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc(addFilters = false)

@WebMvcTest(SystemAdminController.class)
@AutoConfigureDataJpa
public class SystemAdminControllerTest {

    private final String URL = "/sys-admin";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;
    @MockBean
    private IStudentService studentService;
    @MockBean
    private ILocationService locationService;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    RoleRepository roleRepository;

    @MockBean
    JwtService jwtService;

    private Role stu;


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
//        loadRoles();
//        stu = roleRepository.findByName(Roles.STUDENT).orElse(null);
//        when(roleRepository.findByName(Roles.STUDENT)).thenReturn(Optional.of(stu));

        stu = new Role();
        stu.setName(Roles.STUDENT);

        // Define the behavior of roleRepository.findByName(Roles.STUDENT)
        when(roleRepository.findByName(Roles.STUDENT)).thenReturn(Optional.of(stu));


        student1 = new Student();
        student1.setRole(stu);
        student1.setStudentID("1001");
        student1.setFirstName("John");
        student1.setLastName("Doe");

        student2 = new Student();
        student2.setRole(stu);
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

        students = Arrays.asList(student1, student2);

        when(studentService.getAllStudents()).thenReturn(students);
        when(studentService.getStudentByStudentID("1001")).thenReturn(student1);
        when(studentService.getStudentByStudentID("1002")).thenReturn(student2);

        courses = Arrays.asList(course1, course2);

        when(courseService.getAllCourses()).thenReturn(courses);

        Mockito.when(courseService.getCourseByCourseCode("CS544")).thenReturn(course1);

        Mockito.when(courseService.getCourseByCourseCode("CS545")).thenReturn(course2);


        locations = Arrays.asList(location1, location2);

        when(locationService.getAllLocations()).thenReturn(locations);
        when(locationService.getLocationById(1L)).thenReturn(location1);
    }

    @Test
    public void testGetAllStudents() throws Exception {
        mockMvc.perform(get(URL + "/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].studentID").value("1001"))
                .andExpect(jsonPath("$[1].studentID").value("1002"));
    }

    @Test
    public void testGetStudentById() throws Exception {
        mockMvc.perform(get(URL + "/students/1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentID").value("1001"));
    }

//    @Test
//    public void testAddStudent() throws Exception {
//        Student newStudent = new Student();
//        newStudent.setRole(stu);
//        newStudent.setStudentID("1003");
//        newStudent.setFirstName("Tom");
//        newStudent.setLastName("Jerry");
//
//        when(studentService.addStudent(any(Student.class))).thenReturn(newStudent);
//
//        mockMvc.perform(post(URL + "/students")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(newStudent)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.studentID").value("1003"));
//    }

    @Test
    public void testDeleteStudentById() throws Exception {
        mockMvc.perform(delete(URL + "/students/1001"))
                .andExpect(status().isNoContent());
    }

//    @Test
//    public void testUpdateStudentById() throws Exception {
//        Student updatedStudent = new Student();
//        updatedStudent.setStudentID("1001");
//        updatedStudent.setFirstName("John");
//        updatedStudent.setLastName("Doe");
//        when(studentService.updateStudentById(eq("1001"), any(Student.class))).thenReturn(updatedStudent);
//
//        mockMvc.perform(put(URL + "/students/1001")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updatedStudent)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.studentID").value("1001"));
//    }


    @Test
    public void testGetCourseById() throws Exception {
        mockMvc.perform(get("/sys-admin/courses/CS544"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseCode").value("CS544"));
    }

    @Test
    public void testGetAllCourses() throws Exception {
        mockMvc.perform(get(URL + "/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].courseCode").value("CS544"))
                .andExpect(jsonPath("$[1].courseCode").value("CS545"));
    }

    @Test
    public void testAddCourse() throws Exception {
        Course newCourse = new Course();
        newCourse.setCourseCode("CS546");
        newCourse.setCourseName("MPP");

        when(courseService.addCourse(any(Course.class))).thenReturn(newCourse);

        mockMvc.perform(post(URL + "/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCourse)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.courseCode").value("CS546"));
    }

    @Test
    public void testDeleteCourseById() throws Exception {
        mockMvc.perform(delete(URL + "/courses/CS544"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateCourseById() throws Exception {
        Course updatedCourse = new Course();
        updatedCourse.setCourseCode("CS544");
        updatedCourse.setCourseName("EA");

        when(courseService.updateCourseByCourseCode(eq("CS544"), any(Course.class))).thenReturn(updatedCourse);

        mockMvc.perform(put(URL + "/courses/CS544")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCourse)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseCode").value("CS544"));
    }


    @Test
    public void testGetAllLocations() throws Exception {
        mockMvc.perform(get(URL + "/locations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Location1"))
                .andExpect(jsonPath("$[1].name").value("Location2"));
    }

    @Test
    public void testGetLocationById() throws Exception {
        mockMvc.perform(get(URL + "/locations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Location1"));
    }

    @Test
    public void testAddLocation() throws Exception {
        Location newLocation = new Location();
        newLocation.setName("NewLocation");

        when(locationService.addLocation(any(Location.class))).thenReturn(newLocation);

        mockMvc.perform(post(URL + "/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newLocation)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("NewLocation"));
    }

    @Test
    public void testDeleteLocationById() throws Exception {
        mockMvc.perform(delete(URL + "/locations/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateLocationById() throws Exception {
        Location updatedLocation = new Location();
        updatedLocation.setName("UpdatedLocation");

        when(locationService.updateLocation(eq(1L), any(Location.class))).thenReturn(updatedLocation);

        mockMvc.perform(put(URL + "/locations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedLocation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UpdatedLocation"));
    }
}
