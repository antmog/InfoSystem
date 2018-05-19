package com.infosystem.springmvc.model.entity;

import com.infosystem.springmvc.model.enums.Status;
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
@Table(name = "TARIFFS")
public class Tariff implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TARIFF_ID")
    private Integer id;

    @Column(name = "NAME", unique = true, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "TARIFF_STATUS", nullable = false)
    private Status status = Status.ACTIVE;

    /**
     * per month
     * Allows to use the tariff -> allows to use the options (options have its own cost/price)
     */
    @Column(name = "PRICE", nullable = false)
    private Double price;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "AVAILABLE_OPTIONS",
            joinColumns = {@JoinColumn(name = "TARIFF_ID", nullable = false, referencedColumnName = "TARIFF_ID")},
            inverseJoinColumns = {@JoinColumn(name = "OPTION_ID", nullable = false, referencedColumnName = "OPTION_ID")})
    private Set<TariffOption> availableOptions = new HashSet<TariffOption>();

    @Override
    public String toString() {
        return "Tariff [id=" + id + ", name=" + name + ", price=" + price + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, price);
    }

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
