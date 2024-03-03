package edu.miu.courseregistrationcore.repository;

import edu.miu.courseregistrationcore.domain.MailEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailEventRepository extends JpaRepository<MailEvent, Long> {
}
