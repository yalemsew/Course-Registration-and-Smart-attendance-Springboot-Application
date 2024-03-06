package edu.miu.courseregistrationcore.domain;

import edu.miu.courseregistrationcore.integration.AuditListener;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Table(name = "LocationType")
@EntityListeners(AuditListener.class)
@Getter
@NoArgsConstructor
@Setter
public class LocationType implements Auditable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;

    @Embedded
    private Audit audit = new Audit();

    @Override
    public void setAuditFields(String user) {
        new AuditHandler().setAuditFields(this.audit, user);
    }
}
