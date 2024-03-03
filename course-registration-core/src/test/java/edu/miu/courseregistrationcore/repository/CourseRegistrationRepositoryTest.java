package edu.miu.courseregistrationcore.repository;

import edu.miu.courseregistrationcore.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class CourseRegistrationRepositoryTest {

    @Autowired
    private CourseRegistrationRepository courseRegistrationRepository;

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private RoleRepository roleRepository;

    private Student student1;
    private Student student2;

    private Course course1;
    private Course course2;
    private CourseOffering courseOffering1;
    private CourseOffering courseOffering2;
    private CourseRegistration courseRegistration1;
    private CourseRegistration courseRegistration2;
    private int id1;
    private int id2;


    Role stu;

    private void loadRoles() {
        Roles[] roleNames = new Roles[]{Roles.SYS_ADMIN, Roles.STAFF, Roles.FACULTY, Roles.STUDENT};
        Map<Roles, String> roleDescriptionMap = Map.of(
                Roles.SYS_ADMIN, "MIU System Administrator",
                Roles.STAFF, "MIU Staff",
                Roles.FACULTY, "MIU Faculty",
                Roles.STUDENT, "MIU Students"
        );

        Arrays.stream(roleNames).forEach((roleName) -> {
            Optional<Role> optionalRole = roleRepository.findByName(roleName);
            optionalRole.ifPresentOrElse(System.out::println, () -> {
                Role roleToCreate = new Role();
                roleToCreate.setName(roleName);
                roleToCreate.setDescription(roleDescriptionMap.get(roleName));
                roleRepository.save(roleToCreate);
            });
        });
    }


    @BeforeEach
    public void setUp() {
        loadRoles();
        stu = roleRepository.findByName(Roles.STUDENT).orElse(null);

        student1 = new Student();
        student1.setStudentID("552424");
        student1.setRole(stu);

        entityManager.persist(student1);
        entityManager.flush();

//        student2 = new Student();
//        student2.setStudentID("552425");
//        student2.setRole(stu);
//        entityManager.persist(student2);
//        entityManager.flush();


        course1 = new Course();
        course1.setCourseName("enterprise architecture");
        course1.setCourseCode("Cod234");
        course1.setDepartment("CS");
        entityManager.persist(course1);
        entityManager.flush();

        course2 = new Course();
        course2.setCourseName("software architecture");
        course2.setCourseCode("Cod235");
        course2.setDepartment("CS");
        entityManager.persist(course2);
        entityManager.flush();

        courseOffering1 = new CourseOffering();
        courseOffering1.setCourse(course1);
        entityManager.persist(courseOffering1);
        id1 = courseOffering1.getId();
        entityManager.flush();


        courseOffering2 = new CourseOffering();
        courseOffering2.setCourse(course2);
        entityManager.persist(courseOffering2);
        id2 = courseOffering2.getId();
        entityManager.flush();

        courseRegistration1 = new CourseRegistration();
        courseRegistration1.setStudent(student1);
        courseRegistration1.setCourseOffering(courseOffering1);
        courseRegistration1.setGrade(Grade.A);
        entityManager.persist(courseRegistration1);
        entityManager.flush();

        courseRegistration2 = new CourseRegistration();
        courseRegistration2.setStudent(student1);
        courseRegistration2.setCourseOffering(courseOffering2);
        courseRegistration2.setGrade(Grade.A);
        entityManager.persist(courseRegistration2);
        entityManager.flush();
    }

    @Test
    public void whenFindByStudentId_thenReturnCourseRegistrationList() {

        List<CourseRegistration> found = courseRegistrationRepository.findByStudentId("552424");

        // then
        assertThat(found).isNotNull();
        assertThat(found).hasSize(2);
        assertThat(found.get(0).getStudent().getStudentID()).isEqualTo("552424");
        assertThat(found.get(0).getGrade()).isEqualTo(Grade.A);
        assertThat(found.get(1).getStudent().getStudentID()).isEqualTo("552424");
        assertThat(found.get(1).getGrade()).isEqualTo(Grade.A);
    }

    @Test
    public void whenFindByCourseOfferingIdAndStudentId_thenReturnCourseRegistrationList() {
        // when
        List<CourseRegistration> found = courseRegistrationRepository.findByCourseOfferingIdAndStudentId(id1, "552424");

        // then
        assertThat(found).isNotNull();
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getStudent().getStudentID()).isEqualTo("552424");
//        assertThat(found.get(0).getCourseOffering().getId()).isEqualTo(id1);
        assertThat(found.get(0).getGrade()).isEqualTo(Grade.A);
    }

    @Test
    public void whenFindByStudent_StudentID_thenReturnCourseRegistrationList() {
        // when
        List<CourseRegistration> found = courseRegistrationRepository.findByStudent_StudentID("552424");

        // then
        assertThat(found).isNotNull();
        assertThat(found).hasSize(2);
        assertThat(found.get(0).getStudent().getStudentID()).isEqualTo("552424");
        assertThat(found.get(0).getGrade()).isEqualTo(Grade.A);
//        assertThat(found.get(1).getStudent().getStudentID()).isEqualTo("552425");
//        assertThat(found.get(1).getGrade()).isEqualTo(Grade.A);
    }

    @Test
    public void whenFindDistinctStudentsByCourseOfferingId_thenReturnStudentList() {
        // given
        Student student1 = new Student();
        student1.setStudentID("552430");
        student1.setRole(stu);
        entityManager.persist(student1);
        entityManager.flush();

        Student student2 = new Student();
        student2.setStudentID("552432");
        student2.setRole(stu);
        entityManager.persist(student2);
        entityManager.flush();

        Course course1 = new Course();
        course1.setCourseName("enterprise architecture");
        course1.setCourseCode("Cod234");
        course1.setDepartment("CS");
        entityManager.persist(course1);
        entityManager.flush();

        Course course2 = new Course();
        course2.setCourseName("software architecture");
        course2.setCourseCode("Cod235");
        course2.setDepartment("CS");
        entityManager.persist(course2);
        entityManager.flush();

        CourseOffering courseOffering1 = new CourseOffering();
        courseOffering1.setCourse(course1);
        entityManager.persist(courseOffering1);
        entityManager.flush();
        int id11 = courseOffering1.getId(); // Get the generated ID after persisting

        CourseOffering courseOffering2 = new CourseOffering();
        courseOffering2.setCourse(course2);
        entityManager.persist(courseOffering2);
        entityManager.flush();
        int id2 = courseOffering2.getId(); // Get the generated ID after persisting

        CourseRegistration courseRegistration1 = new CourseRegistration();
        courseRegistration1.setStudent(student1);
        courseRegistration1.setCourseOffering(courseOffering1);
        courseRegistration1.setGrade(Grade.A);
        entityManager.persist(courseRegistration1);
        entityManager.flush();

        CourseRegistration courseRegistration2 = new CourseRegistration();
        courseRegistration2.setStudent(student2);
        courseRegistration2.setCourseOffering(courseOffering1);
        courseRegistration2.setGrade(Grade.A);
        entityManager.persist(courseRegistration2);
        entityManager.flush();

        CourseRegistration courseRegistration3 = new CourseRegistration();
        courseRegistration3.setStudent(student1);
        courseRegistration3.setCourseOffering(courseOffering2);
        courseRegistration3.setGrade(Grade.A);
        entityManager.persist(courseRegistration3);
        entityManager.flush();

        // when
        List<Student> found = courseRegistrationRepository.findDistinctStudentsByCourseOfferingId(id11);

        // then
        assertThat(found).isNotNull();
        assertThat(found).hasSize(2);
        assertThat(found.get(0).getStudentID()).isEqualTo("552430");
    }
}
