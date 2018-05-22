package com.infosystem.springmvc.validators;

import com.infosystem.springmvc.dto.AddTariffOptionDto;
import com.infosystem.springmvc.service.TariffOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TariffOptionFormValidator implements Validator {

    private final TariffOptionService tariffOptionService;

    @Autowired
    public TariffOptionFormValidator(TariffOptionService tariffOptionService) {
        this.tariffOptionService = tariffOptionService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return AddTariffOptionDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        AddTariffOptionDto addTariffOptionDto = (AddTariffOptionDto) o;

        if (!tariffOptionService.isTariffOptionUnique(addTariffOptionDto.getName())) {
            errors.rejectValue("name", "Unique.addTariffOptionDto.name");
        }
    }
}
