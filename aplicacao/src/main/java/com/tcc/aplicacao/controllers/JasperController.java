package com.tcc.aplicacao.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import com.tcc.aplicacao.repository.PessoaRepository;
import com.tcc.aplicacao.services.JasperService;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

@Controller
public class JasperController {

    @Autowired
    private JasperService service;

    @Autowired
    private PessoaRepository pessoaRepository;

    @GetMapping("/relatorio/pdf/relatorio-docente")
    public void exibirRelatorio01(@RequestParam("code") String code,
            @RequestParam("acao") String acao,
            HttpServletResponse response) throws IOException {

        byte[] bytes = service.exportarPDF(code);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        if (acao.equals("v")) {
            response.setHeader("attachment", "incline; filename=relatorio-" + code + ".pdf");
        } else {
            response.setHeader("Content-disposition", "incline; filename=relatorio-" + code + ".pdf");

        }
        response.getOutputStream().write(bytes);

    }

    @GetMapping("/relatorio/pdf/relatorio-horas/{code}")
    public void exibirRelatorio02(@PathVariable("code") String code,
            @RequestParam(name = "nomeCompleto", required = false) String nomeCompleto,
            HttpServletResponse response) throws IOException {
        service.addParams("NomeDocente", nomeCompleto.isEmpty() ? null : nomeCompleto);

        byte[] bytes = service.exportarPDF(code);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("attachment", "incline; filename=relatorio-" + code + ".pdf");
        response.getOutputStream().write(bytes);

    }

    @GetMapping("/relatorio/excel/relatorio-horas/{code}")
    public void exibirRelatorio03(@PathVariable("code") String code,
            @RequestParam(name = "nomeCompleto", required = false) String nomeCompleto,
            HttpServletResponse response) throws IOException, JRException {
        service.addParams("NomeDocente", (nomeCompleto == null || nomeCompleto.isEmpty()) ? null : nomeCompleto);

        JRXlsxExporter exporter = service.gerarEXCEL(code, response);

        if (exporter != null) {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=relatorio-" + code + ".xlsx");

            exporter.exportReport();
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao gerar relatório");
        }
    }

    @ModelAttribute("pessoa")
    public List<String> getnomeCompleto() {
        List<String> lista = new ArrayList<>();
        lista = pessoaRepository.findNomes();
        System.out.println("Teste lista relatório = " + lista);
        return lista;
    }

}
