package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class ValidadorAgendamentoConsultaHorarioAntecedenciaTest {

    @Autowired
    ValidadorAgendamentoConsultaHorarioAntecedencia validador;

    @Test
    @DisplayName("Should throw a Validacao exception when the scheduling isn't 30 minutes advanced then the appointment")
    void validar_horario_consulta1() {
        DadosAgendamentoConsulta agendamentoConsulta = new DadosAgendamentoConsulta(2L, 3L, LocalDateTime.now().plusMinutes(20), Especialidade.CARDIOLOGIA);

        ValidacaoException validacaoException = assertThrows(ValidacaoException.class, () -> validador.validar(agendamentoConsulta));

        assertThat("O agendamento da consulta deve ter antecedência de 30 minutos").isEqualTo(validacaoException.getMessage());
    }

    @Test
    @DisplayName("Shouldn't throw a Validacao exception when the scheduling is 30 minutes advanced then the appointment")
    void validar_horario_consulta2() {
        DadosAgendamentoConsulta agendamentoConsulta = new DadosAgendamentoConsulta(2L, 3L, LocalDateTime.now().plusMinutes(30), Especialidade.CARDIOLOGIA);

        assertDoesNotThrow(() -> validador.validar(agendamentoConsulta));
    }

}