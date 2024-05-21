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
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nomeCompleto;
    private String cpf;
    private String telefone;
    private Date dataNascimento;
    private Double horasMensais;
    private String email;
}
