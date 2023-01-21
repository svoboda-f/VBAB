package cz.osu.vbap.model.auth;

import java.time.LocalDate;

public record RegisterCredentials(String email, String username, String password, String firstName, String lastName,
        LocalDate dateOfBirth) {

}
