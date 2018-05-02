package com.websystique.springmvc.model;

import java.io.Serializable;
import java.util.Date;
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
    @Column(name = "USER_ID")
    private Integer id;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "ADRESS", nullable = false)
    private String address;


    @Column(name = "BIRTH_DATE", nullable = false)
    private String birthDate;


    @Column(name = "PASSPORT_ID", nullable = false, unique = true)
    private Integer passport;


    @Column(name = "MAIL", nullable = false, unique = true)
    private String mail;

    @Column(name = "LOGIN", nullable = false, unique = true)
    private String login;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private Status status = Status.ACTIVE;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    @Column(name = "USER_CONTRACTS", nullable = false)
    private Set<Contract> userContracts = new HashSet<Contract>();

    @Override
    public String toString() {
        return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName
                + ", address=" + address + ", birthDate=" + birthDate+ ", passport=" + passport +
                ", role=" + role +  ", mail=" + mail +  ", login=" +  login  + ", status=" + status + "]";

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
