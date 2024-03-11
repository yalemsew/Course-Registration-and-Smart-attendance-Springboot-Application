package edu.miu.courseregistrationcore.integration;

import edu.miu.courseregistrationcore.domain.Auditable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class AuditListener {

    @PrePersist
    @PreUpdate
    public void setAuditFields(Object entity) {
        if (entity instanceof Auditable auditable) {
            auditable.setAuditFields("currentUser");  // TODO: replace with actual user context
        }
    }
}

