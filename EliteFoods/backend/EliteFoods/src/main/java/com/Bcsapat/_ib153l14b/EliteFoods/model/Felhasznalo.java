package com.Bcsapat._ib153l14b.EliteFoods.model;

import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name="felhasznalo")
public class Felhasznalo implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long azonosito;




    @ManyToOne
    @JoinColumn(name="cim_id")
    private Cim cim;
    private String jelszo;
    private String keresztnev;
    private String vezeteknev;
    private String email_cim;
    private String felhasznalonev;
    private String telefonszam;
    @Override
    public String getPassword() {
        return this.jelszo;
    }

    @Override
    public String getUsername() {
        return this.felhasznalonev;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @ManyToOne
    @JoinColumn(name = "jogosultsag_id")
    private Jogosultsag role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roleName = "ROLE_" + role.getNev().toUpperCase();
        return Collections.singletonList(new SimpleGrantedAuthority(roleName));
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }



}
