package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.paciente.PacienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ValidadorAgendamentoConsultaPacienteAtivoTest {

    @InjectMocks
    private ValidadorAgendamentoConsultaPacienteAtivo validadorPacienteAtivo;

    @Mock
    private PacienteRepository repository;

    @Test
    @DisplayName("Should throw Validacao exception when the patient isn't active")
    void validar_paciente_ativo1() {
        DadosAgendamentoConsulta agendamentoConsulta = new DadosAgendamentoConsulta(2L, 3L, LocalDateTime.now().plusMinutes(20), Especialidade.CARDIOLOGIA);

        var validacaoException = assertThrows(ValidacaoException.class,() -> validadorPacienteAtivo.validar(agendamentoConsulta));
        assertThat(validacaoException.getMessage()).isEqualTo("O paciente deve estar ativo para agendar uma consulta");
    }

    @Test
    @DisplayName("Shouldn't throw Validacao exception when the patient is active")
    void validar_paciente_ativo2(){
        DadosAgendamentoConsulta agendamentoConsulta = new DadosAgendamentoConsulta(2L, 3L, LocalDateTime.now().plusMinutes(20), Especialidade.CARDIOLOGIA);
        when(repository.findAtivoById(any())).thenReturn(true);
        assertDoesNotThrow(() -> validadorPacienteAtivo.validar(agendamentoConsulta));
    }

}