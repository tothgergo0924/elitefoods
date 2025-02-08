package com.Bcsapat._ib153l14b.EliteFoods.restController;

import com.Bcsapat._ib153l14b.EliteFoods.model.Felhasznalo;
import com.Bcsapat._ib153l14b.EliteFoods.model.Kategoria;
import com.Bcsapat._ib153l14b.EliteFoods.model.Termek;
import com.Bcsapat._ib153l14b.EliteFoods.service.CategoryService;
import com.Bcsapat._ib153l14b.EliteFoods.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductRestController {
    private ProductService productService;
    private CategoryService categoryService;

    public ProductRestController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }
    @GetMapping("/categories")
    public ResponseEntity<List<Kategoria>> listCategories(){
        log.info("kategoria meghivva!");
        List<Kategoria> categories=categoryService.findAllCategories();
        return ResponseEntity.ok(categories);
    }
    @GetMapping
    public List<Termek> listProducts(@RequestParam(required = false) String search,
                                                     @RequestParam(required = false) List<Long> categoryIds,
                                                     @RequestParam(defaultValue = "id") String sortBy
                                                     ){
        log.info(sortBy);
        return productService.getFilteredProducts(search,categoryIds,sortBy);
    }
}
