package com.tcc.aplicacao.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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

    @OneToOne(mappedBy = "usuario")
    // relacionamento com a entidade matricula Ajeitar depois

    private int id;
    private int fkIdMatricula;
    private String nomeUsuario;
    private String horaSaida;
    private String senha;

    public Usuario() {
    }

}
