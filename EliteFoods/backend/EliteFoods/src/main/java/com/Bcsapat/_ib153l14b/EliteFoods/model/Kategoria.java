package com.Bcsapat._ib153l14b.EliteFoods.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name = "kategoria")
public class Kategoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long azonosito;

    private String nev;

    @JsonIgnore
    @OneToMany(mappedBy = "kategoria")
    @ToString.Exclude
    private List<Termek> termekek;
}
