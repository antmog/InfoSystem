package com.websystique.springmvc.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
@Data
@Entity
@Table(name = "TARIFF_OPTIONS")
public class TariffOption implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "OPTION_ID")
    private Integer id;

    @NotEmpty
    @Column(name="NAME", unique=true, nullable=false)
    private String name;

    @NotEmpty
    @Column(name="PRICE", nullable=false)
    private Double price;

    @NotEmpty
    @Column(name="COSTOFADD", nullable=false)
    private Double costOfAdd;


    @NotEmpty
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "RELATED_OPTIONS",
            joinColumns = { @JoinColumn(name = "OPTION_ID",nullable = false, referencedColumnName = "OPTION_ID") },
            inverseJoinColumns = { @JoinColumn(name = "RELATED_OPTION_ID",nullable = false, referencedColumnName = "OPTION_ID") })
    private Set<TariffOption> relatedTariffOptions = new HashSet<TariffOption>();


    @NotEmpty
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "EXCLUDING_OPTIONS",
            joinColumns = { @JoinColumn(name = "OPTION_ID",nullable = false, referencedColumnName = "OPTION_ID") },
            inverseJoinColumns = { @JoinColumn(name = "EXCLUDING_OPTION_ID",nullable = false, referencedColumnName = "OPTION_ID") })
    private Set<TariffOption> excludingTariffOptions = new HashSet<TariffOption>();





}
