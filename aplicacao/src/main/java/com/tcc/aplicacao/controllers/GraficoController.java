package com.tcc.aplicacao.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcc.aplicacao.entities.Ajuste;
import com.tcc.aplicacao.entities.BancoHoras;
import com.tcc.aplicacao.entities.MarcacaoPonto;
import com.tcc.aplicacao.entities.Usuario;
import com.tcc.aplicacao.services.AjusteService;
import com.tcc.aplicacao.services.BancoHorasService;
import com.tcc.aplicacao.services.MarcacaoPontoService;
import com.tcc.aplicacao.services.UsuarioService;

@Controller
public class GraficoController {

    @Autowired
    MarcacaoPontoService marcacaoPontoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    AjusteService ajusteService;

    @Autowired
    BancoHorasService bancoHorasService;

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/montandoGrafico")
    public ModelAndView mostraGrafico() {
        return new ModelAndView("montandoGrafico");
    }

    @GetMapping("/dadosGrafico")
    @ResponseBody
    public String getDadosGrafico() throws Exception {
        List<MarcacaoPonto> listaMarcacoes = marcacaoPontoService.getListaPontoDocente();
        List<Usuario> listaUsuarios = usuarioService.getListaUsuario();
        List<Ajuste> listaAjustes = ajusteService.buscarTodosAjustes();
        List<BancoHoras> listaBancoHoras = bancoHorasService.buscaBancosHoras();

        var dadosPorUsuario = listaUsuarios.stream().map(usuario -> {
            var marcacoesUsuario = listaMarcacoes.stream()
                    .filter(m -> m.getIdUsuario() == usuario.getId())
                    .collect(Collectors.toList());
            var ajustesUsuario = listaAjustes.stream()
                    .filter(a -> a.getMarcacaoPonto() != null && a.getMarcacaoPonto().getIdUsuario() == usuario.getId())
                    .collect(Collectors.toList());
            var bancoHorasUsuario = listaBancoHoras.stream()
                    .filter(bh -> bh.getUsuario().getId() == usuario.getId())
                    .collect(Collectors.toList());
            return new Object[] { usuario.getPessoa().getNomeCompleto(), marcacoesUsuario, ajustesUsuario,
                    bancoHorasUsuario };
        }).collect(Collectors.toList());

        return objectMapper.writeValueAsString(dadosPorUsuario);
    }

}
