package com.Bcsapat._ib153l14b.EliteFoods.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="rendeles")
public class Rendeles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long azonosito;
    private int osszeg;
    private Date datum;

    @OneToMany(mappedBy = "rendeles", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Termek> termekek=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "felhasznalo_id")
    private Felhasznalo felhasznaloAzonostio;

    public Rendeles(int osszeg, Date datum,  Felhasznalo felhasznaloAzonostio) {
        this.osszeg = osszeg;
        this.datum = datum;
        this.felhasznaloAzonostio = felhasznaloAzonostio;
    }

    public Rendeles() {
    }

    public void addTermek(Termek termek) {
        termekek.add(termek); // Hozzáadjuk a terméket a rendelés terméklistájához
        termek.setRendeles(this); // Beállítjuk a termék rendelését
    }
}
