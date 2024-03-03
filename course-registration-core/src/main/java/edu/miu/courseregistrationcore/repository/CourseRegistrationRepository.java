package edu.miu.courseregistrationcore.repository;

import edu.miu.courseregistrationcore.domain.CourseRegistration;
import edu.miu.courseregistrationcore.domain.Student;
import edu.miu.courseregistrationcore.dto.CourseStudentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Integer> {


    @Query("SELECT c FROM CourseRegistration c WHERE c.student.studentID = :studentId")
    List<CourseRegistration> findByStudentId(@Param("studentId") String studentId);

    @Query("SELECT c FROM CourseRegistration c JOIN FETCH c.courseOffering co WHERE co.id = :offeringId AND c.student.studentID = :studentId")
    List<CourseRegistration> findByCourseOfferingIdAndStudentId(@Param("offeringId") int offeringId, @Param("studentId") String studentId);

    List<CourseRegistration> findByStudent_StudentID(String studentId);

    @Query("SELECT DISTINCT s FROM Student s JOIN CourseRegistration cr ON s.id = cr.student.id WHERE cr.courseOffering.id = :offeringId")
    List<Student> findDistinctStudentsByCourseOfferingId(@Param("offeringId") int offeringId);

    @Query("SELECT cr FROM CourseRegistration cr WHERE cr.courseOffering.id = :offeringId")
    List<CourseRegistration> findAllByCourseOfferingId(@Param("offeringId") int offeringId);

//    @Query("SELECT s.email, co.startDate, co.course.courseCode FROM CourseRegistration cr JOIN cr.student s JOIN cr.courseOffering co")
//    new com.example.CourseRegistrationDTO(cr.id, s.email, s.name, co.startDate, c.courseCode)
@Query("SELECT NEW edu.miu.courseregistrationcore.dto.CourseStudentDto(s.email, co.startDate, co.course.courseName) " +
        "FROM CourseRegistration cr " +
        "JOIN cr.student s " +
        "JOIN cr.courseOffering co " +
        "JOIN co.course c")
List<CourseStudentDto> findStartdateofcourse();

}



