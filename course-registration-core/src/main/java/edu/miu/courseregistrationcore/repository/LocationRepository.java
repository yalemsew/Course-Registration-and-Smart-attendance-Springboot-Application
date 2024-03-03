package edu.miu.courseregistrationcore.repository;

import edu.miu.courseregistrationcore.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findByName(String name);
}
