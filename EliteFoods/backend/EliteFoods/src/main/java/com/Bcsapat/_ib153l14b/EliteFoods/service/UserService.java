package com.Bcsapat._ib153l14b.EliteFoods.service;

import com.Bcsapat._ib153l14b.EliteFoods.model.Felhasznalo;
import com.Bcsapat._ib153l14b.EliteFoods.model.Jogosultsag;
import com.Bcsapat._ib153l14b.EliteFoods.model.Rendeles;
import com.Bcsapat._ib153l14b.EliteFoods.repository.AddressRepository;
import com.Bcsapat._ib153l14b.EliteFoods.repository.OrderRepository;
import com.Bcsapat._ib153l14b.EliteFoods.repository.RoleRepository;
import com.Bcsapat._ib153l14b.EliteFoods.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;


@Service
@Transactional
@Slf4j
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private AddressRepository addressRepository;


    private OrderRepository orderRepository;

    @Autowired
    public UserService (UserRepository contactUserRepository,RoleRepository roleRepository,AddressRepository addressRepository, OrderRepository orderRepository) {
        this.userRepository=contactUserRepository;
        this.roleRepository=roleRepository;
        this.addressRepository=addressRepository;
        this.orderRepository = orderRepository;
    }

    public Felhasznalo registerUser(Felhasznalo felhasznalo){
        Jogosultsag jogosultsag=roleRepository.findByNev("felhasználó");
        felhasznalo.setRole(jogosultsag);
        Felhasznalo user=userRepository.findByFelhasznalonev(felhasznalo.getFelhasznalonev());
        Felhasznalo user1=userRepository.findByEmail_cim(felhasznalo.getEmail_cim());


        if(user==null&& user1==null){
            addressRepository.save(felhasznalo.getCim());
            return userRepository.save(felhasznalo);
        }
        return null;
    }
    public Felhasznalo addUser(Felhasznalo felhasznalo,Jogosultsag jogosultsag){
        felhasznalo.setRole(jogosultsag);
        addressRepository.save(felhasznalo.getCim());
        Felhasznalo user=userRepository.findByFelhasznalonev(felhasznalo.getFelhasznalonev());
        Felhasznalo user1=userRepository.findByEmail_cim(felhasznalo.getEmail_cim());
        if(user==null && user1==null){
            return userRepository.save(felhasznalo);
        }
        return null;

    }
    public Jogosultsag findRoleByName(String name){
        return roleRepository.findByNev(name);
    }

    public List<Felhasznalo> getUsers(){
        return userRepository.findAll();
    }

    @Override
    public Felhasznalo loadUserByUsername(String username) throws UsernameNotFoundException {
        Felhasznalo user=userRepository.findByFelhasznalonev(username);
        log.info(username);
        if(user==null){
            throw new UsernameNotFoundException("Could not find user with username for email!");
        }
        log.info("Authentikacio"+user.getUsername()+" "+user.getJelszo());
        return user;
    }
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public Felhasznalo updateUser(Felhasznalo user) {
        // Ellenőrizzük, hogy az email vagy felhasználónév változott-e, és ha igen, akkor végezzük el az ellenőrzést
        if (!user.getEmail_cim().equals(userRepository.findById(user.getAzonosito()).get().getEmail_cim()) &&
                userRepository.findByEmail_cim(user.getEmail_cim())!=null) {
            return null;
        }

        if (!user.getFelhasznalonev().equals(userRepository.findById(user.getAzonosito()).get().getFelhasznalonev()) &&
                userRepository.findByFelhasznalonev(user.getFelhasznalonev())!=null) {
            return null;
        }
        log.info(String.valueOf(userRepository.findByFelhasznalonev(user.getFelhasznalonev())!=null));
        log.info(String.valueOf(userRepository.findByEmail_cim(user.getEmail_cim())!=null));
        log.info("Átért!");
        // Ha minden rendben, mentjük a felhasználót
        return userRepository.save(user);
    }
    public Felhasznalo getUserById(Long id){
        return userRepository.getReferenceById(id);
    }

    public List<Rendeles> getUserOrders(Felhasznalo user) {
        return orderRepository.findByFelhasznaloAzonostio(user);
    }

    public void deleteOrderByAzonosito(Long azonosito) {
        orderRepository.deleteByAzonosito(azonosito);
    }
}
