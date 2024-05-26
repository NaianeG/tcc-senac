package com.tcc.aplicacao.entities;

import java.sql.Time;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MarcacaoPonto {

    // ID da marca
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int idUsuario;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date data;
    private Time horaEntrada;
    private Time horaSaida;
}
