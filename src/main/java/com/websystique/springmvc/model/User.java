package com.websystique.springmvc.model;

import java.io.Serializable;

import javax.persistence.*;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@Entity
@Table(name = "USERS")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Integer id;

    @NotEmpty
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotEmpty
    @Column(name = "MAIL", nullable = false, unique = true)
    private String mail;

    @NotEmpty
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    private Role role;

}
