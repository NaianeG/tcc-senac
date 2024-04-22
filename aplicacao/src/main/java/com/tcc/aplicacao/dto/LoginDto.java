package com.tcc.aplicacao.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    private String nomeUsuario;
    private String senha;
   
    public LoginDto() {

    }

    public LoginDto(String nomeUsuario, String senha) {
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
    }
}
