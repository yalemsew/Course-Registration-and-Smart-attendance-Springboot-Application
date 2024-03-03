package edu.miu.courseregistrationcore.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor

public class CourseRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Grade grade;

    @ManyToOne
    @JoinColumn(name = "courseoffering_id")
    private CourseOffering courseOffering;

    public CourseRegistration(Student student, CourseOffering courseOffering) {
        this.student = student;
        this.courseOffering = courseOffering;
    }
}
