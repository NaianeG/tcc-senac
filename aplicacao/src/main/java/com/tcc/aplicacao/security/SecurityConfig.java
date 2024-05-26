package com.tcc.aplicacao.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.tcc.aplicacao.services.UsuarioDetailService;

@Configuration
@EnableWebSecurity()
public class SecurityConfig {
    @Autowired
    private UsuarioDetailService usuarioDetailService;

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
    // Exception {
    // return http
    // .csrf(csrf -> csrf.disable())
    // .sessionManagement(session ->
    // session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    // .authorizeHttpRequests(authorize -> authorize
    // // Colocar a rota para liberar para todos com permit all, ou bloquear
    // // dependendo o tipo de usuario com hasRole

    // .requestMatchers(HttpMethod.DELETE, "/deletarDocente/{id}").permitAll()
    // .requestMatchers(HttpMethod.POST, "/cadastrarDocente").permitAll()
    // .requestMatchers(HttpMethod.GET, "/cadastroDocente").permitAll()
    // .requestMatchers(HttpMethod.GET, "css/formCadastroDocente.css").permitAll()
    // .requestMatchers(HttpMethod.GET,
    // "scripts/formCadastroDocente.js").permitAll()

    // .requestMatchers(HttpMethod.GET, "/home").permitAll()
    // .requestMatchers("/css/home.css").permitAll()
    // .requestMatchers("/scripts/home.js").permitAll()

    // .requestMatchers(HttpMethod.GET, "/login").permitAll()
    // .requestMatchers(HttpMethod.POST, "/logar").permitAll()
    // .requestMatchers(HttpMethod.GET, "/css/login.css").permitAll()
    // .requestMatchers(HttpMethod.GET, "/css/listaDocente.css").permitAll()
    // .requestMatchers("/css/listaDocente.css").permitAll()
    // .requestMatchers("/css/home.css").permitAll()
    // .requestMatchers("/scripts/home.js").permitAll()
    // // .requestMatchers("/cadastro").hasRole("ADMIN")
    // .requestMatchers(HttpMethod.POST, "/cadastro").permitAll()
    // .requestMatchers(HttpMethod.GET, "/cadastro").permitAll()
    // .requestMatchers(HttpMethod.GET, "/listaDocentes").permitAll()
    // .requestMatchers(HttpMethod.POST, "/listaDocentes").permitAll()

    // .requestMatchers(HttpMethod.POST,
    // "/relatorio/pdf/relatorio-docente").permitAll()
    // .requestMatchers(HttpMethod.GET,
    // "/relatorio/pdf/relatorio-docente").permitAll()
    // .anyRequest().authenticated())
    // .formLogin(form -> form.loginPage("/login"))
    // .build();
    // }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> {
                    request.requestMatchers("/css/*", "/scripts/**", "cadastro", "/cadastroUsuario", "/testeGrafico")
                            .permitAll();
                    request.requestMatchers(HttpMethod.POST, "/cadastroUsuario", "/cadastrarDocente",
                            "/relatorio/pdf/relatorio-docente").hasRole("ADMIN");
                    request.requestMatchers(HttpMethod.DELETE, "/deletarDocente/{id}").hasRole("ADMIN");
                    request.requestMatchers(HttpMethod.GET, "/deletarDocente/{id}", "cadastro",
                            "/relatorio/pdf/relatorio-docente").hasRole("ADMIN");
                    request.anyRequest().authenticated();
                })
                .formLogin(httpFormLogin -> {
                    httpFormLogin.loginPage("/login").permitAll();
                    httpFormLogin.successHandler(new AuthentiationSuccess()).permitAll();
                })
                .logout(logout -> {
                    logout.logoutUrl("/logout");
                    logout.logoutSuccessUrl("/login?logout");
                    logout.invalidateHttpSession(true);
                    logout.deleteCookies("JSESSIONID");
                    logout.permitAll();
                })
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(usuarioDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return usuarioDetailService;
    }
}
