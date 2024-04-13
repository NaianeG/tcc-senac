package com.tcc.aplicacao.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    private String nome;
    private String senha;

    public LoginDto() {

    }

    public LoginDto(String nome, String senha) {
        this.nome = nome;
        this.senha = senha;
    }
}
