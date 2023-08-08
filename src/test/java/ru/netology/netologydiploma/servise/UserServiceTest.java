package ru.netology.netologydiploma.servise;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.netology.netologydiploma.entity.Role;
import ru.netology.netologydiploma.entity.User;
import ru.netology.netologydiploma.reposipory.UserRepository;
import ru.netology.netologydiploma.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testLoadUserByUsername() {
        String login = "testLogin";
        User user = User.builder()
                .login("testUser")
                .password("testPassword")
                .role(Role.USER)
                .build();

        when(userRepository.findByLogin(login)).thenReturn(Optional.of(user));

        UserDetails result = userService.loadUserByUsername(login);

        assertEquals(user.getLogin(), result.getUsername());
        assertEquals(user.getPassword(), result.getPassword());

    }

    @Test
    void testLoadUserByUsernameThrowsUsernameNotFoundException() {
        String login = "nonExistingLogin";

        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());

        try {
            userService.loadUserByUsername(login);
        } catch (UsernameNotFoundException e) {
            assertEquals("Bad credentials", e.getMessage());
        }
    }
}