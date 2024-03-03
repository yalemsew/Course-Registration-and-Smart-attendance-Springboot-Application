package edu.miu.courseregistrationcore;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableKafka
@EnableScheduling
@Slf4j
public class CourseRegistrationCoreApplication {

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(CourseRegistrationCoreApplication.class, args);
    }

    @PostConstruct
    public void printKafkaConfig() {
        log.info("Kafka Bootstrap Servers: " + env.getProperty("spring.kafka.bootstrap-servers"));
    }

}
