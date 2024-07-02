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
        System.out.println(bancoHoras);
        List<MarcacaoPonto> marcacoes = marcacaoPontoRepository.findByIdUsuario(idUsuario);
        long saldoAtual = bancoHoras.getSaldoAtual();

        for (MarcacaoPonto marcacao : marcacoes) {
            if (marcacao.getHoraSaida() != null) {
                long millisEntrada = marcacao.getHoraEntrada().getTime();
                long millisSaida = marcacao.getHoraSaida().getTime();
                long diff = millisSaida - millisEntrada;

                // Depuração: converter milissegundos para horas e minutos
                long diffHoras = diff / (1000 * 60 * 60);
                long diffMinutos = (diff / (1000 * 60)) % 60;
                System.out.println("Horas trabalhadas: " + diffHoras + "h " + diffMinutos + "m");

                saldoAtual += diff;
            }
        }

        bancoHoras.setSaldoAtual(saldoAtual);
        bancoHorasRepository.save(bancoHoras);
    }

    public void setarHoraMensal(Usuario user, Pessoa pessoa) {
        BancoHoras bancoHoras = bancoHorasRepository.findByUsuarioId(user.getId());
        if (bancoHoras == null) {
            bancoHoras = new BancoHoras();
            bancoHoras.setUsuario(user);
            bancoHoras.setSaldoMensal(pessoa.getHorasMensais());
        } else {
            bancoHoras.setSaldoMensal(pessoa.getHorasMensais());
        }
        bancoHorasRepository.save(bancoHoras);
    }

    public List<BancoHoras> buscaBancosHoras() {
        return bancoHorasRepository.findAll();
    }
}
