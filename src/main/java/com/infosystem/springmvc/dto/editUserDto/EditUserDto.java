package com.infosystem.springmvc.dto.editUserDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class EditUserDto  extends  CustomerEditUserDto{

    @Size(min = 4, max = 32)
    private String password;
}
