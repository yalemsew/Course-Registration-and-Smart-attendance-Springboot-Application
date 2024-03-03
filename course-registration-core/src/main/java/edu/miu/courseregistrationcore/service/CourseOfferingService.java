package edu.miu.courseregistrationcore.service;

import edu.miu.courseregistrationcore.domain.CourseOffering;
import edu.miu.courseregistrationcore.repository.CourseOfferingRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.List;

@Service
@Transactional
@Slf4j
public class CourseOfferingService {

    @Autowired
    CourseOfferingRepository courseOfferingRepository;

    public List<CourseOffering> getCourseOfferingByDateBetween(LocalDate date) {
        return courseOfferingRepository.findByDateBetween(date);
    }

    public List<CourseOffering> getAllCourseOfferings() {
        return courseOfferingRepository.findAll();
    }

    public CourseOffering getCourseOfferingsbyId(int offeringId) {
        return courseOfferingRepository.findById(offeringId).orElseThrow();
    }

    public void deleteCourseOfferingById(int id) {
        courseOfferingRepository.deleteById(id);
    }

    public CourseOffering updateCourseOfferingById(int id, CourseOffering courseOffering) {
        CourseOffering existingCourseOffering = courseOfferingRepository.getCourseOfferingById(id);

        existingCourseOffering.setCourse(courseOffering.getCourse());
        existingCourseOffering.setRoom(courseOffering.getRoom());
        existingCourseOffering.setCapacity(courseOffering.getCapacity());
        existingCourseOffering.setCredits(courseOffering.getCredits());
        existingCourseOffering.setStartDate(courseOffering.getStartDate());
        existingCourseOffering.setEndDate(courseOffering.getEndDate());
        existingCourseOffering.setCourseOfferingType(courseOffering.getCourseOfferingType());
        existingCourseOffering.setFaculty(courseOffering.getFaculty());

        courseOfferingRepository.save(existingCourseOffering);

        return existingCourseOffering;
    }

    public CourseOffering createCourseOffering(CourseOffering courseOffering) {
        courseOfferingRepository.save(courseOffering);
        return courseOffering;
    }

    //Get the start date of the course offering

    public Long getStartDate(int id) {
        CourseOffering courseOffering = courseOfferingRepository.getCourseOfferingById(id);
        LocalDateTime startDate = courseOffering.getStartDate().atStartOfDay();

        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Chicago"));
        Duration duration = Duration.between(now, startDate);
        return duration.toHours();
    }

    //Delete course offering if the start date is before today
//    @Scheduled(fixedRate = 50000)
//    public void checkCourseOffering() {
//        List<CourseOffering> courseOfferings = courseOfferingRepository.findAll();
//        for (CourseOffering courseOffering : courseOfferings) {
//            if (courseOffering.getStartDate().isBefore(LocalDate.now())) {
//                courseOfferingRepository.delete(courseOffering);
//            }
//        }
//    }

    //Send a reminder, 8 and 4 hours prior to the end of the registration period
    @Scheduled(cron = "0 0/30 * * * ?")
    public void ReminderForCourseOfferingStart() {
        List<CourseOffering> courseOfferings = courseOfferingRepository.findAll();
        for (CourseOffering courseOffering : courseOfferings) {
            Long hours = getStartDate(courseOffering.getId());
            if (hours == 8 || hours == 4) {
                log.info("Reminder for course offering " + courseOffering.getId() + " sent");
//                System.out.println("Reminder for course offering " + courseOffering.getId() + " sent");
            }

        }
    }
}
