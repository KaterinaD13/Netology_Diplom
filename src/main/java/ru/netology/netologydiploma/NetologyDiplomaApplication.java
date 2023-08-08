package ru.netology.netologydiploma;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.netology.netologydiploma.entity.Role;
import ru.netology.netologydiploma.entity.User;
import ru.netology.netologydiploma.reposipory.UserRepository;

@SpringBootApplication
public class NetologyDiplomaApplication {

    public static void main(String[] args) {

        SpringApplication.run(NetologyDiplomaApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            if (userRepository.count() == 0) {
                userRepository.save(User.builder()
                        .login("user")
                        .password(encoder.encode("password"))
                        .role(Role.USER)
                        .build());
                userRepository.save(User.builder()
                        .login("admin")
                        .password(encoder.encode("password"))
                        .role(Role.ADMIN)
                        .build());
            }
        };
    }
}