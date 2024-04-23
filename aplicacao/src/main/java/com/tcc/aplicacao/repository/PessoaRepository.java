package com.tcc.aplicacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.aplicacao.entities.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

}
