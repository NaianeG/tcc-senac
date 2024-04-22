package com.tcc.aplicacao.dto;

import com.tcc.aplicacao.entities.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDto {

  private int id;
  private int fkIdMatricula;
  private String nomeUsuario;
  private String horaSaida;

  public UsuarioDto() {

  }

  public UsuarioDto(int id, int fkIdMatricula, String nomeUsuario, String horaSaida) {
    this.id = id;
    this.fkIdMatricula = fkIdMatricula;
    this.nomeUsuario = nomeUsuario;
    this.horaSaida = horaSaida;
  }

  public UsuarioDto(Usuario usuario) {
    this.id = usuario.getId();
    this.fkIdMatricula = usuario.getFkIdMatricula();
    this.nomeUsuario = usuario.getNomeUsuario();
    this.horaSaida = usuario.getHoraSaida();
  }
}