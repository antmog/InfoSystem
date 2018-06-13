package com.infosystem.springmvc.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "ADV_PROFILE_TARIFFS")
public class AdvProfileTariffs {

    @EmbeddedId
    private AdvProfileTariffId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("advProfileId")
    private AdvProfile advProfile;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("tariffId")
    private Tariff tariff;

    @Column(name="IMG")
    private String img;

    private AdvProfileTariffs(){}

    public AdvProfileTariffs(AdvProfile advProfile, Tariff tariff) {
        this.advProfile = advProfile;
        this.tariff = tariff;
        this.id = new AdvProfileTariffId(advProfile.getId(),tariff.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdvProfileTariffs that = (AdvProfileTariffs) o;
        return Objects.equals(advProfile, that.advProfile) &&
                Objects.equals(tariff, that.tariff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(advProfile, tariff);
    }
}
