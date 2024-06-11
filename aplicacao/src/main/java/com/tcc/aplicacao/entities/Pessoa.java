package com.tcc.aplicacao.entities;

import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

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
    private long horasMensais;
    private String email;

    @OneToOne(mappedBy = "pessoa", cascade = CascadeType.ALL, orphanRemoval = true)
    private Usuario usuario;

    public Pessoa() {
    }

    public Pessoa(int id, String nomeCompleto, String cpf, String telefone, Date dataNascimento, long horasMensais,
            String email, Usuario usuario) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.telefone = telefone;
        this.dataNascimento = dataNascimento;
        this.horasMensais = horasMensais;
        this.email = email;
        this.usuario = usuario;
    }
}
