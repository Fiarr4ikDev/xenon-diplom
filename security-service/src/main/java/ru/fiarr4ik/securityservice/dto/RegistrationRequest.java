package ru.fiarr4ik.securityservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {

    private String username;
    private String password;

}
