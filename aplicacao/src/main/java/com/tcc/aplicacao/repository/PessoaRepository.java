package com.tcc.aplicacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tcc.aplicacao.entities.Pessoa;

import java.util.*;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

    @Query("select p.nomeCompleto from Pessoa p order by p.nomeCompleto asc")
    List<String> findNomes();

    @Query("SELECT p FROM Pessoa p JOIN p.usuario u WHERE u.role != 'ADMIN'")
    List<Pessoa> findPessoasDiferenteDeADMIN();

}
