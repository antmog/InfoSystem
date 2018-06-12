package com.infosystem.springmvc.validators;

import com.infosystem.springmvc.dto.SearchByNumberDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SearchUserByNumberValidator implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return SearchByNumberDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        SearchByNumberDto searchByNumberDto = (SearchByNumberDto) o;

        if (searchByNumberDto.getPhoneNumber().length()<6 || searchByNumberDto.getPhoneNumber().length()>32) {
            errors.rejectValue("phoneNumber", "Custom.SearchByNumberDto.phoneNumberLength");
        }

        if (!searchByNumberDto.getPhoneNumber().matches("\\d*")) {
            errors.rejectValue("phoneNumber", "Custom.SearchByNumberDto.phoneNumberDigits");
        }
    }
}
