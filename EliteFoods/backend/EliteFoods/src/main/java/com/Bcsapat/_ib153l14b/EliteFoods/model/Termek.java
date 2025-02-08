package com.Bcsapat._ib153l14b.EliteFoods.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name="termek")
public class Termek {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long azonosito;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "rendeles_id")
    private Rendeles rendeles;

    private String nev;

    private int ar;

    private String kep;

    private String lejarati_datum;

    @Setter
    private int mennyiseg;

    @ManyToOne
    @JoinColumn(name = "kategoria_id")
    @ToString.Exclude
    private Kategoria kategoria;

}
