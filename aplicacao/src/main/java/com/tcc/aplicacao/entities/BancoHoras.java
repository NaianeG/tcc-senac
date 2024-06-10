package com.tcc.aplicacao.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BancoHoras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private long saldoMensal;
    private long saldoAtual;
    private Boolean saldoNegativo;

    @ManyToOne(optional = true)
    private Usuario usuario;
}

// -- Criação da função para atualizar saldo negativo ou positivo

// CREATE OR REPLACE FUNCTION atualiza_saldo_negativo()
// RETURNS TRIGGER AS $$
// BEGIN
// IF NEW.saldo_atual < NEW.saldo_mensal THEN
// NEW.saldo_negativo := TRUE;
// ELSE
// NEW.saldo_negativo := FALSE;
// END IF;
// RETURN NEW;
// END;
// $$ LANGUAGE plpgsql;

// -- Criação do trigger
// CREATE TRIGGER trigger_atualiza_saldo_negativo
// BEFORE INSERT OR UPDATE ON public.banco_horas
// FOR EACH ROW
// EXECUTE FUNCTION atualiza_saldo_negativo();