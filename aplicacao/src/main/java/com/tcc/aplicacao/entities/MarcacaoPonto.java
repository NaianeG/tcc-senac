package com.tcc.aplicacao.entities;

import java.sql.Time;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class MarcacaoPonto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int idUsuario;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date data;
    private Time horaEntrada;
    private Time horaSaida;
    private String localizacao;

    @OneToOne(mappedBy = "marcacaoPonto", cascade = CascadeType.ALL)
    @JsonIgnore
    private Ajuste ajuste;
}
