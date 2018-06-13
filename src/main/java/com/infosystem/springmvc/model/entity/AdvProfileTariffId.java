package com.infosystem.springmvc.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class AdvProfileTariffId implements Serializable {
    @Column(name = "adv_profile_id")
    private Integer advProfileId;

    @Column(name = "tariff_id")
    private Integer tariffId;

    private AdvProfileTariffId(){}

    public AdvProfileTariffId(Integer advProfileId, Integer tariffId) {
        this.advProfileId = advProfileId;
        this.tariffId = tariffId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdvProfileTariffId that = (AdvProfileTariffId) o;
        return Objects.equals(advProfileId, that.advProfileId) &&
                Objects.equals(tariffId, that.tariffId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(advProfileId, tariffId);
    }
}
