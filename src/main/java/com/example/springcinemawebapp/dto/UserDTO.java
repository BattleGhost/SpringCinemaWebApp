package com.example.springcinemawebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;

import static com.example.springcinemawebapp.properties.TechnicalTextConstants.REAL_NAME_REGEX;
import static com.example.springcinemawebapp.properties.TechnicalTextConstants.PASSWORD_REGEX;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    @Pattern(regexp = REAL_NAME_REGEX, message = "{message.wrong.format.name.first}")
    @NotEmpty(message = "{message.wrong.value.empty}")
    @NotNull(message = "{message.wrong.value.null}")
    @Size(min = 2, max = 30, message = "{message.wrong.size.name.first}")
    private String firstName;

    @Pattern(regexp = REAL_NAME_REGEX, message = "{message.wrong.format.name.last}")
    @NotEmpty(message = "{message.wrong.value.empty}")
    @NotNull(message = "{message.wrong.value.null}")
    @Size(min = 2, max = 30, message = "{message.wrong.size.name.last}")
    private String lastName;

    @NotEmpty(message = "{message.wrong.value.empty}")
    @NotNull(message = "{message.wrong.value.null}")
    @Email(message = "{message.wrong.format.email}")
    private String email;

    @Pattern(regexp = PASSWORD_REGEX, message = "{message.wrong.format.password}")
    @NotEmpty(message = "{message.wrong.value.empty}")
    @NotNull(message = "{message.wrong.value.null}")
    @Size(min = 5, max = 30, message = "{message.wrong.size.password}")
    private String password;
}
