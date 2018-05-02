package com.websystique.springmvc.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

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

    @Override
    public String toString() {
        return "Tariff [id=" + id + ", name=" + name + ", price=" + price +"]";
    }

}
