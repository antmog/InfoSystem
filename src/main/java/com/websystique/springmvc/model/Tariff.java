package com.websystique.springmvc.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
@Data
@Entity
@Table(name = "TARIFF")
public class Tariff implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TARIFF_ID")
    private Integer id;

    @NotEmpty
    @Column(name="NAME", unique=true, nullable=false)
    private String name;

    @NotEmpty
    @Column(name="PRICE", nullable=false)
    private Double price;

}
