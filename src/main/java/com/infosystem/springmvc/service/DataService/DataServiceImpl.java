package com.infosystem.springmvc.service.DataService;

import com.infosystem.springmvc.dto.AdminPanelDto;
import com.infosystem.springmvc.service.TariffOptionService;
import com.infosystem.springmvc.service.TariffService;
import com.infosystem.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("dataService")
@Transactional
public class DataServiceImpl implements DataService {

    @Autowired
    TariffOptionService tariffOptionService;
    @Autowired
    TariffService tariffService;
    @Autowired
    UserService userService;

    @Override
    public AdminPanelDto getAdminPanelData() {
        return new AdminPanelDto(userService.findFirstUsers(),
                tariffService.findFirstTariffs(),tariffOptionService.findFirstTariffOptions());
    }
}
