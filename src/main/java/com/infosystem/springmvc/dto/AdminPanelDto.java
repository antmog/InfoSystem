package com.infosystem.springmvc.dto;

import com.infosystem.springmvc.model.entity.Tariff;
import com.infosystem.springmvc.model.entity.TariffOption;
import com.infosystem.springmvc.model.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;

@Data
@NoArgsConstructor
public class AdminPanelDto {
    //todo edit
    private List<User> users;
    private List<Tariff> tariffs;
    private List<TariffOption> tariffOptions;

    public AdminPanelDto(List<User> users, List<Tariff> tariffs, List<TariffOption> tariffOptions) {
        this.users = users;
        this.tariffs = tariffs;
        this.tariffOptions = tariffOptions;
    }
}
