package com.tcc.aplicacao.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tcc.aplicacao.entities.MarcacaoPonto;
import com.tcc.aplicacao.services.MarcacaoPontoService;

@Controller
@RequestMapping("/ponto/")
public class MarcacaoPontoController {

    @Autowired
    MarcacaoPontoService marcacaoPontoService;

    ModelAndView mv;

    @PostMapping("cadastroPonto")
    public ModelAndView cadastroPonto(MarcacaoPonto marcacaoPonto, RedirectAttributes redirectAttributes) {

        mv = new ModelAndView("redirect:/home");
        List<MarcacaoPonto> marcacaoList;
        marcacaoList = marcacaoPontoService.cadastroPonto(marcacaoPonto);
        if (marcacaoList.isEmpty()) {
            redirectAttributes.addFlashAttribute("alerta", "limiteAtingido");
        }
        mv.addObject("listaHoraMarcada", marcacaoList);
        return mv;
    }

    @GetMapping("listaPonto")
    public ModelAndView ajustePonto(@RequestParam(value = "id") Integer idUsuario) {
        mv = new ModelAndView("homeDocente");
        mv.addObject("pessoas", marcacaoPontoService.listaPonto(idUsuario));
        return mv;
    }
}
