package edu.miu.courseregistrationcore.integration.job;

import edu.miu.courseregistrationcore.domain.CourseOffering;
import edu.miu.courseregistrationcore.domain.MailEvent;
import edu.miu.courseregistrationcore.domain.Student;
import edu.miu.courseregistrationcore.repository.CourseOfferingRepository;
import edu.miu.courseregistrationcore.repository.CourseRegistrationRepository;
import edu.miu.courseregistrationcore.repository.MailEventRepository;
import edu.miu.courseregistrationcore.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component
@Slf4j
public class CourseRegistrationReminderJob {
    @Autowired
    private CourseOfferingRepository courseOfferingRepository;
    @Autowired
    private CourseRegistrationRepository courseRegistrationRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MailEventRepository mailEventRepository;

    private Long getHoursDuration(int id) {
        CourseOffering courseOffering = courseOfferingRepository.getCourseOfferingById(id);
        LocalDateTime startDate = courseOffering.getStartDate().atStartOfDay();
        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Chicago"));
        Duration duration = Duration.between(now, startDate);
        return duration.toHours();
    }

    // Send a reminder, 8 and 4 hours prior to the end of the registration period
    @Scheduled(fixedRate = 1000 * 60 * 60)
    public void ReminderForCourseOfferingStart() {
        List<CourseOffering> courseOfferings = courseOfferingRepository.findAll();
        List<Student> students = studentRepository.findAll();

        // already registered student's ID
        List<String> registeredStudentIds = courseRegistrationRepository.findAll().stream()
                .map(courseRegistration -> courseRegistration.getStudent().getStudentID())
                .toList();

        for (CourseOffering courseOffering : courseOfferings) {
            long hours = getHoursDuration(courseOffering.getId());

            if (hours == 8 || hours == 4) {
                for (Student student : students) {
                    if (!registeredStudentIds.contains(student.getStudentID())) {
                        // build mail
                        MailEvent mailEvent = MailEvent.builder()
                                .subject("Reminder for Course Offering")
                                .body("Reminder: The registration period" + " will end in " + hours + " hours.")
                                .sender("noreply@miu.edu")
                                .receiver(student.getEmail())
                                .cc(student.getFaculty().getEmail())
                                .bcc("")
                                .build();
                        // send email
                        log.info("Reminder for course offering " + courseOffering.getId() + " sent to student " + student.getStudentID());
                        mailEventRepository.save(mailEvent);
                    }
                }
            }
        }
    }
}