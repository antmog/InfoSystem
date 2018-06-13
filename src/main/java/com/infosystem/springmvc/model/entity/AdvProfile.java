package com.infosystem.springmvc.model.entity;

import com.infosystem.springmvc.model.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "ADV_PROFILE")
public class AdvProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADV_PROFILE_ID")
    private Integer id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private Status status = Status.INACTIVE;

    public AdvProfile(){

    }

    public AdvProfile(String name) {
        this.name = name;
    }

    @OneToMany(
            mappedBy = "advProfile",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<AdvProfileTariffs> advProfileTariffsList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdvProfile that = (AdvProfile) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
