package com.websystique.springmvc.controller;

import com.websystique.springmvc.dto.GetTarifAsJsonDto;
import com.websystique.springmvc.model.Contract;
import com.websystique.springmvc.model.Tariff;
import com.websystique.springmvc.model.TariffOption;
import com.websystique.springmvc.service.ContractService;
import com.websystique.springmvc.service.TariffOptionService;
import com.websystique.springmvc.service.TariffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.websystique.springmvc.service.UserService;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/")
public class DataController {

    @Autowired
    UserService userService;

    @Autowired
    ContractService contractService;

    @Autowired
    TariffOptionService tariffOptionService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    TariffService tariffService;

    @RequestMapping(value = "/sss", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
    public@ResponseBody String s (){
        return " s";
    }



}

