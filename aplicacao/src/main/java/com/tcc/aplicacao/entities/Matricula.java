package com.tcc.aplicacao.entities;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

//Classe da entidade matricula
@Entity
@Getter
@Setter
public class Matricula {
    // dados da tabela
    /*
     * id da matrícula. Está para gerar automáticamente para criar dados fictícios,
     * mas quem geraria esses id seria o próprio gestor
     * informando no sistema.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String telefone;
    private String nomeCompleto;
    private Date dataNascimento;
    private String turma;
    private Double horasMensais;
    private String cpf;
    private String emal;
}
