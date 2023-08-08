package ru.netology.netologydiploma.controller;

import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.netologydiploma.model.AuthRequest;
import ru.netology.netologydiploma.model.AuthResponse;
import ru.netology.netologydiploma.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private static final Logger log = Logger.getLogger(AuthController.class);

    @PostMapping("/login")
    public AuthResponse createAuthToken(@RequestBody AuthRequest authRequest) {
        log.info("New authRequest");
        return authService.createAuthToken(authRequest);
    }
}