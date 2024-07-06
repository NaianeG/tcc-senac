package com.tcc.aplicacao.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
import com.tcc.aplicacao.exceptions.AjusteExistenteException;
import com.tcc.aplicacao.services.AjusteService;
import com.tcc.aplicacao.services.MarcacaoPontoService;

@Controller
@SessionAttributes("usuario")
public class AjusteController {

        @Autowired
        private AjusteService ajusteService;

        @Autowired
        private MarcacaoPontoService marcacaoPontoService;

        @GetMapping("pedirAjuste/{id}")
        public String pedirAjuste(@PathVariable(name = "id") int id, Model model) {
                MarcacaoPonto marcacaoPonto = marcacaoPontoService.buscaMarcacaoPontoPorId(id);
                Ajuste ajuste = new Ajuste();
                ajuste.setMarcacaoPonto(marcacaoPonto);
                ajuste.setData(marcacaoPonto.getData());
                ajuste.setHoraEntrada(marcacaoPonto.getHoraEntrada());
                ajuste.setHoraSaida(marcacaoPonto.getHoraSaida());
                model.addAttribute("ajuste", ajuste);
                return "pedirAjuste";
        }

        @PostMapping("/salvarAjuste")
        public String salvarAjuste(@ModelAttribute Usuario usuario, @ModelAttribute Ajuste ajuste,
                        @RequestParam("arquivoNovo") MultipartFile arquivoNovo,
                        @RequestParam("horaEntradaStr") String horaEntradaStr,
                        @RequestParam("horaSaidaStr") String horaSaidaStr,
                        RedirectAttributes redirectAttributes) {
                // Converte as horas de String para Time
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

                try {
                        if (horaEntradaStr != null && !horaEntradaStr.isEmpty()) {
                                Time horaEntrada = new Time(sdf.parse(horaEntradaStr).getTime());
                                ajuste.setHoraEntrada(horaEntrada);
                        }

                        if (horaSaidaStr != null && !horaSaidaStr.isEmpty()) {
                                Time horaSaida = new Time(sdf.parse(horaSaidaStr).getTime());
                                ajuste.setHoraSaida(horaSaida);
                        }

                        ajusteService.salvarAjuste(ajuste, arquivoNovo);
                        redirectAttributes.addFlashAttribute("message", "Ajuste solicitado com sucesso");
                        return "redirect:/ponto/listaPonto/" + usuario.getId();
                } catch (ParseException e) {
                        e.printStackTrace();
                        redirectAttributes.addFlashAttribute("error",
                                        "Erro ao converter as horas. Por favor, tente novamente.");
                        return "redirect:/pedirAjuste/" + ajuste.getMarcacaoPonto().getId();
                } catch (AjusteExistenteException e) {
                        redirectAttributes.addFlashAttribute("error",
                                        "Já possui um ajuste solicitado para esse registro de ponto");
                        return "redirect:/ponto/listaPonto/" + usuario.getId();
                }
        }

        @PostMapping("/aprovarAjuste/{id}")
        public String aprovarAjuste(@PathVariable(name = "id") int id, RedirectAttributes redirectAttributes) {
                ajusteService.aprovarAjuste(id);
                redirectAttributes.addFlashAttribute("message", "Ajuste aprovado com sucesso");
                return "redirect:/listarAjustes";
        }

        @PostMapping("/rejeitarAjuste/{id}")
        public String rejeitarAjuste(@PathVariable(name = "id") int id, RedirectAttributes redirectAttributes) {
                ajusteService.rejeitarAjuste(id);
                redirectAttributes.addFlashAttribute("message", "Ajuste rejeitado com sucesso");
                return "redirect:/listarAjustes";
        }

        @GetMapping("/listarAjustes")
        public String listarAjustes(Model model) {
                model.addAttribute("ajustes", ajusteService.buscarTodosAjustesComDocente());
                return "listarAjustes";
        }

        @GetMapping("/downloadArquivo/{id}")
        public ResponseEntity<InputStreamResource> downloadArquivo(@PathVariable int id) {
                Ajuste ajuste = ajusteService.buscarPorId(id);
                if (ajuste == null || ajuste.getArquivo() == null) {
                        throw new IllegalArgumentException("Ajuste ou arquivo não encontrado");
                }

                File file = new File(ajuste.getArquivo());
                if (!file.exists()) {
                        throw new IllegalArgumentException("Arquivo não encontrado no servidor");
                }

                try {
                        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
                        return ResponseEntity.ok()
                                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                                        "attachment;filename=" + file.getName())
                                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                                        .contentLength(file.length())
                                        .body(resource);
                } catch (IOException e) {
                        e.printStackTrace();
                        throw new IllegalArgumentException("Erro ao baixar o arquivo");
                }
        }
}
