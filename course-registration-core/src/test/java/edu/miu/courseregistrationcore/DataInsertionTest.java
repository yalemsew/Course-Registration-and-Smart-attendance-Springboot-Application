package edu.miu.courseregistrationcore;

import edu.miu.courseregistrationcore.domain.*;
import edu.miu.courseregistrationcore.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CourseRegistrationCoreApplication.class)
public class DataInsertionTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private CourseOfferingRepository courseOfferingRepository;
    @Autowired
    private CourseRegistrationRepository courseRegistrationRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testInsertData() {

        Role faculty = roleRepository.findByName(Roles.FACULTY).orElse(null);
        Role student = roleRepository.findByName(Roles.STUDENT).orElse(null);
        Role admin = roleRepository.findByName(Roles.SYS_ADMIN).orElse(null);
        Role sysAdmin = roleRepository.findByName(Roles.SYS_ADMIN).orElse(null);
        Role staff = roleRepository.findByName(Roles.STAFF).orElse(null);


        Faculty faculty1 = new Faculty();
        faculty1.setSalutation("Dr.");
        faculty1.setRole(sysAdmin);
        faculty1.setFacultyID("FAC001");
        faculty1.setAudit(new Audit(LocalDateTime.now(), LocalDateTime.now(), "system", "system"));
        faculty1.setEmail("fct1@miu.edu");
        BCryptPasswordEncoder passwordEncoder4 = new BCryptPasswordEncoder();
        String encodedPassword4 = passwordEncoder4.encode("password");
        faculty1.setPassword(encodedPassword4);
        faculty1.setUsername("mike");
        faculty1.setFirstName("Mike");
        faculty1.setLastName("Smith");
        faculty1.setGenderType(GenderType.FEMALE);
        faculty1.setBirthDate(LocalDate.of(1995, 1, 1));
        List<String> facultyHobbies = new ArrayList<>();
        facultyHobbies.add("Reading");
        facultyHobbies.add("Swimming");
        faculty1.setFacultyHobbies(facultyHobbies);
        personRepository.save(faculty1);



        Faculty faculty2 = new Faculty();
        faculty2.setSalutation("Prof.");
        faculty2.setRole(admin);
        faculty2.setUsername("king");
        faculty2.setFacultyID("FAC002");
        faculty2.setAudit(new Audit(LocalDateTime.now(), LocalDateTime.now(), "system", "system"));
        faculty2.setEmail("fct2@miu.edu");
        BCryptPasswordEncoder passwordEncoder5 = new BCryptPasswordEncoder();
        String encodedPassword5 = passwordEncoder5.encode("password");
        faculty2.setPassword(encodedPassword5);
        faculty2.setFirstName("king");
        faculty2.setLastName("david");
        faculty2.setGenderType(GenderType.MALE);
        faculty2.setBirthDate(LocalDate.of(1997, 3, 1));
        List<String> facultyHobbies2 = new ArrayList<>();
        facultyHobbies2.add("Playing soccer");
        facultyHobbies2.add("Biking");
        faculty2.setFacultyHobbies(facultyHobbies2);
        personRepository.save(faculty2);

        Faculty faculty3 = new Faculty();
        faculty3.setSalutation("Mr.");
        faculty3.setUsername("david");
        faculty3.setRole(faculty);
        faculty3.setFacultyID("FAC003");
        faculty3.setAudit(new Audit(LocalDateTime.now(), LocalDateTime.now(), "system", "system"));
        faculty3.setEmail("fct3@miu.edu");
        BCryptPasswordEncoder passwordEncoder6 = new BCryptPasswordEncoder();
        String encodedPassword6 = passwordEncoder6.encode("password");
        faculty3.setPassword(encodedPassword6);
        faculty3.setFirstName("david");
        faculty3.setLastName("smith");
        faculty3.setBirthDate(LocalDate.of(1997, 3, 1));
        faculty3.setGenderType(GenderType.FEMALE);
        List<String> facultyHobbies3 = new ArrayList<>();
        facultyHobbies3.add("Dancing");
        facultyHobbies3.add("Hiking");
        faculty3.setFacultyHobbies(facultyHobbies3);
        personRepository.save(faculty3);

        Student student1 = new Student();
        student1.setEntry("2023-Fall");
        student1.setRole(student);
        student1.setFaculty(faculty1);
        student1.setFirstName("Ping");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("password");
        student1.setPassword(encodedPassword);
        student1.setUsername("ping");
        student1.setEmail("stu1@miu.edu");
        student1.setLastName("que");
        student1.setAudit(new Audit(LocalDateTime.now(), LocalDateTime.now(), "system", "system"));
        student1.setAlternateID("ALT001");
        student1.setGenderType(GenderType.MALE);
        student1.setApplicantID("APP001");
        student1.setBirthDate(LocalDate.of(1999, 1, 1));
        student1.setStudentID("STU001");
        personRepository.save(student1);


        Student student2 = new Student();
        student2.setEntry("2023-Spring");
        student2.setFirstName("Jane");
        student2.setLastName("Doe");
        student2.setRole(student);
        student2.setBirthDate(LocalDate.of(1990, 1, 1));
        student2.setGenderType(GenderType.MALE);
        student2.setAudit(new Audit(LocalDateTime.now(), LocalDateTime.now(), "system", "system"));
        student2.setEmail("stu2@miu.edu");
        student2.setUsername("jane");
        BCryptPasswordEncoder passwordEncoder2 = new BCryptPasswordEncoder();
        String encodedPassword2 = passwordEncoder2.encode("password");
        student2.setPassword(encodedPassword2);
        student2.setFaculty(faculty2);
        student2.setAlternateID("ALT002");
        student2.setApplicantID("APP002");
        student2.setStudentID("STU002");
        personRepository.save(student2);

        Student student3 = new Student();
        student3.setEntry("2024-Winter");
        student3.setFirstName("micheal");
        student3.setRole(student);
        student3.setLastName("Jack");
        student3.setBirthDate(LocalDate.of(1990, 1, 1));
        student3.setGenderType(GenderType.FEMALE);
        student3.setAudit(new Audit(LocalDateTime.now(), LocalDateTime.now(), "system", "system"));
        student3.setEmail("stu3@miu.edu");
        student3.setUsername("micheal");
        BCryptPasswordEncoder passwordEncoder3 = new BCryptPasswordEncoder();
        String encodedPassword3 = passwordEncoder3.encode("password");
        student3.setPassword(encodedPassword3);
        student3.setFaculty(faculty3);
        student3.setAlternateID("ALT003");
        student3.setApplicantID("APP003");
        student3.setStudentID("STU003");
        personRepository.save(student3);

        // Create prerequisite courses
        Course prerequisite1 = new Course();
        prerequisite1.setCredits(3);
        prerequisite1.setCourseDescription("Basic Mathematics");
        prerequisite1.setCourseCode("MATH101");
        prerequisite1.setCourseName("Basic Math");
        prerequisite1.setDepartment("Mathematics");

        Course prerequisite2 = new Course();
        prerequisite2.setCredits(3);
        prerequisite2.setCourseDescription("Introduction to Programming");
        prerequisite2.setCourseCode("CS100");
        prerequisite2.setCourseName("Intro to Programming");
        prerequisite2.setDepartment("Computer Science");

       /// Save prerequisite courses to the repository
        courseRepository.save(prerequisite1);
        courseRepository.save(prerequisite2);

        Course course1 = new Course();
        course1.setCredits(3);
        course1.setCourseDescription("Introduction to Computer Science");
        course1.setCourseCode("CS101");
        course1.setCourseName("Intro to CS");
        course1.setDepartment("Computer Science");
        List<Course> prerequisites = new ArrayList<>();
        prerequisites.add(prerequisite1);
        prerequisites.add(prerequisite2);
        course1.setCourses(prerequisites);
        courseRepository.save(course1);

        // Create prerequisite courses for course2
        Course prerequisite3 = new Course();
        prerequisite3.setCredits(3);
        prerequisite3.setCourseDescription("Data Structures");
        prerequisite3.setCourseCode("CS201");
        prerequisite3.setCourseName("Data Structures");
        prerequisite3.setDepartment("Computer Science");

        Course prerequisite4 = new Course();
        prerequisite4.setCredits(3);
        prerequisite4.setCourseDescription("Discrete Mathematics");
        prerequisite4.setCourseCode("MATH201");
        prerequisite4.setCourseName("Discrete Math");
        prerequisite4.setDepartment("Mathematics");

     // Save prerequisite courses to the repository
        courseRepository.save(prerequisite3);
        courseRepository.save(prerequisite4);

        Course course2 = new Course();
        course2.setCredits(4);
        course2.setCourseDescription("Advanced Algorithms");
        course2.setCourseCode("CS301");
        course2.setCourseName("Algorithms");
        course2.setDepartment("Computer Science");
        List<Course> prerequisitesForCourse2 = new ArrayList<>();
        prerequisitesForCourse2.add(prerequisite3);
        prerequisitesForCourse2.add(prerequisite4);
        course2.setCourses(prerequisitesForCourse2);
        courseRepository.save(course2);


        Location location1 = new Location();
        location1.setCapacity(100);
        location1.setName("Main Campus");
        LocationType locationType1 = new LocationType();
        locationType1.setType("Classroom");
        location1.setLocationType(locationType1);
        location1.setAudit(new Audit(LocalDateTime.now(), LocalDateTime.now(), "system", "system"));
        locationRepository.save(location1);

        Location location2 = new Location();
        location2.setCapacity(75);
        location2.setName("Remote Location");
        LocationType locationType2 = new LocationType();
        locationType2.setType("Remote");
        location2.setLocationType(locationType2);
        location2.setAudit(new Audit(LocalDateTime.now(), LocalDateTime.now(), "system", "system"));
        locationRepository.save(location2);

        CourseOffering offering1 = new CourseOffering();
        offering1.setCourse(course1);
        offering1.setCourseOfferingType(CourseOfferingType.ON_CAMPUS);
        offering1.setStartDate(LocalDate.of(2024, 6, 19));
        offering1.setEndDate(LocalDate.of(2024, 7, 19));
        offering1.setCredits(4);
        offering1.setRoom("Room 101");
        offering1.setCapacity(100);
        offering1.setFaculty(faculty1);
        courseOfferingRepository.save(offering1);

        CourseOffering offering2 = new CourseOffering();
        offering2.setCourse(course2);
        offering2.setCourseOfferingType(CourseOfferingType.DISTANCE);
        offering2.setStartDate(LocalDate.of(2024, 6, 19));
        offering2.setEndDate(LocalDate.of(2024, 12, 15));
        offering2.setCredits(4);
        offering2.setRoom("Room 103");
        offering2.setCapacity(100);
        offering2.setFaculty(faculty2);
        courseOfferingRepository.save(offering2);
//2024-06-19
        AttendanceRecord attendance1 = new AttendanceRecord();
        attendance1.setScanDateTime(LocalDateTime.of(2024,6,19,10,5,0));
        attendance1.setLocation(location1);
        attendance1.setStudent(student1);
        attendanceRecordRepository.save(attendance1);

        AttendanceRecord attendance2 = new AttendanceRecord();
        attendance2.setScanDateTime(LocalDateTime.of(2024,6,19,10,5,0));
        attendance2.setLocation(location2);
        attendance2.setStudent(student2);
        attendanceRecordRepository.save(attendance2);

        AttendanceRecord attendance3 = new AttendanceRecord();
        attendance3.setScanDateTime(LocalDateTime.of(2024,6,19,10,5,0));
        attendance3.setLocation(location1);
        attendance3.setStudent(student3);
        attendanceRecordRepository.save(attendance3);

        AttendanceRecord attendance4 = new AttendanceRecord();
        attendance4.setScanDateTime(LocalDateTime.of(2024,6,20,10,5,0));
        attendance4.setLocation(location1);
        attendance4.setStudent(student1);
        attendanceRecordRepository.save(attendance4);

        CourseRegistration courseRegistration1 = new CourseRegistration();
        courseRegistration1.setCourseOffering(offering1);
        courseRegistration1.setStudent(student1);
        courseRegistration1.setGrade(Grade.A);
        courseRegistrationRepository.save(courseRegistration1);

        CourseRegistration courseRegistration2 = new CourseRegistration();
        courseRegistration2.setCourseOffering(offering1);
        courseRegistration2.setStudent(student2);
        courseRegistration2.setGrade(Grade.B);
        courseRegistrationRepository.save(courseRegistration2);


    }
}
