package edu.miu.courseregistrationcore.bootstrap;

import edu.miu.courseregistrationcore.domain.Role;
import edu.miu.courseregistrationcore.domain.Roles;
import edu.miu.courseregistrationcore.repository.RoleRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;


    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.loadRoles();
    }

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
}
