package ru.netology.netologydiploma.reposipory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.netology.netologydiploma.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
}