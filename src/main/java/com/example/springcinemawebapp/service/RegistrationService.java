package com.example.springcinemawebapp.service;

import com.example.springcinemawebapp.dto.UserDTO;
import com.example.springcinemawebapp.model.Role;
import com.example.springcinemawebapp.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;

    public void register(UserDTO userDTO) {

        User user = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .role(Role.USER)
                .build();

        userService.signUp(user);
    }
}
