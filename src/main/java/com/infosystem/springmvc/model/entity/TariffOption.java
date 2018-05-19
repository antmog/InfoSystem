package com.infosystem.springmvc.model.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "RELATED_OPTIONS",
            joinColumns = {@JoinColumn(name = "OPTION_ID", nullable = false, referencedColumnName = "OPTION_ID")},
            inverseJoinColumns = {@JoinColumn(name = "RELATED_OPTION_ID", nullable = false, referencedColumnName = "OPTION_ID")})
    private Set<TariffOption> relatedTariffOptions = new HashSet<TariffOption>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "EXCLUDING_OPTIONS",
            joinColumns = {@JoinColumn(name = "OPTION_ID", nullable = false, referencedColumnName = "OPTION_ID")},
            inverseJoinColumns = {@JoinColumn(name = "EXCLUDING_OPTION_ID", nullable = false, referencedColumnName = "OPTION_ID")})
    private Set<TariffOption> excludingTariffOptions = new HashSet<TariffOption>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "IS_RELATED_FOR",
            joinColumns = {@JoinColumn(name = "OPTION_ID", nullable = false, referencedColumnName = "OPTION_ID")},
            inverseJoinColumns = {@JoinColumn(name = "RELATED_FOR", nullable = false, referencedColumnName = "OPTION_ID")})
    private Set<TariffOption> isRelatedFor = new HashSet<>();

    @Override
    public String toString() {
        return "TariffOption [id=" + id + ", name=" + name + ", price=" + price
                + ", costOfAdd=" + costOfAdd + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TariffOption that = (TariffOption) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(price, that.price) &&
                Objects.equals(costOfAdd, that.costOfAdd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, costOfAdd);
    }
}
