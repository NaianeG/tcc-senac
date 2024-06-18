package com.tcc.aplicacao.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.tcc.aplicacao.entities.MarcacaoPonto;
import com.tcc.aplicacao.repository.MarcacaoPontoRepository;

import java.util.Collections;

@Service
public class MarcacaoPontoService {
    @Autowired
    MarcacaoPontoRepository marcacaoPontoRepository;

    @Autowired
    BancoHorasService bancoHorasService;

    public List<MarcacaoPonto> cadastroPonto(MarcacaoPonto marcacaoPonto) {
        MarcacaoPonto marcacaoPonto2 = marcacaoPontoRepository.findByDataAndIdUsuario(
                marcacaoPonto.getData(), marcacaoPonto.getIdUsuario());
        if (marcacaoPonto2 == null) {
            marcacaoPontoRepository.save(marcacaoPonto);
            System.out.println(marcacaoPonto.getIdUsuario());
            bancoHorasService.atualizarBancoHoras(marcacaoPonto.getIdUsuario());
            return marcacaoPontoRepository.findAll();
        } else if (marcacaoPonto2.getHoraSaida() == null) {
            marcacaoPonto2.setHoraSaida(marcacaoPonto.getHoraEntrada());
            marcacaoPontoRepository.save(marcacaoPonto2);
            bancoHorasService.atualizarBancoHoras(marcacaoPonto.getIdUsuario());
            return marcacaoPontoRepository.findAll();
        }
        return Collections.emptyList();
    }

    public Page<MarcacaoPonto> findAllByIdUsuario(int idUsuario, PageRequest pageRequest) {
        return marcacaoPontoRepository.findByIdUsuarioOrderByIdDesc(idUsuario, pageRequest);
    }

    public void deletarPonto(int id) {
        marcacaoPontoRepository.deleteById(id);
    }

    public MarcacaoPonto buscaMarcacaoPontoPorId(int id) {
        return marcacaoPontoRepository.findById(id);
    }

    public void salvar(MarcacaoPonto marcacaoPonto) {
        marcacaoPontoRepository.save(marcacaoPonto);
    }
}
