package edu.miu.courseregistrationcore.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "Roles")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles name;

    @Column(nullable = false)
    private String description;

    @Embedded
    private Audit audit = new Audit();
    ;

    @Override
    public void setAuditFields(String user) {
        new AuditHandler().setAuditFields(this.audit, user);
    }

}
