package com.Bcsapat._ib153l14b.EliteFoods.model;

public class UserRoleDTO {
    private Felhasznalo felhasznalo;
    private Jogosultsag role;

    public UserRoleDTO(Felhasznalo felhasznalo, Jogosultsag role) {
        this.felhasznalo = felhasznalo;
        this.role = role;
    }

    public Felhasznalo getFelhasznalo() {
        return felhasznalo;
    }

    public Jogosultsag getRole() {
        return role;
    }
}
