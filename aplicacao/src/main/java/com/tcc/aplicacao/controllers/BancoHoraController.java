package com.tcc.aplicacao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tcc.aplicacao.entities.Usuario;
import com.tcc.aplicacao.services.BancoHorasService;
import com.tcc.aplicacao.services.PessoaService;

@Controller
public class BancoHoraController {

    @Autowired
    PessoaService pessoaService;

    @Autowired
    BancoHorasService bancoHorasService;

    @GetMapping("/zerarBancoHoras/{id}")
    public String zerarBancoHoras(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try {
            Usuario usuario = pessoaService.buscarUsuarioPorPessoaId(id);
            if (usuario != null) {
                boolean sucesso = bancoHorasService.zerarBancoHoras(usuario.getId());
                if (sucesso) {
                    redirectAttributes.addFlashAttribute("mensagem", "Banco de horas zerado com sucesso.");
                    redirectAttributes.addFlashAttribute("tipoMensagem", "sucesso");
                } else {
                    redirectAttributes.addFlashAttribute("mensagem", "Erro ao zerar banco de horas.");
                    redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
                }
            } else {
                redirectAttributes.addFlashAttribute("mensagem", "Usuário não encontrado.");
                redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagem",
                    "Erro ao zerar banco de horas: " + e.getLocalizedMessage());
            redirectAttributes.addFlashAttribute("tipoMensagem", "erro");
        }
        return "redirect:/listaDocentes";
    }
}
