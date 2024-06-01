package com.tcc.aplicacao.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

//Classe da entidade Banco de horas que armazenará e calculará o banco de horas do doscente. OBS: Verificar calculo, pelo Banco de dados.
@Entity
@Getter
@Setter
public class BancoHoras {

    // id do banco de horas
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Dados da tabela banco
    private Double saldoMensal;
    private Boolean saldoNegativo;
}
