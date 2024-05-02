package com.tcc.aplicacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.tcc.aplicacao.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Usuario findByNomeUsuario(String nomeUsuario);

    UserDetails findByUsername(String nomeUsuario);
}
