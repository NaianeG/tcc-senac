package com.tcc.aplicacao.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tcc.aplicacao.entities.MarcacaoPonto;

public interface MarcacaoPontoRepository extends JpaRepository<MarcacaoPonto, Integer> {

    List<MarcacaoPonto> findByIdUsuario(int idUsuario);

    MarcacaoPonto findByDataAndIdUsuario(Date data, int idUsuario);

    Page<MarcacaoPonto> findByIdUsuarioOrderByIdDesc(int idUsuario, Pageable pageable);

    MarcacaoPonto findById(int id);

    List<Integer> findIdByIdUsuario(int idUsuario);

    @Query("SELECT m FROM MarcacaoPonto m WHERE m.idUsuario = :idUsuario")
    MarcacaoPonto buscaPorIdUsuario(@Param("idUsuario") int idUsuario);

    MarcacaoPonto findByData(Date date);

}
