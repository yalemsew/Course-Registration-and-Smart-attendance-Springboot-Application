package edu.miu.courseregistrationcore.bootstrap;

import edu.miu.courseregistrationcommon.dto.RegisterDTO;
import edu.miu.courseregistrationcore.domain.Person;
import edu.miu.courseregistrationcore.domain.Role;
import edu.miu.courseregistrationcore.domain.Roles;
import edu.miu.courseregistrationcore.repository.PersonRepository;
import edu.miu.courseregistrationcore.repository.RoleRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    private final PersonRepository personRepository;

    private final PasswordEncoder passwordEncoder;


    public AdminSeeder(
            RoleRepository roleRepository,
            PersonRepository personRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createSuperAdministrator();
    }

    private void createSuperAdministrator() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("admin");
        registerDTO.setEmail("admin@miu.edu");
        registerDTO.setPassword("123456");

        Optional<Role> optionalRole = roleRepository.findByName(Roles.SYS_ADMIN);
        Optional<Person> optionalUser = personRepository.findByEmail(registerDTO.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        var user = new Person();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setRole(optionalRole.get());
        personRepository.save(user);
    }
}
