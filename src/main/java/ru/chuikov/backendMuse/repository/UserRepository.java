package ru.chuikov.backendMuse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chuikov.backendMuse.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
}
