package com.tcc.aplicacao.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tcc.aplicacao.entities.Ajuste;
import com.tcc.aplicacao.repository.AjusteRepository;
import com.tcc.aplicacao.repository.MarcacaoPontoRepository;

@Service
public class AjusteService {

    @Autowired
    private AjusteRepository ajusteRepository;

    @Autowired
    private MarcacaoPontoRepository marcacaoPontoRepository;

    public void salvarAjuste(Ajuste ajuste, MultipartFile arquivo) {
        if (!arquivo.isEmpty()) {
            // lógica para salvar o arquivo no sistema de arquivos ou base de dados
            ajuste.setArquivo(arquivo.getOriginalFilename());
        }
        ajusteRepository.save(ajuste);
    }

    public List<Ajuste> buscarAjustesPorUsuario(int idUsuario) {
        return ajusteRepository.findByFkIdMarcacaoPontoIn(marcacaoPontoRepository.findIdByIdUsuario(idUsuario));
    }
}
