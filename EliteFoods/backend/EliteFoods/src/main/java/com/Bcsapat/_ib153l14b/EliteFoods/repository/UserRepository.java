package com.Bcsapat._ib153l14b.EliteFoods.repository;

import com.Bcsapat._ib153l14b.EliteFoods.model.Felhasznalo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Felhasznalo,Long> {
    Felhasznalo findByFelhasznalonev(String felhasznalonev);

    default Felhasznalo findByEmail_cim(String email) {
        return  findAll().stream().filter(p->p.getEmail_cim().equals(email)).findFirst().orElse(null);
    }
    default boolean existsByEmail_cim(String email){
        return findByEmail_cim(email)!=null;

    }
    boolean existsByFelhasznalonev(String username);
}
