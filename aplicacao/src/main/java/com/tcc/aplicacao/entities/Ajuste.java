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

//Classe da entidade Ajuste, que irá armazenar os dados do ajuste que o doscente fará caso falte, pegue atestado ou algo do tipo.
@Entity
@Getter
@Setter
public class Ajuste {

    // ID da classe ajuste
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // chave estrangeira da entidade Marcação de Ponto para relacionar as tabelas.
    // OBS: consultar modelagem de classe
    private int fkIdMarcacaoPonto;

    // dados da tabela
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date data;
    private Time horaEntrada;
    private Time horaSaida;
    private String justificativa;
    private String arquivo;
    private String status;
}
