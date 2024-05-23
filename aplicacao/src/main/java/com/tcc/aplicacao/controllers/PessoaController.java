package com.tcc.aplicacao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tcc.aplicacao.entities.Pessoa;
import com.tcc.aplicacao.services.PessoaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PessoaController {

    @Autowired
    PessoaService pessoaService;

    @GetMapping("/cadastroDocente")
    public String formCadastroDocente() {
        return "formCadastroDocente";
    }

    @PostMapping("/cadastrarDocente")
    public String cadastroPessoa(Pessoa pessoa) {
        try {
            pessoaService.cadastraDocente(pessoa);
        } catch (Exception e) {
            System.out.println("Exception:" + e.getLocalizedMessage());
        }
        return "redirect:/cadastroDocente";
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
    public String deletarDocente(@PathVariable("id") int id) {
        try {
            pessoaService.deletaDocente(id);
        } catch (Exception e) {
            System.out.println("Exception:" + e.getLocalizedMessage());
        }
        return "redirect:/listaDocentes";
    }

}
