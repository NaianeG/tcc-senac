package com.tcc.aplicacao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tcc.aplicacao.entities.Pessoa;
import com.tcc.aplicacao.entities.Usuario;
import com.tcc.aplicacao.services.PessoaService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping("pesquisaDocente")
    public String pesquisaDocente() {
        return "pesquisaDocente";
    }

    @GetMapping("/cadastroDocente")
    public String formCadastroDocente() {
        return "formCadastroDocente";
    }

    @PostMapping("/cadastrarDocente")
    public String cadastroPessoa(Pessoa pessoa, Usuario usuario) {
        try {
            pessoaService.cadastraDocente(pessoa, usuario);
        } catch (Exception e) {
            System.out.println("Exception:" + e.getLocalizedMessage());
        }
        return "redirect:/listaDocentes";
    }

    @GetMapping("/editarDocente/{id}")
    public ModelAndView buscaDocentePorId(@PathVariable("id") int id) {
        ModelAndView mv = new ModelAndView();
        mv.addObject(pessoaService.editarDocentePorId(id));
        return mv;
    }

    @GetMapping("/listaDocentes")
    public ModelAndView listaDocentes() {
        return pessoaService.listaDocentes();
    }

    @GetMapping("/deletarDocente/{id}")
    public String deletarDocente(@PathVariable("id") int id) {
        try {
            pessoaService.deletaDocente(id);
        } catch (Exception e) {
            System.out.println("Exception:" + e.getLocalizedMessage());
        }
        return "redirect:/listaDocentes";
    }

}
