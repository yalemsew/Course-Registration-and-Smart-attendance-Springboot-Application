package edu.miu.courseregistrationcore.controller;

//import com.tericcabrel.authapi.entities.User;
//import com.tericcabrel.authapi.dtos.LoginUserDto;
//import com.tericcabrel.authapi.dtos.RegisterUserDto;
//import com.tericcabrel.authapi.responses.LoginResponse;
//import com.tericcabrel.authapi.services.AuthenticationService;
//import com.tericcabrel.authapi.services.JwtService;

import edu.miu.courseregistrationcommon.dto.LoginDTO;
import edu.miu.courseregistrationcommon.dto.LoginResponse;
import edu.miu.courseregistrationcommon.dto.RegisterDTO;
import edu.miu.courseregistrationcore.domain.Person;
import edu.miu.courseregistrationcore.service.AuthenticationService;
import edu.miu.courseregistrationcore.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Person> register(@RequestBody RegisterDTO registerUserDto) {
        Person registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginDTO loginUserDto) {
        Person authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }
}
