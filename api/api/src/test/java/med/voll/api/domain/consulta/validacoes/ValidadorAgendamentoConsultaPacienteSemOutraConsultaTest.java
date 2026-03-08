package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ValidadorAgendamentoConsultaPacienteSemOutraConsultaTest {

    @InjectMocks
    private ValidadorAgendamentoConsultaPacienteSemOutraConsulta validadorPacienteSemOutraConsulta;

    @Mock
    private ConsultaRepository repository;

    @Test
    @DisplayName("Should throw Validacao exception when patient already has an appointment on the date")
    void validar_paciente_com_consulta() {
        var agendamentoConsulta = new DadosAgendamentoConsulta(2L, 3L, LocalDateTime.now().plusMinutes(20), Especialidade.CARDIOLOGIA);

        when(repository.existsByPacienteIdAndDataBetween(any(), any(), any())).thenReturn(true);

        var validacaoException = assertThrows(ValidacaoException.class,() -> validadorPacienteSemOutraConsulta.validar(agendamentoConsulta));
        assertThat(validacaoException.getMessage()).isEqualTo("Paciente já possui consulta marcada neste dia");
    }

    @Test
    @DisplayName("Shouldn't throw Validacao exception when the patient doesn't an have appointment on the date")
    void validar_paciente_sem_consulta() {
        var agendamentoConsulta = new DadosAgendamentoConsulta(2L, 3L, LocalDateTime.now().plusMinutes(20), Especialidade.CARDIOLOGIA);

        when(repository.existsByPacienteIdAndDataBetween(any(), any(), any())).thenReturn(false);

        assertDoesNotThrow(() -> validadorPacienteSemOutraConsulta.validar(agendamentoConsulta));
    }
}