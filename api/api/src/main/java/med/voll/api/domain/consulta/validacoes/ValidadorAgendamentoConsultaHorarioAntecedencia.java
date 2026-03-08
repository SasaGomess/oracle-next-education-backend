package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class ValidadorAgendamentoConsultaHorarioAntecedencia implements IValidadorAgendamentoConsulta {

    @Override
    public void validar(DadosAgendamentoConsulta agendamentoConsulta){
        var dataConsulta = agendamentoConsulta.data();
        var agora = LocalDateTime.now();

        var diferencaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();

        if(diferencaEmMinutos < 30) {
            throw new ValidacaoException("O agendamento da consulta deve ter antecedência de 30 minutos");
        }
    }
}
