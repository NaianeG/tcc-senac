package com.tcc.aplicacao.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GraficoController {

    @GetMapping("/montandoGrafico")
    public ModelAndView mostraGrafico() {
        ModelAndView mv = new ModelAndView("montandoGrafico");
        return mv;
    }
}
