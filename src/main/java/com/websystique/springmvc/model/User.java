package com.websystique.springmvc.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@Entity
@Table(name = "USERS")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", unique = true)
    private Integer id;

    @NotEmpty
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @NotEmpty
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @NotEmpty
    @Column(name = "ADRESS", nullable = false)
    private String adress;

    @NotEmpty
    @Column(name = "BIRTH_DATE", nullable = false)
    private String birthDate;

    @NotEmpty
    @Column(name = "PASSPORT", nullable = false, unique = true)
    private Integer passport;


    @NotEmpty
    @Column(name = "MAIL", nullable = false, unique = true)
    private String mail;

    @NotEmpty
    @Column(name = "LOGIN", nullable = false, unique = true)
    private String login;

    @NotEmpty
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user")
    @NotEmpty
    @Column(name = "USER_CONTRACTS", nullable = false)
    private Set<Contract> userContracts = new HashSet<Contract>();

}
