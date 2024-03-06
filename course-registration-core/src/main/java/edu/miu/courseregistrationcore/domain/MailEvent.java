package edu.miu.courseregistrationcore.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailEvent implements Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subject;
    private String body;
    private String sender;
    private String receiver;
    private String cc;
    private String bcc;
    @Embedded
    private Audit audit = new Audit();
    ;

    @Override
    public void setAuditFields(String user) {
        new AuditHandler().setAuditFields(this.audit, user);
    }
}
