package com.infosystem.springmvc.dto;

import com.infosystem.springmvc.model.Role;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

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
    @Size(min = 6, max = 32)
    private String passport;

    @Email
    @Size(min = 6, max = 32)
    private String mail;

    @Size(min = 6, max = 32)
    private String login;

    @Size(min = 6, max = 32)
    private String password;

    @NotNull
    private Role role;

    public AddUserDto(String firstName, String lastName, String address, Date birthDate, String passport, String mail, String login, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthDate = birthDate;
        this.passport = passport;
        this.mail = mail;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public AddUserDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
