package com.tcc.aplicacao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tcc.aplicacao.entities.Ajuste;

public interface AjusteRepository extends JpaRepository<Ajuste, Integer> {
    // Retorna uma lista de ajustes para outros casos de uso
    List<Ajuste> findByMarcacaoPontoId(int marcacaoPontoId);

    // Retorna um ajuste específico, se existir, para checar duplicação
    Optional<Ajuste> findFirstByMarcacaoPontoId(int marcacaoPontoId);

    // Usando uma query JPQL personalizada
    @Query("SELECT a FROM Ajuste a WHERE a.marcacaoPonto.id = :marcacaoPontoId")
    List<Ajuste> buscaPorMarcacaoPontoId(@Param("marcacaoPontoId") int marcacaoPontoId);
}
