package edu.miu.courseregistrationcommon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    // ignore validations here, we need to validate every input actually!!!
    private String email;
    private String password;
    private String username;
    private String role;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String genderType;
}
