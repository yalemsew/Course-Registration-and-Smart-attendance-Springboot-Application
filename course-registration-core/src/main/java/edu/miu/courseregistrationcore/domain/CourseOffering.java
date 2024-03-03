package edu.miu.courseregistrationcore.domain;

import edu.miu.courseregistrationcore.integration.AuditListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CourseOffering")
@EntityListeners(AuditListener.class)
public class CourseOffering implements Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "CourseID")
    private Course course;

    @Column(name = "Room")
    private String room;

    private int capacity;

    private int credits;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private CourseOfferingType courseOfferingType;

    @Embedded
    private Audit audit = new Audit();

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;


    public List<Session> generateSessions() {
        List<Session> sessions = new ArrayList<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            if (currentDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
                sessions.add(new Session(currentDate, LocalTime.of(10, 0), LocalTime.of(12, 30)));
                if (!currentDate.equals(endDate)) {
                    sessions.add(new Session(currentDate, LocalTime.of(13, 30), LocalTime.of(15, 30)));
                }
            }
            currentDate = currentDate.plusDays(1);
        }

        return sessions;
    }

    @Override
    public void setAuditFields(String user) {
        new AuditHandler().setAuditFields(this.audit, user);
    }
}
