package edu.miu.courseregistrationcore.domain;

import edu.miu.courseregistrationcore.dto.GrantedAuthorityDto;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@SecondaryTable(name = "PersonAccount", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
@Data
public class Person implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(table = "PersonAccount")
    String username;
    @Column(table = "PersonAccount")
    String password;
    LocalDate birthDate;
    @Enumerated(EnumType.STRING)
    GenderType genderType;
    // First we assume it is many-to-one relationship here
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "roleId", referencedColumnName = "id", nullable = false)
    private Role role;
    private String firstName;
    private String lastName;
    private String email;
    @Embedded
    private Audit audit;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName().toString());
        return List.of(authority);
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role.getName().toString());
//        return List.of(new GrantedAuthorityDto(authority));
//    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

