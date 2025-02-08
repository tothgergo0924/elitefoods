package com.Bcsapat._ib153l14b.EliteFoods.repository;

import com.Bcsapat._ib153l14b.EliteFoods.model.Felhasznalo;
import com.Bcsapat._ib153l14b.EliteFoods.model.Rendeles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Rendeles,Long> {

    List<Rendeles> findByFelhasznaloAzonostio(Felhasznalo felhasznalo);

    Rendeles getRendelesByAzonosito(Long azonsoito);

    void deleteByAzonosito(Long azonosito);

}
