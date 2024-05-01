package com.tcc.aplicacao.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.aplicacao.dto.UsuarioDto;
import com.tcc.aplicacao.entities.Usuario;
import com.tcc.aplicacao.services.UsuarioService;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService service;

    @PostMapping("/cadastro")
    public String cadastroUsuario(Usuario usuario) {
        return service.salvar(usuario);
    }

    @GetMapping("/lista")
    public List<UsuarioDto> listaUsuarios() {
        return service.listaUsuario();
    }


    @GetMapping("/busca/{id}")
        public UsuarioDto buscaUsuario(@PathVariable("id") int id) {
        return service.buscaUsuario(id);
    }


    @GetMapping("/excluir/{id}")
    public String excluirUsuario(@PathVariable("id") int id) {
        return service.excluirUsuario(id);
    }
}
