package com.Bcsapat._ib153l14b.EliteFoods.restController;


import com.Bcsapat._ib153l14b.EliteFoods.model.*;
import com.Bcsapat._ib153l14b.EliteFoods.service.CartService;
import com.Bcsapat._ib153l14b.EliteFoods.service.OrderService;
import com.Bcsapat._ib153l14b.EliteFoods.service.ProductService;
import com.Bcsapat._ib153l14b.EliteFoods.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/cart")
public class CartRestController {
    private UserService userService;
    private CartService cartService;
    private OrderService orderService;
    private ProductService productService;

    public CartRestController(UserService userService, CartService cartService, OrderService orderService, ProductService productService) {
        this.userService = userService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<String> purchaseItems(@RequestBody PurchaseRequest purchaseRequest) {
        log.info("Vásárlás!!");
        try {

            Felhasznalo felhasznalo = userService.loadUserByUsername(purchaseRequest.getUsername());
            List<Kosar> termekek = purchaseRequest.getProducts();

            // Rendelés inicializálása és mentése
            Rendeles rendeles = new Rendeles();
            rendeles.setDatum(new Date());
            rendeles.setFelhasznaloAzonostio(felhasznalo);

            // Első lépésben mentjük a rendelést
            rendeles = cartService.addOrderToRepo(rendeles);

            // Iterálás a termékeken
            for (Kosar kosar : termekek) {
                Termek termek = new Termek();
                termek.setMennyiseg(kosar.getMennyiseg());
                termek.setAr(kosar.getAr());
                termek.setKep(kosar.getKep());
                termek.setNev(kosar.getNev());
                termek.setLejarati_datum(new Date().toString());
                termek.setRendeles(rendeles); // Kapcsoljuk a rendeléshez
                productService.saveProduct(termek); // Mentjük a terméket
                rendeles.addTermek(termek);

            }

            rendeles.setOsszeg(purchaseRequest.getPrice());
            cartService.addOrderToRepo(rendeles); // Ismét elmentjük a rendelést a termékekkel

            log.info("Termékek száma " + rendeles.getTermekek().size());
            return ResponseEntity.ok("Purchase successful");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Purchase failed: " + e.getMessage());
        }
    }
}
