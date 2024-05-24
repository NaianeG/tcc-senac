package com.tcc.aplicacao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tcc.aplicacao.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsername(String nomeUsuario);

    @Query("select u from Usuario u where u.username = ?1")
    Usuario findByUsuarioJPQL(String nomeUsuario);

    @Query(value = "SELECT * FROM usuario WHERE username = ?1", nativeQuery = true)
    Usuario findByUsuarioNativo(String nomeUsuario);
}
