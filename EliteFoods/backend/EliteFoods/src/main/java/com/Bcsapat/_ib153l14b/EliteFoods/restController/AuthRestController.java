package com.Bcsapat._ib153l14b.EliteFoods.restController;

import com.Bcsapat._ib153l14b.EliteFoods.config.JwtUtil;
import com.Bcsapat._ib153l14b.EliteFoods.model.AuthResponse;
import com.Bcsapat._ib153l14b.EliteFoods.model.Felhasznalo;
import com.Bcsapat._ib153l14b.EliteFoods.model.LoginRequest;
import com.Bcsapat._ib153l14b.EliteFoods.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@Slf4j
@RequestMapping("/api/auth")
public class AuthRestController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthRestController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
        @PostMapping("/login")
        public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
            log.info("Login api meghivva!");
            log.info(loginRequest.getUsername());
            log.info(loginRequest.getPassword());
            Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
            String token=jwtUtil.generateToken(authentication);
            log.info(token);
            return ResponseEntity.ok(new AuthResponse(token));
            }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Felhasznalo user) throws Exception {
        log.info("Regisztracio api meghivva!");
        user.setJelszo(passwordEncoder.encode(user.getJelszo()));
        try{
            if(userService.registerUser(user)==null){
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body("Username/email is already taken!");
            }
        }catch (Exception e){
            log.info("Nem sikerült a regisztráció!");
        }
        return ResponseEntity.ok("User registered successfully!");

    }
}
