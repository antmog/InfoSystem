package com.infosystem.springmvc.dto;

import com.infosystem.springmvc.model.enums.Role;
import com.infosystem.springmvc.model.enums.Status;
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
import java.util.Objects;
import java.util.Set;

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

    @Pattern(regexp="^(ADMIN|CUSTOMER)$",message="invalid code")
    @NotNull
    private String role;

    private Set<ContractDto> userContracts;

    private Status status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddUserDto that = (AddUserDto) o;
        return Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(address, that.address) &&
                Objects.equals(birthDate, that.birthDate) &&
                Objects.equals(passport, that.passport) &&
                Objects.equals(mail, that.mail) &&
                Objects.equals(login, that.login) &&
                Objects.equals(password, that.password) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {

        return Objects.hash(firstName, lastName, address, birthDate, passport, mail, login, password, role);
    }
}
