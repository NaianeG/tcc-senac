package com.tcc.aplicacao.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BancoHorasDTO {

    private String nomeUsuario;
    private long saldoMensal;
    private String saldoAtual;
    private Boolean saldoNegativo;

    public BancoHorasDTO(String nomeUsuario, long saldoMensal, String saldoAtual, Boolean saldoNegativo) {
        this.nomeUsuario = nomeUsuario;
        this.saldoMensal = saldoMensal;
        this.saldoAtual = saldoAtual;
        this.saldoNegativo = saldoNegativo;
    }
}
