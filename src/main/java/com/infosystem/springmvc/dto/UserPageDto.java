package com.infosystem.springmvc.dto;

import com.infosystem.springmvc.model.entity.Contract;
import com.infosystem.springmvc.model.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;

@Data
@NoArgsConstructor
public class UserPageDto {

    private int id;

    private String firstName;

    private String lastName;

    private String address;

    //todo string? date?
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    private String birthDate;

    private String passport;

    private String mail;

    private Set<Contract> userContracts = new HashSet<Contract>();

    public TreeSet<Contract> getUserContracts(){
        TreeSet<Contract> options = new TreeSet<>(Comparator.comparingInt(Contract::getId));
        options.addAll(userContracts);
        return options;
    }

    private Status status;

    private Double balance;


}
