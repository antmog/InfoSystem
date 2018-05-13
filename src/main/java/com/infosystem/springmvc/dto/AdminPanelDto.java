package com.infosystem.springmvc.dto;

import com.infosystem.springmvc.model.entity.Tariff;
import com.infosystem.springmvc.model.entity.TariffOption;
import com.infosystem.springmvc.model.entity.User;

import java.util.List;

public class AdminPanelDto {

    private List<User> users;
    private List<Tariff> tariffs;
    private List<TariffOption> tariffOptions;

    public AdminPanelDto() {
    }

    public AdminPanelDto(List<User> users, List<Tariff> tariffs, List<TariffOption> tariffOptions) {
        this.users = users;
        this.tariffs = tariffs;
        this.tariffOptions = tariffOptions;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Tariff> getTariffs() {
        return tariffs;
    }

    public void setTariffs(List<Tariff> tariffs) {
        this.tariffs = tariffs;
    }

    public List<TariffOption> getTariffOptions() {
        return tariffOptions;
    }

    public void setTariffOptions(List<TariffOption> tariffOptions) {
        this.tariffOptions = tariffOptions;
    }
}
