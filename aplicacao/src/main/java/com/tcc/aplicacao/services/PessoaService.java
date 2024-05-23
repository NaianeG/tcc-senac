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
        try {
            pessoaRepository.save(pessoa);
        } catch (Exception e) {
            System.out.println("Exception:" + e.getLocalizedMessage());
        }
    }

    public ModelAndView listaDocentes() {
        ModelAndView mv = new ModelAndView("listaDocentes");
        List<Pessoa> docentes = new ArrayList<>();
        docentes = pessoaRepository.findAll();
        mv.addObject("pessoas", docentes);
        return mv;
    }

    public Pessoa buscaDocentePorId(int id) {
        return pessoaRepository.findById(id).get();
    }

    public void deletaDocente(@PathVariable("id") int id) {
        try {
            pessoaRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("Exception:" + e.getLocalizedMessage());
        }
    }

}
