package com.tcc.aplicacao.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tcc.aplicacao.repository.UsuarioRepository;
import com.tcc.aplicacao.dto.LoginDto;
import com.tcc.aplicacao.dto.UsuarioDto;
import com.tcc.aplicacao.entities.Usuario;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository repository;

   private PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository){
        this.repository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<UsuarioDto> listaUsuario() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios = repository.findAll();
        return usuarios.stream().map(x -> new UsuarioDto(x)).collect(Collectors.toList());

    }

    public String salvar(Usuario usuario) {
        String senhaEncoder = this.passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaEncoder);
        repository.save(usuario);
        return "";
    }

    public UsuarioDto buscaUsuario(int id) {
        Usuario usuario = new Usuario();
        usuario = repository.findById(id).get();
        UsuarioDto dto = new UsuarioDto(usuario);
        return dto;
    }

    public String excluirUsuario(int id) {
        repository.deleteById(id);
        return "";
    }

    public ResponseEntity<String> autentica(LoginDto loginDto) {
        ResponseEntity<String> resposta = new ResponseEntity<>(HttpStatus.ACCEPTED);
        List<Usuario> usuarios = repository.findAll();
        for (Usuario usuario : usuarios) {
            if (usuario.getNomeUsuario().equals(loginDto.getNomeUsuario()) &&
                    passwordEncoder.matches(loginDto.getSenha(), usuario.getSenha())) {
                resposta = ResponseEntity.ok("Autenticado com sucesso!");
                break;
            } else {
                resposta = ResponseEntity.badRequest().body("Login Inválido");
            }
        }

        return resposta;

    }

}

