package com.example.springcinemawebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    // TODO: 07.10.2021 MESSAGES (message = ...)

    @NotEmpty
    @NotNull
    @Size(min = 2, max = 30)
    private String firstName;

    @NotEmpty
    @NotNull
    @Size(min = 2, max = 30)
    private String lastName;

    @NotEmpty
    @NotNull
    @Email
    private String email;

    @NotEmpty
    @NotNull
    @Size(min = 5, max = 30)
    private String password;
}
