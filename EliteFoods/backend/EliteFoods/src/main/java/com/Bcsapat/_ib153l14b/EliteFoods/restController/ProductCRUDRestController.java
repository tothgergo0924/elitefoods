package com.Bcsapat._ib153l14b.EliteFoods.restController;


import com.Bcsapat._ib153l14b.EliteFoods.model.Felhasznalo;
import com.Bcsapat._ib153l14b.EliteFoods.model.Kategoria;
import com.Bcsapat._ib153l14b.EliteFoods.model.Termek;
import com.Bcsapat._ib153l14b.EliteFoods.service.CategoryService;
import com.Bcsapat._ib153l14b.EliteFoods.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/productcrud")
public class ProductCRUDRestController {
    private ProductService productService;
    private CategoryService categoryService;

    public ProductCRUDRestController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }



    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody Termek product){
        log.info("product add meghivas");

        try{
            if(productService.saveProduct(product)==null){
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("Product already exists!");
            }
        }catch (Exception e){
            log.info(e.toString());
            log.info("Nem sikerült a hozzadas!");
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Product already exists!");
        }
        return ResponseEntity.ok("Product added successfully!");
    }
    @GetMapping("/categories")
    public ResponseEntity<List<Kategoria>> listCategories(){
        List<Kategoria> categories=categoryService.findAllCategories();
        return ResponseEntity.ok(categories);
    }
    @GetMapping
    public ResponseEntity<List<Termek>> listProducts(){
        List<Termek> products=productService.findAllProducts();
        return ResponseEntity.ok(products);
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody Termek product){
        log.info("Editing..");

        try{
            Termek termek=productService.getTermekById(id);
            termek.setNev(product.getNev());
            termek.setAr(product.getAr());
            termek.setLejarati_datum(product.getLejarati_datum());
            termek.setMennyiseg(product.getMennyiseg());
            productService.saveProduct(product);
        }catch (Exception e){
            log.info(e.toString());
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Product name is already taken!");
        }

        return ResponseEntity.ok(" updated successfully!");
    }
    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id){
        log.info("Deleting..");
        productService.deleteProduct(id);
    }

}
