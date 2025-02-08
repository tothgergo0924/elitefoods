package com.Bcsapat._ib153l14b.EliteFoods.repository;

import com.Bcsapat._ib153l14b.EliteFoods.model.Kategoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Kategoria,Long> {
    Kategoria findByAzonosito(Long azon);
}
