package com.tcc.aplicacao.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.aplicacao.entities.MarcacaoPonto;
import com.tcc.aplicacao.entities.Usuario;

public interface MarcacaoPontoRepository extends JpaRepository<MarcacaoPonto, Integer> {

    List<MarcacaoPonto> findByIdUsuario(int idUsuario);

    Usuario findById(int idUsuario);

    MarcacaoPonto findByDataAndIdUsuario(Date data, int idUsuario);
}
