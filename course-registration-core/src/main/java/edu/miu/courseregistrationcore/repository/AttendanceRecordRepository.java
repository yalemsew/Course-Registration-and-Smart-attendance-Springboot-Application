package edu.miu.courseregistrationcore.repository;

import edu.miu.courseregistrationcore.domain.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Integer> {
    //@Query("SELECT a FROM AttendanceRecord a WHERE a.student.studentID = :studentId AND a.session.courseOffering.id = :offeringId")
//    @Query("SELECT a from AttendanceRecord a join a.student s join s.courseRegistration cr join cr.courseOffering co WHERE s.studentID = :studentId AND co.id = :offeringId")
//    List<AttendanceRecord> findByStudentIdAndCourseOfferingId(@Param("studentId") String studentId, @Param("offeringId") Integer offeringId);
    @Query("SELECT a FROM AttendanceRecord a WHERE a.student.studentID = :studentId AND a.scanDateTime BETWEEN :startDate AND :endDate")
    List<AttendanceRecord> findByStudentIdAndScanDateTimeBetween(@Param("studentId") String studentId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("select ar from AttendanceRecord ar join fetch ar.student s where s.studentID = :studentId")
    List<AttendanceRecord> findAllAttendancerecordOfStudent(@Param("studentId") String studentId);

    @Query("SELECT ar FROM AttendanceRecord ar JOIN ar.student s JOIN CourseRegistration cr ON cr.student = s JOIN cr.courseOffering co WHERE co.id = :offeringId  AND s.studentID = :studentId")
    List<AttendanceRecord> findByCourseOfferingId(Integer offeringId, String studentId);

    @Query("SELECT ar FROM AttendanceRecord ar WHERE ar.student.studentID = :studentId")
    List<AttendanceRecord> findAllAttendanceRecordsByStudentId(@Param("studentId") String studentId);

}

