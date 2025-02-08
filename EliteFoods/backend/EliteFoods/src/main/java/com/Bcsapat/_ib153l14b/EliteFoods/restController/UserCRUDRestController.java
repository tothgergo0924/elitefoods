package com.Bcsapat._ib153l14b.EliteFoods.restController;

import com.Bcsapat._ib153l14b.EliteFoods.model.Felhasznalo;
import com.Bcsapat._ib153l14b.EliteFoods.model.Jogosultsag;
import com.Bcsapat._ib153l14b.EliteFoods.model.UserRoleDTO;
import com.Bcsapat._ib153l14b.EliteFoods.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/usercrud")
public class UserCRUDRestController {
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public UserCRUDRestController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<List<Felhasznalo>> listUser(){
        List<Felhasznalo> users=userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id){
        log.info("Deleting..");
        userService.deleteUser(id);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody Felhasznalo user){
        log.info("Editing..");

        try{
            Felhasznalo contact=userService.getUserById(id);
            contact.setTelefonszam(user.getTelefonszam());
            contact.setKeresztnev(user.getKeresztnev());
            contact.setEmail_cim(user.getEmail_cim());
            userService.updateUser(contact);
        }catch (Exception e){
            log.info(e.toString());
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Email is already taken!");
        }

        return ResponseEntity.ok("User updated successfully!");
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserRoleDTO userRoleDTO){
        log.info("Add meghivasra kerult!");

        Felhasznalo felhasznalo=userRoleDTO.getFelhasznalo();
        Jogosultsag jogosultsag=userService.findRoleByName(userRoleDTO.getRole().getNev());
        log.info(felhasznalo.getFelhasznalonev());
        felhasznalo.setJelszo(passwordEncoder.encode(felhasznalo.getJelszo()));
        try{
            if(userService.addUser(felhasznalo,jogosultsag)==null){
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("Username/email is already taken!");
            }
        }catch (Exception e){
            log.info(e.toString());
            log.info("Nem sikerült a hozzadas!");
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Username/email is already taken!");
        }
        return ResponseEntity.ok("User added successfully!");
    }
}
