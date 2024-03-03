package edu.miu.courseregistrationcore.repository;

import edu.miu.courseregistrationcore.domain.Role;
import edu.miu.courseregistrationcore.domain.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Roles name);
}
