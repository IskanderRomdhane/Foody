package com.Foody.Foody.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest {

    @NotEmpty(message = "Firstname is mandatory")
    @NotNull(message = "Firstname is mandatory")
    private String firstname;

    @NotEmpty(message = "Lastname is mandatory")
    @NotNull(message = "Lastname is mandatory")
    private String lastname;

    @Email(message = "Email is not well formatted")
    @NotEmpty(message = "Email is mandatory")
    @NotNull(message = "Email is mandatory")
    private String email;

    @NotEmpty(message = "Password is mandatory")
    @NotNull(message = "Password is mandatory")
    @Size(min = 8, message = "Password should be 8 characters long minimum")
    private String password;

    @NotNull(message = "Weight is mandatory")
    private Float weight;

    @NotNull(message = "Height is mandatory")
    private Float height;

    @NotEmpty(message = "Gender is mandatory")
    @NotNull(message = "Gender is mandatory")
    private String gender;

    @NotNull(message = "Age is mandatory")
    private Integer age;

    @NotEmpty(message = "Goal is mandatory")
    @NotNull(message = "Goal is mandatory")
    private String goal;

    @NotEmpty(message = "Activity Level is mandatory")
    @NotNull(message = "Activity Level is mandatory")
    private String activityLevel;
}
