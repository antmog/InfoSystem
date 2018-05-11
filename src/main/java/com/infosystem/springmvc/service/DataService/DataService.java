package com.infosystem.springmvc.service.DataService;

import com.infosystem.springmvc.dto.AdminPanelDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface DataService {
    AdminPanelDto getAdminPanelData();
}
