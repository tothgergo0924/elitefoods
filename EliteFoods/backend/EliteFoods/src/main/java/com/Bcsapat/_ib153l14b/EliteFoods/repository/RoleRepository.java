package com.Bcsapat._ib153l14b.EliteFoods.repository;

import com.Bcsapat._ib153l14b.EliteFoods.model.Jogosultsag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Jogosultsag,Long> {
    Jogosultsag findByNev(String nev);
}
