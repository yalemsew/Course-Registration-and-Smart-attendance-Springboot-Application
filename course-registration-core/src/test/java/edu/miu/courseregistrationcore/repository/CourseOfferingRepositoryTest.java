package edu.miu.courseregistrationcore.repository;

import edu.miu.courseregistrationcore.CourseRegistrationCoreApplication;
import edu.miu.courseregistrationcore.domain.CourseOffering;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = CourseRegistrationCoreApplication.class)
public class CourseOfferingRepositoryTest {

    @Autowired
    private CourseOfferingRepository courseOfferingRepository;

    private CourseOffering courseOffering1;
    private CourseOffering courseOffering2;

    @BeforeEach
    public void setUp() {
        courseOffering1 = new CourseOffering();
        courseOffering1.setStartDate(LocalDate.of(2023, 1, 1));
        courseOffering1.setEndDate(LocalDate.of(2023, 6, 1));

        courseOffering2 = new CourseOffering();
        courseOffering2.setStartDate(LocalDate.of(2023, 7, 1));
        courseOffering2.setEndDate(LocalDate.of(2023, 12, 1));

        courseOfferingRepository.save(courseOffering1);
        courseOfferingRepository.save(courseOffering2);
    }

    @Test
    public void testFindByDateBetween() {
        LocalDate testDate1 = LocalDate.of(2023, 3, 1);
        List<CourseOffering> result1 = courseOfferingRepository.findByDateBetween(testDate1);
        assertThat(result1).hasSize(1);
        assertThat(result1).contains(courseOffering1);

        LocalDate testDate2 = LocalDate.of(2023, 8, 1);
        List<CourseOffering> result2 = courseOfferingRepository.findByDateBetween(testDate2);
        assertThat(result2).hasSize(1);
        assertThat(result2).contains(courseOffering2);

    }

    @Test
    public void testFindById() {
        Optional<CourseOffering> result = courseOfferingRepository.findById(Math.toIntExact(courseOffering1.getId()));
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getStartDate()).isEqualTo(courseOffering1.getStartDate());
        assertThat(result.get().getEndDate()).isEqualTo(courseOffering1.getEndDate());

    }
    @Test
    public void testGetCourseOfferingById() {
        CourseOffering result = courseOfferingRepository.getCourseOfferingById(Math.toIntExact(courseOffering1.getId()));
        assertThat(result.getStartDate()).isEqualTo(courseOffering1.getStartDate());
        assertThat(result.getEndDate()).isEqualTo(courseOffering1.getEndDate());
    }
}
