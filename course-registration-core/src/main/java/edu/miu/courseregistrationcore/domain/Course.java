package edu.miu.courseregistrationcore.domain;

import edu.miu.courseregistrationcore.integration.AuditListener;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "Course")
@EntityListeners(AuditListener.class)
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class Course implements Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int credits;

    @Lob
    @Column(name = "CourseDescription", columnDefinition = "LONGTEXT")
    private String courseDescription;

    @Column(name = "CourseCode")
    private String courseCode;
    @Column(name = "CourseName")
    private String courseName;
    private String department;
    @Embedded
    private Audit audit = new Audit();

    @OneToMany
    @JoinTable(
            name = "CoursePrerequisite",
            joinColumns = @JoinColumn(name = "CourseId"),
            inverseJoinColumns = @JoinColumn(name = "PrerequisiteId")
    )
    private List<Course> courses = new ArrayList<>();

    @Override
    public void setAuditFields(String user) {
        new AuditHandler().setAuditFields(this.audit, user);
    }
}
