package com.tcc.aplicacao.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity()
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // Colocar a rota para liberar para todos com permit all, ou bloquerar
                        // dependendo o tipo de usuarui com hasRole
                        .requestMatchers(HttpMethod.GET, "/ponto/home").permitAll()
                        .requestMatchers(HttpMethod.GET, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/logar").permitAll()
                        .requestMatchers(HttpMethod.GET, "/css/login.css").permitAll()
                        .requestMatchers("/css/home.css").permitAll()
                        .requestMatchers("/scripts/home.js").permitAll()
                        // .requestMatchers("/cadastro").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/cadastro").permitAll()
                        .requestMatchers(HttpMethod.GET, "/cadastro").permitAll()
                        .requestMatchers(HttpMethod.GET, "/listaDocentes").permitAll()
                        .requestMatchers(HttpMethod.POST, "/listaDocentes").permitAll()
                        .requestMatchers(HttpMethod.POST, "/relatorio/pdf/jr1").permitAll()
                        .requestMatchers(HttpMethod.GET, "/relatorio/pdf/jr1").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login"))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
