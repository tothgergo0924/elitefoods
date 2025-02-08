package com.Bcsapat._ib153l14b.EliteFoods.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name="jogosultsag")
public class Jogosultsag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long azonosito;

    private String nev;
    @OneToMany(mappedBy = "role")
    private Set<Felhasznalo> contacts;
}
