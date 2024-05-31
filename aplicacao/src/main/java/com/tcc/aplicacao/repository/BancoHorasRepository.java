package com.tcc.aplicacao.repository;

import com.tcc.aplicacao.entities.BancoHoras;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BancoHorasRepository extends JpaRepository<BancoHoras, Integer> {
    BancoHoras findByUsuarioId(int usuarioId);
}
