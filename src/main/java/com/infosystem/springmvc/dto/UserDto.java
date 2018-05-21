package com.infosystem.springmvc.dto;

import com.infosystem.springmvc.model.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
public class UserDto {

    private int id;

    private String firstName;

    private String lastName;

    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    private String passport;

    private String mail;

    private String login;

    private String password;

    private String role;

    private Integer contractsCount;

    private Status status;

    private Double balance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return id == userDto.id &&
                Objects.equals(firstName, userDto.firstName) &&
                Objects.equals(lastName, userDto.lastName) &&
                Objects.equals(address, userDto.address) &&
                Objects.equals(birthDate, userDto.birthDate) &&
                Objects.equals(passport, userDto.passport) &&
                Objects.equals(mail, userDto.mail) &&
                Objects.equals(login, userDto.login) &&
                Objects.equals(password, userDto.password) &&
                Objects.equals(role, userDto.role) &&
                status == userDto.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, address, birthDate, passport, mail, login, password, role, status);
    }
}
