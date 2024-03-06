package edu.miu.courseregistrationcore.domain;

import edu.miu.courseregistrationcore.integration.AuditListener;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Location")
@EntityListeners(AuditListener.class)
public class Location implements Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "Capacity")
    private int capacity;
    @Column(name = "Name")
    private String name;
    @Embedded
    private Audit audit = new Audit();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id")
    private LocationType locationType;

    @Override
    public void setAuditFields(String user) {
        new AuditHandler().setAuditFields(this.audit, user);
    }

}
