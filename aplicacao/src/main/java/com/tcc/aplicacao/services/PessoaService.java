package com.tcc.aplicacao.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private BancoHorasService bancoHorasService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void cadastraDocente(Pessoa pessoa, Usuario usuario) {
        try {
            Pessoa pessoa2 = pessoaRepository.save(pessoa);
            usuario.setPessoa(pessoa2);
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            usuarioService.salvar(usuario);
            bancoHorasService.setarHoraMensal(usuario, pessoa2);
        } catch (Exception e) {
            System.out.println("Exception:" + e.getLocalizedMessage());
        }
    }

    public void atualizaDocente(Pessoa pessoa, Usuario usuario) {
        try {
            Pessoa existingPessoa = pessoaRepository.findById(pessoa.getId()).orElseThrow();
            Usuario existingUsuario = usuarioRepository.findByIdPessoa(pessoa.getId());

            existingPessoa.setNomeCompleto(pessoa.getNomeCompleto());
            existingPessoa.setTelefone(pessoa.getTelefone());
            existingPessoa.setDataNascimento(pessoa.getDataNascimento());
            existingPessoa.setCpf(pessoa.getCpf());
            existingPessoa.setEmail(pessoa.getEmail());
            existingPessoa.setHorasMensais(pessoa.getHorasMensais());

            pessoaRepository.save(existingPessoa);

            existingUsuario.setUsername(usuario.getUsername());
            if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(usuario.getPassword());
                existingUsuario.setPassword(encodedPassword);
            }
            existingUsuario.setRole(usuario.getRole());
            bancoHorasService.setarHoraMensal(existingUsuario, existingPessoa);
            usuarioRepository.save(existingUsuario);
        } catch (Exception e) {
            System.out.println("Exception:" + e.getLocalizedMessage());
        }
    }

    public ModelAndView editarDocentePorId(int id) {
        ModelAndView mv = new ModelAndView("formCadastroDocente");
        Pessoa docente = pessoaRepository.findById(id).orElseThrow();
        Usuario usuario = usuarioRepository.findByIdPessoa(id);
        mv.addObject("pessoa", docente);
        mv.addObject("usuario", usuario);
        return mv;
    }

    public ModelAndView listaDocentes() {
        ModelAndView mv = new ModelAndView("listaDocentes");
        List<Pessoa> docentes = new ArrayList<>();
        docentes = pessoaRepository.findAll();
        mv.addObject("pessoas", docentes);
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
