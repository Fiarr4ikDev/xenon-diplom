package ru.fiarr4ik.securityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fiarr4ik.securityservice.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
