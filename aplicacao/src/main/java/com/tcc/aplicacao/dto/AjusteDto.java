package com.tcc.aplicacao.dto;

import com.tcc.aplicacao.entities.Ajuste;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AjusteDTO {
    private Ajuste ajuste;
    private String nomeDocente;

    public AjusteDTO(Ajuste ajuste, String nomeDocente) {
        this.ajuste = ajuste;
        this.nomeDocente = nomeDocente;
    }
}
