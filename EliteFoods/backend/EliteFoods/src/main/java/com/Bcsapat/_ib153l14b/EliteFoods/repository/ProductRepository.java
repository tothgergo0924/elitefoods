package com.Bcsapat._ib153l14b.EliteFoods.repository;

import com.Bcsapat._ib153l14b.EliteFoods.model.Kategoria;
import com.Bcsapat._ib153l14b.EliteFoods.model.Rendeles;
import com.Bcsapat._ib153l14b.EliteFoods.model.Termek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Termek,Long> {
    List<Termek> findByNevContainingIgnoreCase(String name);

    List<Termek> findByKategoria(Kategoria kategoria);

    Termek findByAzonosito(Long azon);

    List<Termek> getTermekByRendeles(Rendeles rendeles);
}
