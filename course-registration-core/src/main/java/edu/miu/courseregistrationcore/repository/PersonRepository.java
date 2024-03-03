package edu.miu.courseregistrationcore.repository;

import edu.miu.courseregistrationcore.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByEmail(String email);

    Optional<Person> findByUsername(String username);
}
