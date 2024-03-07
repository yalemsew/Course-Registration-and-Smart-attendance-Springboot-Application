package edu.miu.courseregistrationcore.service;

import edu.miu.courseregistrationcore.domain.CourseOffering;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ICourseOfferingService {
    List<CourseOffering> getCourseOfferingByDateBetween(LocalDate date);

    public Optional<CourseOffering> getCourseOfferingById(Integer id);
}
