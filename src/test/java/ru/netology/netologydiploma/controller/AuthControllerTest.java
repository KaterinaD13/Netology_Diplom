package ru.netology.netologydiploma.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.netology.netologydiploma.model.AuthRequest;
import ru.netology.netologydiploma.model.AuthResponse;
import ru.netology.netologydiploma.service.AuthService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {AuthController.class})
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    private MockMvc mockMvc;
    @Mock
    private AuthService authService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setup() {

        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testCreateAuthToken() throws Exception {
        AuthRequest authRequest = new AuthRequest("username", "password");
        AuthResponse response = new AuthResponse("token");

        when(authService.createAuthToken(authRequest)).thenReturn(response);

        mockMvc.perform(post("/login")
                .content(objectMapper.writeValueAsString(authRequest))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        verify(authService, times(1)).createAuthToken(authRequest);
    }

}