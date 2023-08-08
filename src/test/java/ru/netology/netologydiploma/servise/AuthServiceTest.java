package ru.netology.netologydiploma.servise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import ru.netology.netologydiploma.exceprion.AuthenticationException;
import ru.netology.netologydiploma.model.AuthRequest;
import ru.netology.netologydiploma.model.AuthResponse;
import ru.netology.netologydiploma.security.JwtService;
import ru.netology.netologydiploma.service.AuthService;
import ru.netology.netologydiploma.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserService userService;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;
    @InjectMocks
    private AuthService authService;

    @Test
    public void testCreateAuthTokenValidInput() {
        AuthRequest authRequest = new AuthRequest("testuser", "testpassword");
        UserDetails userDetails = mock(UserDetails.class);
        when(userService.loadUserByUsername("testuser")).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn("testtoken");

        AuthResponse response = authService.createAuthToken(authRequest);

        assertEquals("testtoken", response.getAuthToken());
        verify(authenticationManager, times(1))
                .authenticate(new UsernamePasswordAuthenticationToken("testuser", "testpassword"));
        verify(userService, times(1)).loadUserByUsername("testuser");
        verify(jwtService, times(1)).generateToken(userDetails);
    }

    @Test
    public void testCreateAuthTokenBadCredentials() {
        AuthRequest authRequest = new AuthRequest("testuser", "testpassword");
        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        Assertions.assertThrows(AuthenticationException.class, () ->
                authService.createAuthToken(authRequest));

        verify(authenticationManager, times(1))
                .authenticate(new UsernamePasswordAuthenticationToken("testuser", "testpassword"));
    }
}