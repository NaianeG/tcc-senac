package com.tcc.aplicacao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tcc.aplicacao.entities.Ajuste;

public interface AjusteRepository extends JpaRepository<Ajuste, Integer> {
    // Usando a nomenclatura do Spring Data JPA
    List<Ajuste> findByMarcacaoPontoId(int marcacaoPontoId);

    // Usando uma query JPQL personalizada
    @Query("SELECT a FROM Ajuste a WHERE a.marcacaoPonto.id = :marcacaoPontoId")
    List<Ajuste> buscaPorMarcacaoPontoId(@Param("marcacaoPontoId") int marcacaoPontoId);
}
