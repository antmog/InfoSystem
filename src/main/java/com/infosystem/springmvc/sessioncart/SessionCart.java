package com.infosystem.springmvc.sessioncart;


import com.infosystem.springmvc.dto.DeleteFromCartDto;
import com.infosystem.springmvc.dto.EditContractDto;
import com.infosystem.springmvc.dto.TariffOptionDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.exception.ValidationException;
import com.infosystem.springmvc.model.entity.Contract;
import com.infosystem.springmvc.model.entity.TariffOption;
import com.infosystem.springmvc.model.enums.Status;
import com.infosystem.springmvc.service.ContractService;
import com.infosystem.springmvc.service.TariffOptionService;
import com.infosystem.springmvc.util.CustomModelMapper;
import com.infosystem.springmvc.util.OptionsRulesChecker;
import javassist.bytecode.analysis.Executor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import sun.rmi.runtime.Log;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component("sessionCart")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class SessionCart {
    private Map<Integer, Set<TariffOptionDto>> options = new HashMap<>();
    private Integer count = 0;

    @Autowired
    private CustomModelMapper modelMapperWrapper;
    @Autowired
    private ContractService contractService;
    @Autowired
    private OptionsRulesChecker optionsRulesChecker;
    @Autowired
    TariffOptionService tariffOptionService;

    @Transactional
    public void addCartItems(EditContractDto editContractDto) throws DatabaseException, LogicException {
        Integer contractId = editContractDto.getContractId();
        Contract contract = contractService.findById(contractId);
        if(!contract.getStatus().equals(Status.ACTIVE)){
            throw new LogicException("Contract is not active. Refresh page.");
        }
        Set<TariffOptionDto> newSet = new HashSet<>();
        Set<TariffOption> toBeAddedOptions = modelMapperWrapper.mapToTariffOptionSet(editContractDto.getTariffOptionDtoList());

        optionsRulesChecker.checkAddToContractCustomer(contractId, toBeAddedOptions);
        optionsRulesChecker.checkIfContractAlreadyHave(contract, toBeAddedOptions);
        if(!options.isEmpty()){
            if(options.containsKey(contractId)){
                Set<TariffOptionDto> currentOptions = new HashSet<>(options.get(contractId));
                currentOptions.retainAll(editContractDto.getTariffOptionDtoList());
                if(!currentOptions.isEmpty()){
                    StringBuilder sb = new StringBuilder();
                    for (TariffOptionDto tariffOptionDto : currentOptions) {
                        sb.append("Option ").append(tariffOptionDto.getName()).append(" for contract ").append(contractId)
                                .append(" alrdy in cart.\n");
                    }
                    throw new LogicException(sb.toString());
                }
            }
        }

        if(options.containsKey(contractId)){
            newSet.addAll(options.get(contractId));
        }
        newSet.addAll(modelMapperWrapper.mapToTariffOptionDtoSet(toBeAddedOptions));
        options.put(contractId, newSet);
        countItems();
    }

    @Transactional
    public void delCartItems(DeleteFromCartDto deleteFromCartDto) throws DatabaseException, ValidationException {
        if(options.containsKey(deleteFromCartDto.getContractId())){
            Set<TariffOptionDto> currentOptions = options.get(deleteFromCartDto.getContractId());
            TariffOption tariffOption = tariffOptionService.findById(deleteFromCartDto.getOptionId());
            TariffOptionDto tariffOptionDto = modelMapperWrapper.mapToTariffOptionDto(tariffOption);
            if(!currentOptions.remove(tariffOptionDto)){
                throw new ValidationException("No such element, hacker.");
            }
            options.put(deleteFromCartDto.getContractId(),currentOptions);
            if(currentOptions.isEmpty()){
                options.remove(deleteFromCartDto.getContractId());
            }
            countItems();
        }
    }

    public void countItems(){
        count = 0;
        options.forEach((k,v)-> count+=v.size());
    }
}
