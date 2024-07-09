package com.tcc.aplicacao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tcc.aplicacao.entities.Pessoa;
import com.tcc.aplicacao.entities.Usuario;
import com.tcc.aplicacao.services.BancoHorasService;
import com.tcc.aplicacao.services.PessoaService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private BancoHorasService bancoHorasService;

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
        return pessoaService.editarDocentePorId(id);
    }

    @PostMapping("/editarDocente/{id}")
    public String atualizaPessoa(@PathVariable("id") int id, Pessoa pessoa, Usuario usuario) {
        pessoa.setId(id);
        usuario.setPessoa(pessoa);
        try {
            pessoaService.atualizaDocente(pessoa, usuario);
        } catch (Exception e) {
            System.out.println("Exception:" + e.getLocalizedMessage());
        }
        return "redirect:/listaDocentes";
    }

    @GetMapping("/listaDocentes")
    public ModelAndView listaDocentes() {
        return pessoaService.listaDocentes();
    }

    @GetMapping("/deletarDocente/{id}")
    public String deletarDocente(@PathVariable("id") int id) {
        try {
            System.err.println("Deletando docente " + id);
            Usuario usuario = pessoaService.buscarUsuarioPorPessoaId(id);
            if (usuario != null) {
                bancoHorasService.deletarBancoHorasPorUsuarioId(usuario.getId());
                pessoaService.deletaDocente(id);
            } else {
                System.out.println("Usuário não encontrado para o ID da pessoa: " + id);
            }
        } catch (Exception e) {
            System.out.println("Exception:" + e.getLocalizedMessage());
        }
        return "redirect:/listaDocentes";
    }
}
