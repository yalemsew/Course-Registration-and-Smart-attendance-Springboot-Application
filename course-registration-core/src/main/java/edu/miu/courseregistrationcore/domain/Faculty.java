package edu.miu.courseregistrationcore.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "Faculty")
public class Faculty extends Person {
    @Column(name = "Salutation")
    private String salutation;

    @Column
    private String facultyID;

    @ElementCollection()
    @CollectionTable(name = "FacultyHobby", joinColumns = @JoinColumn(name = "Faculty_id"))
    @Column(name = "hobbies")
    private List<String> facultyHobbies;
}
