package com.Bcsapat._ib153l14b.EliteFoods.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="cim")
public class Cim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long azonosito;

    private String orszag;
    private String varos;
    private String iranyitoszam;
    private String kozterulet;
    private String hazszam;
}
