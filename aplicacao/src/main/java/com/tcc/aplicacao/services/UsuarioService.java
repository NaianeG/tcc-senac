package com.tcc.aplicacao.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tcc.aplicacao.repository.UsuarioRepository;
import com.tcc.aplicacao.dto.CadastroDTO;
import com.tcc.aplicacao.dto.LoginDto;
import com.tcc.aplicacao.dto.UsuarioDto;
import com.tcc.aplicacao.entities.Usuario;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    AuthenticationManager authenticationManager;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<UsuarioDto> listaUsuario() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios = repository.findAll();
        return usuarios.stream().map(x -> new UsuarioDto(x)).collect(Collectors.toList());

    }

    public void salvar(CadastroDTO cadastroDTO) {
        if (this.repository.findByUsername(cadastroDTO.nomeUsuario()) == null) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(cadastroDTO.senha());
            Usuario novoUsuario = new Usuario(cadastroDTO.nomeUsuario(), encryptedPassword, cadastroDTO.role());
            System.out.println(cadastroDTO.role());
            this.repository.save(novoUsuario);
        }
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

    public String autentica(LoginDto loginDto) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password());

        var auth = this.authenticationManager.authenticate(usernamePassword);
        System.err.println("Teste" + auth.getAuthorities());
        return "Usuário autentincado";
    }

    // public ResponseEntity<String> autentica(LoginDto loginDto) {
    // // Verifique se o usuário existe no repositório
    // Usuario usuario = repository.findByNomeUsuario(loginDto.getNomeUsuario());

    // // Verifique se as credenciais estão corretas
    // if (usuario != null && usuario.getSenha().equals(loginDto.getSenha())) {
    // // Credenciais corretas, retorne uma resposta de sucesso
    // return ResponseEntity.ok("Login bem-sucedido!");
    // } else {
    // // Credenciais incorretas, retorne uma resposta de erro
    // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais
    // inválidas");
    // }
    // }

    // public ResponseEntity<String> autentica(LoginDto loginDto) {
    // ResponseEntity<String> resposta = new ResponseEntity<>(HttpStatus.ACCEPTED);
    // List<Usuario> usuarios = repository.findAll();
    // for (Usuario usuario : usuarios) {
    // if (usuario.getNomeUsuario().equals(loginDto.getNomeUsuario()) &&
    // passwordEncoder.matches(loginDto.getSenha(), usuario.getSenha())) {
    // resposta = ResponseEntity.ok("Autenticado com sucesso!");
    // break;
    // } else {
    // resposta = ResponseEntity.badRequest().body("Login Inválido");
    // }
    // }

    // return resposta;

    // }

}
