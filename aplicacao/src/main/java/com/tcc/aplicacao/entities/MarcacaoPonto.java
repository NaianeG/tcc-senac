package com.tcc.aplicacao.entities;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

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

    @DateTimeFormat(iso = ISO.DATE, fallbackPatterns = { "dd.MM.yyyy" })
    private Date data;
    private String horaEntrada;
    private String horaSaida;
}
