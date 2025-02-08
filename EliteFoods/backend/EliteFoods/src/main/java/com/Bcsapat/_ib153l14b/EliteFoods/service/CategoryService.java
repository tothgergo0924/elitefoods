package com.Bcsapat._ib153l14b.EliteFoods.service;

import com.Bcsapat._ib153l14b.EliteFoods.model.Kategoria;
import com.Bcsapat._ib153l14b.EliteFoods.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Kategoria findCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public List<Kategoria> findAllCategories() {
        return categoryRepository.findAll();
    }
}