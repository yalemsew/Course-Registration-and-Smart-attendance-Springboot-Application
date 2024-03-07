package edu.miu.courseregistrationcore.service;

import edu.miu.courseregistrationcommon.dto.LoginDTO;
import edu.miu.courseregistrationcommon.dto.RegisterDTO;
import edu.miu.courseregistrationcore.domain.Person;
import edu.miu.courseregistrationcore.domain.Role;
import edu.miu.courseregistrationcore.domain.Roles;
import edu.miu.courseregistrationcore.repository.PersonRepository;
import edu.miu.courseregistrationcore.repository.RoleRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final PersonRepository personRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final RoleRepository roleRepository;

    public AuthenticationService(
            PersonRepository personRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder, RoleRepository roleRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public Person signup(RegisterDTO input) {
        Optional<Role> optionalRole = roleRepository.findByName(Roles.STUDENT);
        if (optionalRole.isEmpty()) {
            return null;
        }
        Person person = new Person();
        person.setUsername(input.getUsername());
        person.setEmail(input.getEmail());
        person.setPassword(passwordEncoder.encode(input.getPassword()));
        person.setRole(optionalRole.get());
        return personRepository.save(person);
    }

    public Person authenticate(LoginDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return personRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}