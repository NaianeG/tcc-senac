package com.tcc.aplicacao.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tcc.aplicacao.repository.UsuarioRepository;
import com.tcc.aplicacao.dto.CadastroDTO;
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

    public Usuario salvar(CadastroDTO cadastroDTO) {
        if (repository.findByUsername(cadastroDTO.nomeUsuario()).isEmpty()) {
            String encryptedPassword = passwordEncoder.encode(cadastroDTO.senha());
            Usuario novoUsuario = new Usuario(cadastroDTO.nomeUsuario(), encryptedPassword, cadastroDTO.role(),
                    cadastroDTO.pessoa());
            return this.repository.save(novoUsuario);
        }
        return null;
    }

    public Usuario getInfoUsuario(String usuario) {
        return repository.findByUsuarioJPQL(usuario);
    }

}
