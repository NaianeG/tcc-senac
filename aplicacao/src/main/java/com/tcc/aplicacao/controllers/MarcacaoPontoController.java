package com.tcc.aplicacao.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tcc.aplicacao.entities.MarcacaoPonto;
import com.tcc.aplicacao.entities.Usuario;
import com.tcc.aplicacao.services.MarcacaoPontoService;

@Controller
@RequestMapping("/ponto/")
@SessionAttributes("usuario")
public class MarcacaoPontoController {

    @Autowired
    private MarcacaoPontoService marcacaoPontoService;

    @PostMapping("cadastroPonto")
    public ModelAndView cadastroPonto(MarcacaoPonto marcacaoPonto, RedirectAttributes redirectAttributes) {

        ModelAndView mv = new ModelAndView("redirect:/home");
        List<MarcacaoPonto> marcacaoList = marcacaoPontoService.cadastroPonto(marcacaoPonto);
        if (marcacaoList.isEmpty()) {
            redirectAttributes.addFlashAttribute("alerta", "limiteAtingido");
        }
        mv.addObject("listaHoraMarcada", marcacaoList);
        return mv;
    }

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

        // Adicione logs
        System.out.println("currentPage: " + page);
        System.out.println("totalPages: " + listaPonto.getTotalPages());
        System.out.println("size: " + size);

        return "listaPonto";
    }

    @GetMapping("deletarPonto/{id}")
    public String deletarPonto(@PathVariable(name = "id") int id,
            @RequestParam int idUsuario,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            RedirectAttributes redirectAttributes) {
        marcacaoPontoService.deletarPonto(id);
        redirectAttributes.addFlashAttribute("message", "Ponto deletado com sucesso");
        return "redirect:/ponto/listaPonto/" + idUsuario + "?page=" + page + "&size=" + size;
    }
}
