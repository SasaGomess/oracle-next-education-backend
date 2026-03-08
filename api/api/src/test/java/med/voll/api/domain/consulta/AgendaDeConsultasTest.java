package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.IValidadorAgendamentoConsulta;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AgendaDeConsultasTest {

    @InjectMocks
    private AgendaDeConsultas agendaDeConsultas;

    @Mock
    private ConsultaRepository consultaRepository;

    @Mock
    private MedicoRepository medicoRepository;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private List<IValidadorAgendamentoConsulta> validadores;

    @Test
    @DisplayName("Should throw a Validacao exception when invalid pacient id")
    void agendar_cenario1() {
        DadosAgendamentoConsulta agendamentoConsulta = new DadosAgendamentoConsulta(-1L, -1L, LocalDateTime.now().plusMinutes(20), Especialidade.CARDIOLOGIA);

        var ex = assertThrows(ValidacaoException.class, () -> agendaDeConsultas.agendar(agendamentoConsulta));
        assertEquals("Id do paciente inválido", ex.getMessage());
    }

    @Test
    @DisplayName("Should throw a Validacao exception when invalid doctor id")
    void agendar_cenario2() {
        DadosAgendamentoConsulta agendamentoConsulta = new DadosAgendamentoConsulta(2L, -1L, LocalDateTime.now().plusMinutes(20), Especialidade.CARDIOLOGIA);

        when(pacienteRepository.existsById(any())).thenReturn(true);

        var ex = assertThrows(ValidacaoException.class, () -> agendaDeConsultas.agendar(agendamentoConsulta));
        assertThat(ex.getMessage()).isEqualTo("Id do médico inválido");
    }
    @Test
    @DisplayName("Should throw a Validacao exception when especialidade is null")
    void agendar_cenario3() {
        DadosAgendamentoConsulta agendamentoConsulta = new DadosAgendamentoConsulta(2L, null, LocalDateTime.now().plusMinutes(20), null);

        when(pacienteRepository.existsById(any())).thenReturn(true);

        var ex = assertThrows(ValidacaoException.class, () -> agendaDeConsultas.agendar(agendamentoConsulta));
        assertThat(ex.getMessage()).isEqualTo("Especialidade é obrigatória, quando médico não é escolhido");
    }

    @Test
    @DisplayName("Should throw a Validacao exception when Medico is null")
    void agendar_cenario4() {
        DadosAgendamentoConsulta agendamentoConsulta = new DadosAgendamentoConsulta(2L, null, LocalDateTime.now().plusMinutes(20), Especialidade.GINECOLOGIA);

        when(pacienteRepository.existsById(any())).thenReturn(true);
        when(medicoRepository.escolheMedicoAleatorioLivreNaData(any(), any())).thenReturn(null);

        var ex = assertThrows(ValidacaoException.class, () -> agendaDeConsultas.agendar(agendamentoConsulta));
        assertThat(ex.getMessage()).isEqualTo("Não possuem médicos livres nessa data");
    }

    @Test
    @DisplayName("Should return a DadosDetalhamentoConsulta when Medico and Pacient informations are correct")
    void agendar_cenario5() {
        DadosAgendamentoConsulta agendamentoConsulta = new DadosAgendamentoConsulta(2L, null, LocalDateTime.now().plusMinutes(20), Especialidade.GINECOLOGIA);

        when(pacienteRepository.existsById(any())).thenReturn(true);
        when(pacienteRepository.getReferenceById(any())).thenReturn(new Paciente(2L, "X", "xxxx@gmail.com", "1199999999", "xxxxxxx", new Endereco(), true));
        when(medicoRepository.escolheMedicoAleatorioLivreNaData(any(), any())).thenReturn(new Medico());

        assertDoesNotThrow(() -> agendaDeConsultas.agendar(agendamentoConsulta));
    }

}