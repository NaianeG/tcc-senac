package com.tcc.aplicacao.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tcc.aplicacao.entities.Usuario;
import com.tcc.aplicacao.repository.UsuarioRepository;

@Service
public class UsuarioDetailService implements UserDetailsService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByUsername(username);

        var userObj = usuarioOptional.get();
        if (usuarioOptional.isPresent()) {
            return User.builder()
                    .username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .roles(getRoles(userObj))
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    private String[] getRoles(Usuario userObj) {
        if (userObj.getRole() == null) {
            return new String[] { "USER" };
        }
        return userObj.getRole().split(",");
    }

}
