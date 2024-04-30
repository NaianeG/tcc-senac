package com.tcc.aplicacao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.aplicacao.dto.LoginDto;
import com.tcc.aplicacao.services.UsuarioService;

@RestController
@RequestMapping(value = "/login")
public class LoginController {
    @Autowired
    UsuarioService service;

    @PostMapping("/login")
    public ResponseEntity<String> login(LoginDto loginDto) {
        return service.autentica(loginDto);
    }

}
