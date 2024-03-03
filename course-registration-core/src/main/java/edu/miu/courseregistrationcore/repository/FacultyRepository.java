package edu.miu.courseregistrationcore.repository;

import edu.miu.courseregistrationcore.domain.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

    Faculty findByFacultyID(String facultyID);

    void deleteByFacultyID(String facultyID);
}
