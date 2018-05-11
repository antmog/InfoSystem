package com.infosystem.springmvc.validators;

import com.infosystem.springmvc.dto.AddUserDto;
import com.infosystem.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserFormValidator implements Validator {
    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return AddUserDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        AddUserDto addUserDto = (AddUserDto) o;

        if(userService.doesLoginExist(addUserDto.getLogin())){
            errors.rejectValue("login", "Unique.addUserDto.login");
        }
        if (userService.doesEmailExist(addUserDto.getMail())) {
            errors.rejectValue("mail", "Unique.addUserDto.mail");
        }
        if (userService.doesPassportExist(addUserDto.getPassport())) {
            errors.rejectValue("passport", "Unique.addUserDto.passport");
        }

    }


}
