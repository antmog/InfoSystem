package com.infosystem.springmvc.dto;

import com.infosystem.springmvc.model.entity.Contract;
import com.infosystem.springmvc.model.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserPageDto {

    private int id;

    private String firstName;

    private String lastName;

    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    private String passport;

    private String mail;

    private Set<Contract> userContracts = new HashSet<Contract>();

    private Status status;

    private Double balance;

}
