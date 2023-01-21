package cz.osu.vbap.model.rest;

import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public record PrivateUserInfo(
        @NotEmpty(message = "First name can't be empty") String firstName,
        @NotEmpty(message = "Last name can't be empty") String lastName,
        @NotNull(message = "Date of birth can't be null") @Past(message = "Date of birth has to be in the past") LocalDate dateOfBirth) {

}
