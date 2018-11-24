package ru.chuikov.backendMuse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.chuikov.backendMuse.entity.Role;
import ru.chuikov.backendMuse.entity.User;
import ru.chuikov.backendMuse.service.SignService;
import ru.chuikov.backendMuse.service.UserService;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Arrays;

@Service
@Transactional
public class SignServiceImpl implements SignService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.create(user);
    }

    @PostConstruct
    private void setupDefaultUser() {
        //-- just to make sure there is an ADMIN user exist in the database for testing purpose
            userService.create(new User("admin@mail.ru",
                    passwordEncoder.encode("password1"),
                    Arrays.asList(new Role("USER"), new Role("ADMIN"))));
    }
}
