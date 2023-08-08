package ru.netology.netologydiploma.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.netology.netologydiploma.model.AuthRequest;
import ru.netology.netologydiploma.model.AuthResponse;
import ru.netology.netologydiploma.exceprion.AuthenticationException;
import ru.netology.netologydiploma.security.JwtService;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse createAuthToken(AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Bad credentials");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getLogin());
        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token);
    }
}