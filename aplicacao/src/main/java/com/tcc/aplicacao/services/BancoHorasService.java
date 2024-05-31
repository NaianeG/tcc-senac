package com.tcc.aplicacao.services;

import com.tcc.aplicacao.entities.BancoHoras;
import com.tcc.aplicacao.entities.Usuario;
import com.tcc.aplicacao.repository.BancoHorasRepository;
import com.tcc.aplicacao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

@Service
public class BancoHorasService {
    @Autowired
    BancoHorasRepository bancoHorasRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public void atualizarBancoHoras(int idUsuario, Time horaEntrada, Time horaSaida) {
        Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        BancoHoras bancoHoras = bancoHorasRepository.findByUsuarioId(idUsuario);
        if (bancoHoras == null) {
            bancoHoras = new BancoHoras();
            bancoHoras.setUsuario(usuario);
            bancoHoras.setSaldoMensal(40.00);
            bancoHoras.setSaldoNegativo(false);
        }

        long millisEntrada = horaEntrada.getTime();
        long millisSaida = horaSaida.getTime();
        long diff = millisSaida - millisEntrada;

        double horasTrabalhadas = TimeUnit.MILLISECONDS.toHours(diff)
                + (TimeUnit.MILLISECONDS.toMinutes(diff) % 60) / 60.0;
        bancoHoras.setSaldoMensal(bancoHoras.getSaldoMensal() + horasTrabalhadas);

        bancoHoras.setSaldoNegativo(bancoHoras.getSaldoMensal() < 0);

        bancoHorasRepository.save(bancoHoras);
    }
}
