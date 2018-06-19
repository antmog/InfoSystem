package com.infosystem.springmvc.validators;

import com.infosystem.springmvc.dto.editUserDto.EditUserDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.model.entity.User;
import com.infosystem.springmvc.service.TariffOptionServiceImpl;
import com.infosystem.springmvc.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EditUserValidator implements Validator {

    private static final Logger logger = Logger.getLogger(TariffOptionServiceImpl.class);
    private final UserService userService;

    @Autowired
    public EditUserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return EditUserDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        EditUserDto editUserDto = (EditUserDto) o;

        User user = new User();
        try {
            user = userService.findById(editUserDto.getId());
        } catch (DatabaseException e) {
            String exceptionMessage = e.getMessage();
            logger.error(exceptionMessage);
            errors.rejectValue("mail", "Custom.editUserDto.mail");
        }
        if(!user.getMail().equals(editUserDto.getMail())){
            if (userService.checkParameterNotUnique("mail", editUserDto.getMail())) {
                errors.rejectValue("mail", "Unique.addUserDto.mail");
            }
        }
    }
}
