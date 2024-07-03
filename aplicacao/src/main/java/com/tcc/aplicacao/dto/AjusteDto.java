package com.tcc.aplicacao.dto;

import com.tcc.aplicacao.entities.Ajuste;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AjusteDto {
    private Ajuste ajuste;
    private String nomeDocente;

    public AjusteDto(Ajuste ajuste, String nomeDocente) {
        this.ajuste = ajuste;
        this.nomeDocente = nomeDocente;
    }
}
