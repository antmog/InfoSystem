package com.infosystem.springmvc.controller.rest;

import com.infosystem.springmvc.dto.adv.AdvInitialDataDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.service.adv.AdvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/adv")
public class AdvController {

    private final static Logger LOGGER = Logger.getLogger(AdvService.class.toString());
    private AdvService advService;

    @Autowired
    public AdvController(AdvService advService){
        this.advService = advService;
    }

    @RequestMapping(value = "/getInitialAdvData")
    public AdvInitialDataDto getInitialAdvData() throws DatabaseException {
        AdvInitialDataDto advInitialDataDto = advService.getInitialAdvData();
        LOGGER.info(() -> "InitialAdvData request.");
        return advInitialDataDto;
    }

}
