package edu.miu.courseregistrationemail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableKafka
@EnableScheduling
public class CourseRegistrationEmailApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseRegistrationEmailApplication.class, args);
    }

}
