package edu.miu.courseregistrationcore.domain;

public interface Auditable {
    Audit getAudit();

    void setAudit(Audit audit);

    void setAuditFields(String user);
}
