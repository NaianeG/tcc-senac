package com.tcc.aplicacao.dto;

import com.tcc.aplicacao.entities.Pessoa;

public record CadastroDTO(String nomeUsuario, String senha, String role, Pessoa pessoa) {

}
