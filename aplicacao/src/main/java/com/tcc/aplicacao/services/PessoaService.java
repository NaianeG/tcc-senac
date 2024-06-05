package com.tcc.aplicacao.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.tcc.aplicacao.entities.Pessoa;
import com.tcc.aplicacao.entities.Usuario;
import com.tcc.aplicacao.repository.PessoaRepository;
import com.tcc.aplicacao.repository.UsuarioRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void cadastraDocente(Pessoa pessoa, Usuario usuario) {
        try {
            Pessoa pessoa2 = pessoaRepository.save(pessoa);

            usuario.setPessoa(pessoa2);

            usuarioService.salvar(usuario);
            System.out.println("++++++++++++++++++++++++Pessoa Service++++++++++++++++++++++++++++++++++++++++");
            System.out.println("Usuário  nome = " + usuario.getUsername());
            System.out.println("Usuário  senha = " + usuario.getPassword());
            System.out.println("Usuário  role =  " + usuario.getRole());
            System.out.println("Usuário  pessoa = " + usuario.getPessoa());
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

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

    public ModelAndView editarDocentePorId(@PathVariable("id") int id) {
        ModelAndView mv = new ModelAndView("formCadastroDocente");
        Pessoa docente = new Pessoa();
        Usuario usuario = new Usuario();
        usuario = usuarioRepository.findByIdPessoa(id);
        docente = pessoaRepository.findById(id).get();
        mv.addObject("pessoa", docente);
        mv.addObject("usuario", usuario);
        return mv;
    }

    public void deletaDocente(@PathVariable("id") int id) {
        try {
            pessoaRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("Exception:" + e.getLocalizedMessage());
        }
    }

}
