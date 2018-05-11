package com.infosystem.springmvc.dto;

import com.infosystem.springmvc.model.Tariff;
import com.infosystem.springmvc.model.TariffOption;

import java.util.List;

public class TariffPageDto {
    private Tariff tariff;
    private List<TariffOption> options;

    public TariffPageDto() {
    }

    public TariffPageDto(Tariff tariff, List<TariffOption> options) {
        this.tariff = tariff;
        this.options = options;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public List<TariffOption> getOptions() {
        return options;
    }

    public void setOptions(List<TariffOption> options) {
        this.options = options;
    }
}
