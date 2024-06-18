package com.tcc.aplicacao.services;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tcc.aplicacao.entities.Ajuste;
import com.tcc.aplicacao.entities.MarcacaoPonto;
import com.tcc.aplicacao.repository.AjusteRepository;
import com.tcc.aplicacao.repository.MarcacaoPontoRepository;

import jakarta.transaction.Transactional;

@Service
public class AjusteService {

    @Autowired
    AjusteRepository ajusteRepository;

    @Autowired
    private MarcacaoPontoRepository marcacaoPontoRepository;

    public void salvarAjuste(Ajuste ajuste, MultipartFile arquivoNovo) {
        if (!arquivoNovo.isEmpty()) {
            String nomeArquivo = arquivoNovo.getOriginalFilename();

            try {
                String diretorioImg = "uploads/";
                File dir = new File(diretorioImg);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File serverFile = new File(dir.getAbsolutePath() + File.separator + nomeArquivo);
                ajuste.setArquivo(serverFile.getAbsolutePath());
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));

                stream.write(arquivoNovo.getBytes());
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        ajusteRepository.save(ajuste);
    }

    public Ajuste buscarPorId(int id) {
        return ajusteRepository.findById(id).orElse(null);
    }

    public void salvar(Ajuste ajuste) {
        ajusteRepository.save(ajuste);
    }

    @Transactional
    public void aprovarAjuste(int ajusteId) {
        Ajuste ajuste = ajusteRepository.findById(ajusteId)
                .orElseThrow(() -> new IllegalArgumentException("Ajuste não encontrado"));
        MarcacaoPonto marcacaoPonto = ajuste.getMarcacaoPonto();
        marcacaoPonto.setHoraEntrada(ajuste.getHoraEntrada());
        marcacaoPonto.setHoraSaida(ajuste.getHoraSaida());
        ajuste.setStatus(true);
        marcacaoPontoRepository.save(marcacaoPonto);
        ajusteRepository.save(ajuste);
    }

    @Transactional
    public void rejeitarAjuste(int ajusteId) {
        Ajuste ajuste = ajusteRepository.findById(ajusteId)
                .orElseThrow(() -> new IllegalArgumentException("Ajuste não encontrado"));
        ajuste.setStatus(false);
        ajusteRepository.save(ajuste);
    }

    public List<Ajuste> buscarPorMarcacaoPontoId(int marcacaoPontoId) {
        return ajusteRepository.findByMarcacaoPontoId(marcacaoPontoId);
    }

    // Novo método para buscar todos os ajustes
    public List<Ajuste> buscarTodosAjustes() {
        return ajusteRepository.findAll();
    }

}
