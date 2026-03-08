package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
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
class ValidadorAgendamentoConsultaMedicoComOutraConsultaTest {

    @InjectMocks
    private ValidadorAgendamentoConsultaMedicoComOutraConsulta validadorMedicoNaoDisponivel;

    @Mock
    private ConsultaRepository repository;

    @Test
    @DisplayName("Should throw Validacao exception when the doctor has a other appointment at the same date")
    void validar_medico_indisponivel1() {
        var dadosAgendamentoConsulta = new DadosAgendamentoConsulta(2L, 3L, LocalDateTime.now().plusMinutes(20), Especialidade.CARDIOLOGIA);

        when(repository.existsByMedicoIdAndData(any(), any())).thenReturn(true);

        var validacaoException = assertThrows(ValidacaoException.class, () -> validadorMedicoNaoDisponivel.validar(dadosAgendamentoConsulta));
        assertThat(validacaoException.getMessage()).isEqualTo("O médico já possui uma consulta agendada neste horário");
    }

    @Test
    @DisplayName("Shouldn't throw Validacao exception when the doctor is available on the date")
    void validar_medico_indisponivel2() {
        var dadosAgendamentoConsulta = new DadosAgendamentoConsulta(2L, 3L, LocalDateTime.now().plusMinutes(20), Especialidade.CARDIOLOGIA);

        when(repository.existsByMedicoIdAndData(any(), any())).thenReturn(false);

       assertDoesNotThrow( () -> validadorMedicoNaoDisponivel.validar(dadosAgendamentoConsulta));
    }
}