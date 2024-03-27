package edu.miu.courseregistrationbarcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class CourseRegistrationBarcodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseRegistrationBarcodeApplication.class, args);
    }

}
