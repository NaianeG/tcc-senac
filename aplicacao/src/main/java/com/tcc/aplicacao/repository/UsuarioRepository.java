package com.tcc.aplicacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.aplicacao.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByIdUser(String nomeUsuario);

    Usuario findByUsername(String nomeUsuario);

}
