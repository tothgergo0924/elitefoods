package com.Bcsapat._ib153l14b.EliteFoods.config;

import com.Bcsapat._ib153l14b.EliteFoods.model.Cim;
import com.Bcsapat._ib153l14b.EliteFoods.model.Felhasznalo;
import com.Bcsapat._ib153l14b.EliteFoods.model.Jogosultsag;
import com.Bcsapat._ib153l14b.EliteFoods.repository.AddressRepository;
import com.Bcsapat._ib153l14b.EliteFoods.repository.RoleRepository;
import com.Bcsapat._ib153l14b.EliteFoods.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.model.process.internal.UserTypeResolution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AdminDataSetup implements ApplicationListener<ContextRefreshedEvent> {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private AddressRepository addressRepository;

    @Autowired
    public AdminDataSetup(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository,AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.addressRepository=addressRepository;
    }
    boolean alreadySetup=false;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event){
        log.info("Setting up admin...");
        if(alreadySetup){
            return;
        }
        Jogosultsag adminRole=roleRepository.findByNev("admin");
        if(adminRole==null){
            Jogosultsag admin=new Jogosultsag();
            admin.setNev("admin");
            roleRepository.save(admin);
            adminRole=roleRepository.findByNev("admin");
        }
        Jogosultsag boltosRole=roleRepository.findByNev("boltos");
        if(boltosRole==null){
            Jogosultsag boltos=new Jogosultsag();
            boltos.setNev("boltos");
            roleRepository.save(boltos);
        }
        Jogosultsag felhasznaloRole=roleRepository.findByNev("felhasználó");
        if(felhasznaloRole==null){
            Jogosultsag felhasznalo=new Jogosultsag();
            felhasznalo.setNev("felhasználó");
            roleRepository.save(felhasznalo);
        }



        if(userRepository.findByFelhasznalonev("admin")!=null){
            return;
        }

        Felhasznalo user=new Felhasznalo();
        user.setJelszo(passwordEncoder.encode("asd"));
        user.setRole(adminRole);
        user.setFelhasznalonev("admin");
        user.setEmail_cim("emal1@cim");
        user.setKeresztnev("keresztnev");
        user.setVezeteknev("vezeteknev");
        user.setTelefonszam("0000000");

        Cim address=new Cim();

        address.setVaros("varos");
        address.setIranyitoszam("iranyitoszam");
        address.setOrszag("orszag");
        address.setHazszam("hazszam");
        address.setKozterulet("kozterulet");
        user.setCim(address);
        addressRepository.save(address);
        userRepository.save(user);
        log.info("Admin saved"+user.getUsername());
        alreadySetup=true;
    }
}
