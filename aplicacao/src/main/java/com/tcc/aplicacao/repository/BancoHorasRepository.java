package com.tcc.aplicacao.repository;

import com.tcc.aplicacao.entities.BancoHoras;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BancoHorasRepository extends JpaRepository<BancoHoras, Integer> {
    BancoHoras findByUsuarioId(int usuarioId);

    @Query(value = "SELECT * FROM banco_horas WHERE hora_saida IS NOT NULL", nativeQuery = true)
    List<BancoHoras> findAllBancoHoras();
}
