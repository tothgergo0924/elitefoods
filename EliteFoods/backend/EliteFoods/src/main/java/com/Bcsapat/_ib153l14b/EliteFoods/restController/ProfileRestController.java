package com.Bcsapat._ib153l14b.EliteFoods.restController;

import com.Bcsapat._ib153l14b.EliteFoods.config.JwtUtil;
import com.Bcsapat._ib153l14b.EliteFoods.model.AuthResponse;
import com.Bcsapat._ib153l14b.EliteFoods.model.Felhasznalo;
import com.Bcsapat._ib153l14b.EliteFoods.model.Rendeles;
import com.Bcsapat._ib153l14b.EliteFoods.model.Termek;
import com.Bcsapat._ib153l14b.EliteFoods.service.OrderService;
import com.Bcsapat._ib153l14b.EliteFoods.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/profile")
public class ProfileRestController {

    private UserService userService;
    private OrderService orderService;
    private JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public ProfileRestController(UserService userService, OrderService orderService, JwtUtil jwtUtil, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.orderService = orderService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/{username}")
    public ResponseEntity<Felhasznalo> getUserByUsername(@PathVariable String username){
        Felhasznalo contact=userService.loadUserByUsername(username);
        return ResponseEntity.ok(contact);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<AuthResponse> editProfile(@PathVariable Long id,@RequestBody Felhasznalo user){
        try{
            Felhasznalo contact=userService.getUserById(id);
            contact.setTelefonszam(user.getTelefonszam());
            contact.setKeresztnev(user.getKeresztnev());
            contact.setEmail_cim(user.getEmail_cim());
            contact.setFelhasznalonev(user.getFelhasznalonev());
            contact.setVezeteknev(user.getVezeteknev());
            contact.setCim(user.getCim());

            if(userService.updateUser(contact)==null){
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(null);
            }
            log.info(user.getFelhasznalonev());
            log.info(user.getJelszo());
            Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getFelhasznalonev(),user.getJelszo()));
            String newToken=jwtUtil.generateToken(authentication);
            return ResponseEntity.ok(new AuthResponse(newToken));
        }catch (Exception e){
            log.info(e.toString());
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(null);
        }


    }
    @GetMapping("/product/{user}")
    public ResponseEntity<List<Rendeles>> getPrdoucts(@PathVariable String user){
        log.info("productsadas....");

        Felhasznalo contact=userService.loadUserByUsername(user);
        List<Rendeles> termekek=userService.getUserOrders(contact);
        for(int i=0;i< termekek.size();i++){
            log.info(String.valueOf(termekek.get(i).getTermekek().size()));

        }
        log.info(String.valueOf(termekek.size()));

        return ResponseEntity.ok(termekek);
    }
    @DeleteMapping("/delete/{productId}")
    public void deleteOrder(@PathVariable Long productId){
        userService.deleteOrderByAzonosito(productId);
    }
    @DeleteMapping("/delete/profile/{username}")
    public void deleteProfile(@PathVariable String username){
        Felhasznalo contact=userService.loadUserByUsername(username);
        userService.deleteUser(contact.getAzonosito());
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<Rendeles> getOrderById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getRendelesById(id));
    }


}
