package com.infosystem.springmvc.dto;


import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.model.entity.TariffOption;
import com.infosystem.springmvc.service.ContractService;
import com.infosystem.springmvc.util.CustomModelMapper;
import com.infosystem.springmvc.util.OptionsRulesChecker;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component("sessionCart")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class SessionCart {
    private Map<Integer, Set<TariffOptionDto>> options = new HashMap<>();

    @Autowired
    private CustomModelMapper modelMapperWrapper;
    @Autowired
    private ContractService contractService;
    @Autowired
    private OptionsRulesChecker optionsRulesChecker;

    public void addCartItems(EditContractDto editContractDto) throws DatabaseException, LogicException {
        Set<TariffOptionDto> newSet = new HashSet<>();
        Set<TariffOption> toBeAddedOptions = modelMapperWrapper.mapToTariffOptionSet(editContractDto.getTariffOptionDtoList());

        optionsRulesChecker.checkAddToContractCustomer(editContractDto.getContractId(), toBeAddedOptions);

        if(options.containsKey(editContractDto.getContractId())){
            newSet.addAll(options.get(editContractDto.getContractId()));
        }
        newSet.addAll(modelMapperWrapper.mapToTariffOptionDtoSet(toBeAddedOptions));
        options.put(editContractDto.getContractId(), newSet);
    }

    public void delCartItems(EditContractDto editContractDto) {
        Set<TariffOption> toBeDeletedOptions = modelMapperWrapper.mapToTariffOptionSet(editContractDto.getTariffOptionDtoList());
        if(options.containsKey(editContractDto.getContractId())){
            Set<TariffOption> currentOptions = modelMapperWrapper.mapToTariffOptionSet(options.get(editContractDto.getContractId()));
            currentOptions.removeAll(toBeDeletedOptions);
        }
    }
}
