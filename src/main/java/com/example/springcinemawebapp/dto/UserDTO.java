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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    // regex ^\p{Lu}\p{Ll}*([`'-]\p{Lu}\p{Ll}*)*$ for names like I-O
    // regex ^\p{Lu}\p{Ll}+([`'-]\p{Lu}\p{Ll}+)*$ for names like Fu-Fo <--
    public static final String realNameRegex = "^\\p{Lu}\\p{Ll}+([`'-]\\p{Lu}\\p{Ll}+)*$";

    // regex with look-aheads for password that must contain 1 uppercase, 1 lowercase, 1 number, may contain any symbols
    public static final String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{3,}$";

    @Pattern(regexp = realNameRegex, message = "{message.wrong.format.name.first}")
    @NotEmpty(message = "{message.wrong.value.empty}")
    @NotNull(message = "{message.wrong.value.null}")
    @Size(min = 2, max = 30, message = "{message.wrong.size.name.first}")
    private String firstName;

    @Pattern(regexp = realNameRegex, message = "{message.wrong.format.name.last}")
    @NotEmpty(message = "{message.wrong.value.empty}")
    @NotNull(message = "{message.wrong.value.null}")
    @Size(min = 2, max = 30, message = "{message.wrong.size.name.last}")
    private String lastName;

    @NotEmpty(message = "{message.wrong.value.empty}")
    @NotNull(message = "{message.wrong.value.null}")
    @Email(message = "{message.wrong.format.email}")
    private String email;

    @Pattern(regexp = passwordRegex, message = "{message.wrong.format.password}")
    @NotEmpty(message = "{message.wrong.value.empty}")
    @NotNull(message = "{message.wrong.value.null}")
    @Size(min = 5, max = 30, message = "{message.wrong.size.password}")
    private String password;
}
