package com.infosystem.springmvc.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infosystem.springmvc.model.enums.TariffOptionRule;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "TARIFF_OPTIONS")
public class TariffOption implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OPTION_ID")
    private Integer id;


    @Column(name = "NAME", unique = true, nullable = false)
    private String name;


    @Column(name = "PRICE", nullable = false)
    private Double price;


    @Column(name = "COSTOFADD", nullable = false)
    private Double costOfAdd;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "RELATED_OPTIONS",
            joinColumns = {@JoinColumn(name = "OPTION_ID", nullable = false, referencedColumnName = "OPTION_ID")},
            inverseJoinColumns = {@JoinColumn(name = "RELATED_OPTION_ID", nullable = false, referencedColumnName = "OPTION_ID")})
    private Set<TariffOption> relatedTariffOptions = new HashSet<TariffOption>();


    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "EXCLUDING_OPTIONS",
            joinColumns = {@JoinColumn(name = "OPTION_ID", nullable = false, referencedColumnName = "OPTION_ID")},
            inverseJoinColumns = {@JoinColumn(name = "EXCLUDING_OPTION_ID", nullable = false, referencedColumnName = "OPTION_ID")})
    private Set<TariffOption> excludingTariffOptions = new HashSet<TariffOption>();


    public void delTariffOptionRules(Set<TariffOption> tariffOptionSet, TariffOptionRule tariffOptionRule){
        if(tariffOptionRule.equals(TariffOptionRule.RELATED)){
            relatedTariffOptions.removeAll(tariffOptionSet);
        }
        if(tariffOptionRule.equals(TariffOptionRule.EXCLUDING)){
            excludingTariffOptions.removeAll(tariffOptionSet);
        }
    }

    public void addTariffOptionRules(Set<TariffOption> tariffOptionSet, TariffOptionRule tariffOptionRule){
        if(tariffOptionRule.equals(TariffOptionRule.RELATED)){
            relatedTariffOptions.addAll(tariffOptionSet);
        }
        if(tariffOptionRule.equals(TariffOptionRule.EXCLUDING)){
            excludingTariffOptions.addAll(tariffOptionSet);
        }
    }
//    @Override
//    public String toString() {
//        return "TariffOption [id=" + id + ", name=" + name + ", price=" + price
//                + ", costOfAdd=" + costOfAdd + "]";
//
//    }
//
//    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + ((id == null) ? 0 : id.hashCode());
//        return result;
//    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof TariffOption))
            return false;
        TariffOption other = (TariffOption) obj;
        if (this.id == null) {
            return other.id == null;
        } else return id.equals(other.id);
    }
}
