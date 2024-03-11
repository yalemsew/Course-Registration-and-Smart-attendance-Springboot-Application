package edu.miu.courseregistrationcore.repository;

import edu.miu.courseregistrationcore.domain.CourseOffering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
// @RepositoryRestResource
public interface CourseOfferingRepository extends JpaRepository<CourseOffering, Integer> {
    // add custom queries here
    @Query("SELECT co FROM CourseOffering co WHERE :date BETWEEN co.startDate AND co.endDate")
    List<CourseOffering> findByDateBetween(@Param("date") LocalDate date);

    @Query("SELECT co FROM CourseOffering co WHERE co.id = :id")
    CourseOffering getCourseOfferingById(Integer id);



}
