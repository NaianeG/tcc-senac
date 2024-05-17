package com.tcc.aplicacao.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tcc.aplicacao.entities.Pessoa;
import com.tcc.aplicacao.services.PessoaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class PessoaController {

    @Autowired
    PessoaService pessoaService;

    @GetMapping("/homeDocente")
    public String homeDocente() {
        return "homeDocente";
    }

    @PostMapping("/cadastrarDocente")
    public void cadastroPessoa(Pessoa pessoa) {
        pessoaService.cadastraDocente(pessoa);
    }

    @GetMapping("/buscarDocente")
    public Pessoa buscaDocentePorId(@PathVariable("id") int id) {
        return pessoaService.buscaDocentePorId(id);
    }

    @GetMapping("/listaDocentes")
    public ModelAndView listaDocentes() {
        return pessoaService.listaDocentes();
    }

    @DeleteMapping("/deletarDocente/{id}")
    public void deletarDocente(@PathVariable("id") int id) {
        pessoaService.deletaDocente(id);
    }

}
