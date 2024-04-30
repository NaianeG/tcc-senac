package com.tcc.aplicacao.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.tcc.aplicacao.entities.Pessoa;
import com.tcc.aplicacao.repository.PessoaRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PessoaService {

    @Autowired
    PessoaRepository pessoaRepository;

    public void cadastraDocente(Pessoa pessoa) {
        pessoaRepository.save(pessoa);
    }

    public ModelAndView listaDocente() {
        ModelAndView mv = new ModelAndView("homeDocente");
        List<Pessoa> docentes = new ArrayList<>();
        docentes = pessoaRepository.findAll();
        mv.addObject("docentes", docentes);
        return mv;
    }

    public Pessoa buscaDocentePorId(int id) {
        return pessoaRepository.findById(id).get();
    }

    public void deletaDocente(@PathVariable("id") int id) {
        pessoaRepository.deleteById(id);
    }

}
