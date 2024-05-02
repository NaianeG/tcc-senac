package com.tcc.aplicacao.dto;

import com.tcc.aplicacao.entities.Usuario;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDto {

  private String username;

  public UsuarioDto() {

  }

  public UsuarioDto(String username) {

    this.username = username;
  }

  public UsuarioDto(Usuario usuario) {
    this.username = usuario.getUsername();
  }
}