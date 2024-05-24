package com.tcc.aplicacao.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tcc.aplicacao.repository.UsuarioRepository;
import com.tcc.aplicacao.dto.CadastroDTO;
import com.tcc.aplicacao.dto.UsuarioDto;
import com.tcc.aplicacao.entities.Usuario;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<UsuarioDto> listaUsuario() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios = repository.findAll();
        return usuarios.stream().map(x -> new UsuarioDto(x)).collect(Collectors.toList());

    }

    public void salvar(CadastroDTO cadastroDTO) {
        if (repository.findByUsername(cadastroDTO.nomeUsuario()).isEmpty()) {
            String encryptedPassword = passwordEncoder.encode(cadastroDTO.senha());
            Usuario novoUsuario = new Usuario(cadastroDTO.nomeUsuario(), encryptedPassword, cadastroDTO.role());
            System.out.println(cadastroDTO.role());
            this.repository.save(novoUsuario);
        }
    }

    public Usuario getInfoUsuario(String usuario) {
        return repository.findByUsuarioJPQL(usuario);
    }

    // public UsuarioDto buscaUsuario(Integer id) {
    // Usuario usuario = new Usuario();
    // usuario = repository.findById(id).get();
    // UsuarioDto dto = new UsuarioDto(usuario);
    // return dto;
    // }

    public String excluirUsuario(Integer id) {
        repository.deleteById(id);
        return "";
    }

}
