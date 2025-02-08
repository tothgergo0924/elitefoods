package com.Bcsapat._ib153l14b.EliteFoods.service;

import com.Bcsapat._ib153l14b.EliteFoods.model.Kategoria;
import com.Bcsapat._ib153l14b.EliteFoods.model.Termek;
import com.Bcsapat._ib153l14b.EliteFoods.repository.CategoryRepository;
import com.Bcsapat._ib153l14b.EliteFoods.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }
    public List<Termek> findAllProducts() {
        return productRepository.findAll();
    }

    public List<Termek> filterProductsByCategories(List<Termek> termekek, List<Long> kategoriakId) {
        //System.out.println(kategoriakId);
        List<Kategoria> kategoriaList = new ArrayList<>();
        List<Termek> ujTermekek = new ArrayList<>();

        if(kategoriakId.isEmpty()){
            return termekek;
        }

        for(Long id : kategoriakId){
            kategoriaList.add(categoryRepository.findByAzonosito(id));
        }

        for(Termek termek : termekek){
            if(kategoriaList.contains(termek.getKategoria())){
                ujTermekek.add(termek);
            }
        }

        return ujTermekek;
    }

    public List<Termek> filterProductsByName(String name) {
        if(name == null || name.trim().isEmpty()){
            return findAllProducts();
        }

        String cleanedName = name.trim().replaceAll("\\s+", " ");
        return productRepository.findByNevContainingIgnoreCase(cleanedName);
    }

    public List<Termek> getFilteredProducts(String search,List<Long> categoryIds,String sortBy){
        List<Termek> products=productRepository.findAll();

        Stream<Termek> filteredStream=products.stream()
                .filter(product->search==null || product.getNev().toLowerCase().contains(search.toLowerCase()))
                .filter(product->categoryIds==null || categoryIds.isEmpty() || (product.getKategoria()!=null &&categoryIds.contains(product.getKategoria().getAzonosito())));

        Comparator<Termek> comparator;
        switch (sortBy) {
            case "name":
                comparator = Comparator.comparing(Termek::getNev);
                break;
            case "price":
                comparator = Comparator.comparing(Termek::getAr);
                break;
            case "expiryDate":
                 comparator = Comparator.comparing(
                        Termek::getLejarati_datum,
                        Comparator.nullsLast(Comparator.naturalOrder())
                );
                 break;
            default:
                comparator = Comparator.comparing(Termek::getAzonosito);
        }

        return filteredStream.sorted(comparator).collect(Collectors.toList());
    }

    public Termek getTermekById(Long id) {
        return productRepository.findByAzonosito(id);
    }

    public Termek saveProduct(Termek product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
