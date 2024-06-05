package com.tcc.aplicacao.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tcc.aplicacao.repository.UsuarioRepository;
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

    public Usuario salvar(Usuario usuario) {
        if (repository.findByUsername(usuario.getUsername()).isEmpty()) {
            String encryptedPassword = passwordEncoder.encode(usuario.getPassword());
            Usuario novoUsuario = new Usuario(usuario.getUsername(), encryptedPassword, usuario.getRole(),
                    usuario.getPessoa());
            return this.repository.save(novoUsuario);
        }
        return null;
    }

    public Usuario getInfoUsuario(String usuario) {
        return repository.findByUsuarioJPQL(usuario);
    }

}
