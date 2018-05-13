package com.infosystem.springmvc.model.entity;

import com.infosystem.springmvc.model.enums.Status;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "TARIFF")
public class Tariff implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TARIFF_ID")
    private Integer id;

    @Column(name="NAME", unique=true, nullable=false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "TARIFF_STATUS", nullable = false)
    private Status status = Status.ACTIVE;

    /**
     * per month
     * Allows to use the tariff -> allows to use the options (options have its own cost/price)
     */
    @Column(name="PRICE", nullable=false)
    private Double price;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "AVAILABLE_OPTIONS",
            joinColumns = {@JoinColumn(name = "TARIFF_ID", nullable = false, referencedColumnName = "TARIFF_ID")},
            inverseJoinColumns = {@JoinColumn(name = "OPTION_ID", nullable = false, referencedColumnName = "OPTION_ID")})
    private Set<TariffOption> availableOptions = new HashSet<TariffOption>();

    public void delAvailableOptions(Set<TariffOption> tariffOptionSet){
        availableOptions.removeAll(tariffOptionSet);
    }

    public void addAvailableOptions(Set<TariffOption> tariffOptionSet){
        availableOptions.addAll(tariffOptionSet);
    }
//    @Override
//    public String toString() {
//        return "Tariff [id=" + id + ", name=" + name + ", price=" + price +"]";
//    }
@Override
public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (!(obj instanceof Tariff))
        return false;
    Tariff other = (Tariff) obj;
    if (this.id == null) {
        return other.id == null;
    } else return id.equals(other.id);
}


}
