package com.tcc.aplicacao.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.servlet.ModelAndView;
import com.tcc.aplicacao.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.tcc.aplicacao.services.UsuarioService;

@Controller
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/home")
    public ModelAndView home(@AuthenticationPrincipal User user) {
        ModelAndView mv = new ModelAndView("home");
        Usuario usuario = new Usuario();
        usuario = usuarioService.getInfoUsuario(user.getUsername());
        mv.addObject("usuario", usuario);
        return mv;
    }

    @GetMapping("/login")
    public String paginaLogin() {
        return "loginPage";
    }

}
