package com.infosystem.springmvc.dto;

import com.infosystem.springmvc.model.enums.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
public class AddUserDto {
    @Size(min = 6, max = 32)
    private String firstName;

    @Size(min = 6, max = 32)
    private String lastName;

    @Size(min = 6, max = 32)
    private String address;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @Pattern(regexp = "\\d*")
    @Size(min = 6, max = 9)
    private String passport;

    @Email
    @Size(min = 6, max = 32)
    private String mail;

    @Size(min = 6, max = 32)
    private String login;

    @Size(min = 6, max = 32)
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;
}
