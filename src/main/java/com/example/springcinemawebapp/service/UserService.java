package com.example.springcinemawebapp.service;

import com.example.springcinemawebapp.exception.EmailIsAlreadyTakenException;
import com.example.springcinemawebapp.model.User;
import com.example.springcinemawebapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.springcinemawebapp.properties.TechnicalTextConstants.EMAIL_IS_ALREADY_TAKEN_MSG;

@Service
@AllArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void signUp(User user) {

        userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new EmailIsAlreadyTakenException(EMAIL_IS_ALREADY_TAKEN_MSG);
        });

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }
}
