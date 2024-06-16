package com.tcc.aplicacao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tcc.aplicacao.entities.MarcacaoPonto;
import com.tcc.aplicacao.entities.Usuario;
import com.tcc.aplicacao.services.MarcacaoPontoService;

@Controller
@RequestMapping("/ponto/")
@SessionAttributes("usuario")
public class MarcacaoPontoController {

    @Autowired
    private MarcacaoPontoService marcacaoPontoService;

    @GetMapping("listaPonto/{id}")
    public String listaPonto(@PathVariable int id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @ModelAttribute("usuario") Usuario usuario,
            Model model) {
        Page<MarcacaoPonto> listaPonto = marcacaoPontoService.findAllByIdUsuario(id, PageRequest.of(page, size));
        model.addAttribute("listaPonto", listaPonto);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", listaPonto.getTotalPages());
        model.addAttribute("id", id);
        model.addAttribute("size", size);
        return "listaPonto";
    }
}
