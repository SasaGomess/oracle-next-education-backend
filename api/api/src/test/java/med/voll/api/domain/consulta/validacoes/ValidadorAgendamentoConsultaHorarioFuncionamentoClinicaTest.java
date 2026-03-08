package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ValidadorAgendamentoConsultaHorarioFuncionamentoClinicaTest {

    @Autowired
    ValidadorAgendamentoConsultaHorarioFuncionamentoClinica validador;

    @Test
    @DisplayName("Should throw Validação exception when the appointment is scheduled after of business hours")
    void validar_agendamento_funcionamento_clinica1() {
        DadosAgendamentoConsulta agendamentoConsulta = new DadosAgendamentoConsulta(1L, 2L, LocalDateTime.of(2026, Month.MARCH, 2, 19, 0), Especialidade.GINECOLOGIA);

        var validacaoException = assertThrows(ValidacaoException.class, () -> validador.validar(agendamentoConsulta));

        assertThat(validacaoException.getMessage()).isEqualTo("Consulta fora do horário de funcionamento da clinica");
    }

    @Test
    @DisplayName("Should throw Validação exception when the appointment is scheduled before of business hours")
    void validar_agendamento_funcionamento_clinica2() {
        DadosAgendamentoConsulta agendamentoConsulta = new DadosAgendamentoConsulta(1L, 2L, LocalDateTime.of(2026, Month.MARCH, 2, 6, 30), Especialidade.GINECOLOGIA);

        var validacaoException = assertThrows(ValidacaoException.class, () -> validador.validar(agendamentoConsulta));

        assertThat(validacaoException.getMessage()).isEqualTo("Consulta fora do horário de funcionamento da clinica");
    }


    @Test
    @DisplayName("Should throw Validação exception when the appointment is scheduled on Sunday")
    void validar_agendamento_funcionamento_clinica3() {
        DadosAgendamentoConsulta agendamentoConsulta = new DadosAgendamentoConsulta(1L, 2L, LocalDateTime.of(2026, Month.MARCH, 8, 10, 30), Especialidade.GINECOLOGIA);

        var validacaoException = assertThrows(ValidacaoException.class, () -> validador.validar(agendamentoConsulta));

        assertThat(validacaoException.getMessage()).isEqualTo("Consulta fora do horário de funcionamento da clinica");
    }

    @Test
    @DisplayName("Shouldn't throw Validação exception when the appointment date falls within business hours")
    void validar_agendamento_funcionamento_clinica4() {
        DadosAgendamentoConsulta agendamentoConsulta = new DadosAgendamentoConsulta(1L, 2L, LocalDateTime.of(2026, Month.MARCH, 10, 10, 30), Especialidade.GINECOLOGIA);

       assertDoesNotThrow(() ->validador.validar(agendamentoConsulta));
    }
}