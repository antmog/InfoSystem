package com.infosystem.springmvc.dto.editUserDto;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class EditUserDto {

    private int id;

    @Size(min = 4, max = 32)
    private String firstName;

    @Size(min = 4, max = 32)
    private String lastName;

    @Size(min = 6, max = 32)
    private String address;

    @Email
    @Size(min = 6, max = 32)
    private String mail;

    @Size(min = 4, max = 32)
    private String password;
}
