package com.tcc.aplicacao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tcc.aplicacao.dto.LoginDto;
import com.tcc.aplicacao.services.UsuarioService;

@Controller
public class LoginController {

    @Autowired
    UsuarioService service;

    @GetMapping("login")
    public String paginaLogin() {
        return "login";
    }

    @PostMapping("/logar")
    public String login(@Validated LoginDto loginDto) {
        service.autentica(loginDto);
        return "redirect:ponto/home";
    }

}
