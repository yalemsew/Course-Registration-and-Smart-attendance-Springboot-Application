package edu.miu.courseregistrationcore.domain;

import java.time.LocalDateTime;

public class AuditHandler {
    public void setAuditFields(Audit audit, String user) {
        if (audit.getCreatedOn() == null) {
            audit.setCreatedOn(LocalDateTime.now());
            audit.setCreatedBy(user);
        }
        audit.setUpdatedOn(LocalDateTime.now());
        audit.setUpdatedBy(user);
    }
}
