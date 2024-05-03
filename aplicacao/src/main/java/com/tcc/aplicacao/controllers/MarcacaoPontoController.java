package com.tcc.aplicacao.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tcc.aplicacao.entities.MarcacaoPonto;
import com.tcc.aplicacao.services.MarcacaoPontoService;

@Controller
public class MarcacaoPontoController {

    @Autowired
    MarcacaoPontoService marcacaoPontoService;

    ModelAndView mv;

    @GetMapping("home")
    public String home() {
        return "home";
    }

    @PostMapping("cadastroPonto")
    public ModelAndView cadastroPedido(MarcacaoPonto marcacaoPonto) {
        mv = new ModelAndView("home");
        List<MarcacaoPonto> marcacaoList;
        marcacaoList = marcacaoPontoService.cadastroPonto(marcacaoPonto);
        if (marcacaoList.isEmpty()) {
            mv.addObject("alerta", "limiteAtingido");
        }
        mv.addObject("listaHoraMarcada", marcacaoList);
        return mv;
    }

    @GetMapping("listaPonto")
    public ModelAndView ajustePonto(@RequestParam(value = "id") Integer idUsuario) {
        mv = new ModelAndView("listaPonto");
        mv.addObject("listaPonto", marcacaoPontoService.listaPonto(idUsuario));
        return mv;
    }
}
