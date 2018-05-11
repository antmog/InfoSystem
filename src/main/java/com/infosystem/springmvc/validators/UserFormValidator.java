package com.infosystem.springmvc.validators;

import com.infosystem.springmvc.dto.AddUserDto;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserFormValidator implements Validator {



        @Override
        public boolean supports(Class<?> aClass) {
            return AddUserDto.class.equals(aClass);
        }

        @Override
        public void validate(Object o, Errors errors) {

            AddUserDto driver = (AddUserDto) o;

            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "first_name", "NotEmpty.driverForm.first_name");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "last_name", "NotEmpty.driverForm.last_name");

//            if (!driver.getFirst_name().matches("^\\D*$")) {
//                errors.rejectValue("first_name", "Digit.driverForm.firstAndLastName");
//            }
//
//            if (!driver.getLast_name().matches("^\\D*$")) {
//                errors.rejectValue("last_name", "Digit.driverForm.firstAndLastName");
//            }

            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.email", "NotEmpty.userForm.email");
        }


}
