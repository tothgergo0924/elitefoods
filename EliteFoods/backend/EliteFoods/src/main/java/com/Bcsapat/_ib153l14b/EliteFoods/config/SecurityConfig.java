package com.Bcsapat._ib153l14b.EliteFoods.config;

import com.Bcsapat._ib153l14b.EliteFoods.service.UserService;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userDetailsService;
    private final JwtTokenFilter jwtTokenFilter;

    public SecurityConfig(UserService contactUserDetailsService,JwtTokenFilter jwtTokenFilter) {
        this.userDetailsService = contactUserDetailsService;
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,JwtTokenFilter jwtTokenFilter) throws Exception {

        http
                .cors(cors-> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(rQ -> {
                    rQ.requestMatchers("/api/auth/login", "/api/auth/register","api/products/**").permitAll();
                    rQ.requestMatchers("/assets/images/products/**").permitAll(); // Bárki elérheti
                    rQ.requestMatchers("/api/usercrud/**").hasRole("ADMIN");
                    rQ.requestMatchers("/api/productcrud/**").hasRole("ADMIN");
                    rQ.anyRequest().authenticated();
                });

        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://161.35.204.146");
        config.addAllowedOrigin("http://161.35.204.146:80");
        config.addAllowedOrigin("http://161.35.204.146/");
        config.addAllowedOrigin("http://localhost:4200"); // Frontend origin
        config.addAllowedHeader("*"); // Engedélyezett fejlécek
        config.addAllowedMethod("*"); // Engedélyezett HTTP metódusok
        config.setAllowCredentials(true); // Cookie-k engedélyezése

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Alkalmazás minden endpointjára
        return source;
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
