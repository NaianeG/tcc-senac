package com.tcc.aplicacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.aplicacao.entities.Ajuste;

public interface AjusteRepository extends JpaRepository<Ajuste, Integer> {
    List<Ajuste> findByFkIdMarcacaoPontoIn(List<Integer> ids);
}
