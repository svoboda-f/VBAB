package cz.osu.vbab.model.auth;

import java.time.LocalDate;

public record RegisterCredentials(String email, String username, String password, String firstName, String lastName,
        LocalDate dateOfBirth) {

}
