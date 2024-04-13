package com.tcc.aplicacao.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "nomeUsuario" }) })
public class Usuario {

    // ID do usuario
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // relacionamento com a entidade matricula Ajeitar depois
    private int fkIdMatricula;

    private String nomeUsuario;
    private String horaSaida;
    private String senha;
}
