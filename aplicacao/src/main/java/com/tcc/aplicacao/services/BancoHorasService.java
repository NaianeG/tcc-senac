package com.tcc.aplicacao.services;

import com.tcc.aplicacao.entities.BancoHoras;
import com.tcc.aplicacao.entities.MarcacaoPonto;
import com.tcc.aplicacao.entities.Pessoa;
import com.tcc.aplicacao.entities.Usuario;
import com.tcc.aplicacao.repository.BancoHorasRepository;
import com.tcc.aplicacao.repository.MarcacaoPontoRepository;
import com.tcc.aplicacao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class BancoHorasService {
    @Autowired
    BancoHorasRepository bancoHorasRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    MarcacaoPontoRepository marcacaoPontoRepository;

    public void atualizarBancoHoras(int idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        BancoHoras bancoHoras = bancoHorasRepository.findByUsuarioId(idUsuario);
        List<MarcacaoPonto> marcacoes = marcacaoPontoRepository.findByIdUsuario(idUsuario);
        double saldoAtual = 0.0;
        for (MarcacaoPonto marcacao : marcacoes) {
            if (marcacao.getHoraSaida() != null) {
                long millisEntrada = marcacao.getHoraEntrada().getTime();
                long millisSaida = marcacao.getHoraSaida().getTime();
                long diff = millisSaida - millisEntrada;

                double horasTrabalhadas = TimeUnit.MILLISECONDS.toHours(diff)
                        + (TimeUnit.MILLISECONDS.toMinutes(diff) % 60) / 60.0;
                saldoAtual += horasTrabalhadas;
            }
        }
        bancoHoras.setSaldoAtual(saldoAtual);
        bancoHoras.setSaldoNegativo(saldoAtual < bancoHoras.getSaldoMensal());

        bancoHorasRepository.save(bancoHoras);
    }

    public void setarHoraMensal(Usuario user, Pessoa pessoa) {
        BancoHoras bancoHoras = new BancoHoras();
        bancoHoras.setUsuario(user);
        bancoHoras.setSaldoMensal(pessoa.getHorasMensais());
        bancoHorasRepository.save(bancoHoras);
    }
}
