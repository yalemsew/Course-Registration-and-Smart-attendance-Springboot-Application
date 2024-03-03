package edu.miu.courseregistrationcore.repository;

import edu.miu.courseregistrationcore.domain.LocationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationTypeRepository extends JpaRepository<LocationType, Integer> {
}
