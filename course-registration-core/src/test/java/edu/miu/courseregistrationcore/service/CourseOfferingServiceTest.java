package edu.miu.courseregistrationcore.service;


import edu.miu.courseregistrationcore.CourseRegistrationCoreApplication;
import edu.miu.courseregistrationcore.domain.CourseOffering;
import edu.miu.courseregistrationcore.repository.CourseOfferingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CourseRegistrationCoreApplication.class)
public class CourseOfferingServiceTest {
    @Mock
    private CourseOfferingRepository courseOfferingRepository;

    @InjectMocks
    private CourseOfferingService courseOfferingService;

    private CourseOffering courseOffering1;
    private CourseOffering courseOffering2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        courseOffering1 = new CourseOffering();
        courseOffering1.setStartDate(LocalDate.of(2023, 1, 1));
        courseOffering1.setEndDate(LocalDate.of(2023, 6, 1));

        courseOffering2 = new CourseOffering();
        courseOffering2.setStartDate(LocalDate.of(2023, 7, 1));
        courseOffering2.setEndDate(LocalDate.of(2023, 12, 1));
    }

    @Test
    public void testGetCourseOfferingByDateBetween() {
        LocalDate testDate1 = LocalDate.of(2023, 3, 1);
        when(courseOfferingRepository.findByDateBetween(testDate1)).thenReturn(Collections.singletonList(courseOffering1));

        List<CourseOffering> result = courseOfferingService.getCourseOfferingByDateBetween(testDate1);
        assertThat(result).hasSize(1);
        assertThat(result).contains(courseOffering1);

        LocalDate testDate2 = LocalDate.of(2023, 8, 1);
        when(courseOfferingRepository.findByDateBetween(testDate2)).thenReturn(Collections.singletonList(courseOffering2));

        List<CourseOffering> result2 = courseOfferingService.getCourseOfferingByDateBetween(testDate2);
        assertThat(result2).hasSize(1);
        assertThat(result2).contains(courseOffering2);

        LocalDate testDate3 = LocalDate.of(2024, 1, 1);
        when(courseOfferingRepository.findByDateBetween(testDate3)).thenReturn(List.of());

        List<CourseOffering> result3 = courseOfferingService.getCourseOfferingByDateBetween(testDate3);
        assertThat(result3).isEmpty();
    }

//    @Test
//    public void testGetCourseOfferingById() {
//        CourseOffering courseOffering = new CourseOffering();
//        courseOffering.setId(1);
//        courseOffering.setCapacity(123);
//
//        when(courseOfferingRepository.getCourseOfferingById(anyInt())).thenReturn(Optional.of(courseOffering));
//
//        Optional<CourseOffering> result = courseOfferingService.getCourseOfferingById(1);
//
//        assertTrue(result.isPresent());
//        assertEquals(123, result.get().getCapacity());
//    }

}
