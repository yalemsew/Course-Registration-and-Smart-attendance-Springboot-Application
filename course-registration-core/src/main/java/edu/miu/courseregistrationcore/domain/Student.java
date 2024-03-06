//package edu.miu.CS544.project.domain;
package edu.miu.courseregistrationcore.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "Student")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"faculty"})
public class Student extends Person {

    @Column(name = "Entry")
    private String entry;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FacultyAdviserID")
    private Faculty faculty;
    @Column(name = "AlternateID")
    private String alternateID;
    @Column(name = "ApplicantID")
    private String applicantID;
    @Column(name = "StudentID", unique = true)
    private String studentID;

}
