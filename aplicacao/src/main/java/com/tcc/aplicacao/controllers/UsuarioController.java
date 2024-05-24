package com.tcc.aplicacao.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.tcc.aplicacao.dto.CadastroDTO;
import com.tcc.aplicacao.dto.UsuarioDto;
import com.tcc.aplicacao.services.UsuarioService;

@Controller
public class UsuarioController {

    @Autowired
    UsuarioService service;

    @GetMapping("/cadastro")
    public String telaCadastro() {
        return "cadastro";
    }

    @PostMapping("/cadastroUsuario")
    public String cadastroUsuario(@Validated CadastroDTO cadastroDTO) {
        service.salvar(cadastroDTO);
        return "redirect:/login";
    }

    @GetMapping("/lista")
    public List<UsuarioDto> listaUsuarios() {
        return service.listaUsuario();
    }

    // @GetMapping("/busca/{id}")
    // public UsuarioDto buscaUsuario(@PathVariable("id") int id) {
    // return service.buscaUsuario(id);
    // }

    @GetMapping("/excluir/{id}")
    public String excluirUsuario(@PathVariable("id") int id) {
        return service.excluirUsuario(id);
    }
}
