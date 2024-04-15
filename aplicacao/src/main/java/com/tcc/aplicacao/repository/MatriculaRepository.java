package com.tcc.aplicacao.repository;

import com.tcc.aplicacao.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tcc.aplicacao.entities.Matricula;

public interface MatriculaRepository extends JpaRepository<Matricula, Integer>{

}
