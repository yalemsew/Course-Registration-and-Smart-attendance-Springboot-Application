package edu.miu.courseregistrationcore.repository;

import edu.miu.courseregistrationcore.CourseRegistrationCoreApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CourseRegistrationCoreApplication.class)
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    @Transactional
    public void test() {
        studentRepository.findAll().forEach(System.out::println);
    }
}
