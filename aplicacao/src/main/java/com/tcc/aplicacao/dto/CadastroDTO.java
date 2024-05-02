package com.tcc.aplicacao.dto;

import com.tcc.aplicacao.entities.UserRole;

public record CadastroDTO(String nomeUsuario, String senha, UserRole role) {

}
