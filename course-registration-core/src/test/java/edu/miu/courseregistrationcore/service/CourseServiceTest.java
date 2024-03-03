package edu.miu.courseregistrationcore.service;


import edu.miu.courseregistrationcore.CourseRegistrationCoreApplication;
import edu.miu.courseregistrationcore.domain.Course;
import edu.miu.courseregistrationcore.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CourseRegistrationCoreApplication.class)
public class CourseServiceTest {
    @Autowired
    CourseService courseService;

    @MockBean
    CourseRepository courseRepository;

    @BeforeEach
    public void setUp() {
        String courseCode = "CS544";
        Course course = new Course();
        course.setCourseName("EA");
        course.setCourseCode(courseCode);
        Mockito.when(courseRepository.findByCourseCode(courseCode)).thenReturn(course);
    }

    @Test
    public void getCourseTest() {
        String courseCode = "CS544";
        Course courseResponse = courseService.getCourseByCourseCode(courseCode);

        assertThat(courseResponse.getCourseCode()).isEqualTo(courseCode);
    }

    @Test
    public void addCourseTest() {
        // Arrange
        Course course = new Course();
        course.setCourseName("EA");
        course.setCourseCode("CS544");

        // Act
        courseService.addCourse(course);

        // Assert
        Mockito.verify(courseRepository).save(course);
    }

    @Test
    public void updateCourseTest() {
        // Arrange
        String courseCode = "CS544";
        Course initialCourse = new Course();
        initialCourse.setCourseName("EA");
        initialCourse.setCourseCode(courseCode);
        Mockito.when(courseRepository.findByCourseCode(courseCode)).thenReturn(initialCourse);

        Course updatedCourse = new Course();
        updatedCourse.setCourseName("EA");
        updatedCourse.setCourseCode(courseCode);

        // Act
        courseService.updateCourseByCourseCode(courseCode, updatedCourse);

        // Assert
        Mockito.verify(courseRepository).save(initialCourse);
    }

    @Test
    public void deleteCourseTest() {
        // Arrange
        String courseCode = "CS544";
        Course course = new Course();
        course.setCourseName("EA");
        course.setCourseCode(courseCode);
        Mockito.when(courseRepository.findByCourseCode(courseCode)).thenReturn(course);

        // Act
        courseService.deleteCourseByCourseCode(courseCode);

        // Assert
        Mockito.verify(courseRepository).deleteCourseByCourseCode(courseCode);
    }

    @Test
    public void getAllCoursesTest() {
        // Arrange
        Course course1 = new Course();
        course1.setCourseName("EA");
        course1.setCourseCode("CS544");

        Course course2 = new Course();
        course2.setCourseName("EA");
        course2.setCourseCode("CS544");

        Mockito.when(courseRepository.findAll()).thenReturn(List.of(course1, course2));

        // Act
        List<Course> courses = courseService.getAllCourses();

        // Assert
        assertThat(courses.size()).isEqualTo(2);
    }

}
