package com.example.springcinemawebapp.properties;

public interface TechnicalTextConstants {
    String USER_NOT_FOUND_MSG = "User not found";
    String EMAIL_IS_ALREADY_TAKEN_MSG = "Email is already taken";
    String TIME_OVERLAPPING_MSG = "Time is overlapping";
    String NO_SUCH_FIELD_MSG = "No such field: ";
    String WRONG_MOVIE_SESSION_TIME_MSG = "Wrong movie session time";
    String DEFAULT_EXCEPTION_MSG = "An error occurred";

    // regex ^\p{Lu}\p{Ll}*([`'-]\p{Lu}\p{Ll}*)*$ for names like I-O
    // regex ^\p{Lu}\p{Ll}+([`'-]\p{Lu}\p{Ll}+)*$ for names like Fu-Fo <--
    String REAL_NAME_REGEX = "^\\p{Lu}\\p{Ll}+([`'-]\\p{Lu}\\p{Ll}+)*$";

    // regex with look-ahead for password that must contain 1 uppercase, 1 lowercase, 1 number, may contain any symbols
    String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{3,}$";
}
