package com.example.springcinemawebapp.service;

import com.example.springcinemawebapp.exception.EmailIsAlreadyTakenException;
import com.example.springcinemawebapp.model.User;
import com.example.springcinemawebapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void signUp(User user) {

        // TODO: 08.10.2021 MESSAGE
        userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new EmailIsAlreadyTakenException("Email is already taken");
        });

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }
}
