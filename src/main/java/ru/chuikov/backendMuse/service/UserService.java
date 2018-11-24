package ru.chuikov.backendMuse.service;

import ru.chuikov.backendMuse.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(User user);
    void delete(User user);
    Optional<User> get(Long id);
    User get(String email);
    List<User> getAll();
    User update(User user);
}
