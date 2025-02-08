package com.Bcsapat._ib153l14b.EliteFoods.model;

import lombok.Setter;

public class Kosar {
    private Long azonosito;
    private String nev;
    private String kep;
    private int ar;
    @Setter
    private int mennyiseg;

    public Kosar(Long azonosito, String nev, String kep, int ar, int mennyiseg) {
        this.azonosito = azonosito;
        this.nev = nev;
        this.kep = kep;
        this.ar = ar;
        this.mennyiseg = mennyiseg;
    }

    public Long getAzonosito() {
        return azonosito;
    }

    public String getNev() {
        return nev;
    }

    public int getAr() {
        return ar;
    }

    public int getMennyiseg() {
        return mennyiseg;
    }

    public int getTotalPrice() {
        return ar * mennyiseg;
    }

    public String getKep() {
        return kep;
    }
}
