package com.infosystem.springmvc.dto;

import com.infosystem.springmvc.model.entity.TariffOption;

import java.util.List;

public class TariffOptionPageDto {
    private TariffOption tariffOption;
    private List<TariffOption> options;

    public TariffOptionPageDto(TariffOption tariffOption, List<TariffOption> options) {
        this.tariffOption = tariffOption;
        this.options = options;
    }

    public TariffOptionPageDto() {
    }

    public TariffOption getTariffOption() {
        return tariffOption;
    }

    public void setTariffOption(TariffOption tariffOption) {
        this.tariffOption = tariffOption;
    }

    public List<TariffOption> getOptions() {
        return options;
    }

    public void setOptions(List<TariffOption> options) {
        this.options = options;
    }
}
