package com.tcc.aplicacao.controllers;

import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tcc.aplicacao.entities.Ajuste;
import com.tcc.aplicacao.entities.MarcacaoPonto;
import com.tcc.aplicacao.entities.Usuario;
import com.tcc.aplicacao.services.AjusteService;
import com.tcc.aplicacao.services.MarcacaoPontoService;

@Controller
@SessionAttributes("usuario")
public class AjusteController {

    @Autowired
    AjusteService ajusteService;

    @Autowired
    MarcacaoPontoService marcacaoPontoService;

    @GetMapping("pedirAjuste/{id}")
    public String pedirAjuste(@PathVariable(name = "id") int id, @ModelAttribute("usuario") Usuario usuario,
            Model model) {
        MarcacaoPonto marcacaoPonto = marcacaoPontoService.buscaMarcacaoPontoPorId(id);

        // Formatando as horas para remover os segundos se não forem nulas
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String horaEntradaFormatada = marcacaoPonto.getHoraEntrada() != null
                ? marcacaoPonto.getHoraEntrada().toLocalTime().format(timeFormatter)
                : null;
        String horaSaidaFormatada = marcacaoPonto.getHoraSaida() != null
                ? marcacaoPonto.getHoraSaida().toLocalTime().format(timeFormatter)
                : null;

        Ajuste ajuste = new Ajuste();
        ajuste.setFkIdMarcacaoPonto(marcacaoPonto.getId());
        ajuste.setData(marcacaoPonto.getData());
        ajuste.setHoraEntrada(
                horaEntradaFormatada != null ? Time.valueOf(LocalTime.parse(horaEntradaFormatada, timeFormatter))
                        : null);
        ajuste.setHoraSaida(
                horaSaidaFormatada != null ? Time.valueOf(LocalTime.parse(horaSaidaFormatada, timeFormatter)) : null);

        model.addAttribute("marcacaoPonto", marcacaoPonto);
        model.addAttribute("ajuste", ajuste);
        model.addAttribute("usuario", usuario);
        return "pedirAjuste";
    }

    @PostMapping("/salvarAjuste")
    public String salvarAjuste(@ModelAttribute Ajuste ajuste, @RequestParam("arquivo") MultipartFile arquivo,
            RedirectAttributes redirectAttributes) {
        ajusteService.salvarAjuste(ajuste, arquivo);
        redirectAttributes.addFlashAttribute("message", "Ajuste solicitado com sucesso");
        return "redirect:/ponto/listaPonto/" + ajuste.getFkIdMarcacaoPonto();
    }
}
