package com.infosystem.springmvc.validators;

import com.infosystem.springmvc.dto.ChangePasswordDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.service.TariffOptionServiceImpl;
import com.infosystem.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ChangePasswordValidator implements Validator {

    private final UserService userService;

    @Autowired
    public ChangePasswordValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return ChangePasswordDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ChangePasswordDto changePasswordDto = (ChangePasswordDto) o;
        try {
            if (!userService.checkIfUserPasswordMatches(changePasswordDto)) {
                errors.rejectValue("password", "Matches.ChangePasswordDto.password");
            }
        } catch (DatabaseException e) {
            errors.rejectValue("password", "Custom.ChangePasswordDto.password");
        }
    }

    public void validateWithException(Object o, Errors errors) throws DatabaseException {
        ChangePasswordDto changePasswordDto = (ChangePasswordDto) o;
        if (!userService.checkIfUserPasswordMatches(changePasswordDto)) {
            errors.rejectValue("password", "Matches.ChangePasswordDto.password");
        }
    }
}
