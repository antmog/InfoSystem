package com.websystique.springmvc.model;

import com.websystique.springmvc.dto.ContractUserIdDto;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "CONTRACTS")
public class Contract implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTRACT_ID")
    private Integer id;

    @Column(name = "PHONE_NUMBER", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "CONTRACT_PRICE", nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "CONTRACT_STATUS", nullable = false)
    private Status status = Status.ACTIVE;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "CONTRACT_USER_ID", nullable = false, referencedColumnName = "USER_ID")
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "CONTRACT_TARIFF_ID", nullable = false, referencedColumnName = "TARIFF_ID")
    private Tariff tariff;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ACTIVE_OPTIONS",
            joinColumns = {@JoinColumn(name = "CONTRACT_ID", nullable = false, referencedColumnName = "CONTRACT_ID")},
            inverseJoinColumns = {@JoinColumn(name = "OPTION_ID", nullable = false, referencedColumnName = "OPTION_ID")})
    private Set<TariffOption> activeOptions = new HashSet<TariffOption>();

    @Override
    public String toString() {
        return "Contract [id=" + id + ", user=" + user + ", phoneNumber=" + phoneNumber + ", tariff=" +  ", status=" + status + "]";
    }
}
