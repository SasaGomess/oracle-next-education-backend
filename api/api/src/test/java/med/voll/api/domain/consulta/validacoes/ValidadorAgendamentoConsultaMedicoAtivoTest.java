package med.voll.api.domain.consulta.validacoes;

import lombok.extern.log4j.Log4j2;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.Especialidade;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class) //Habilita uso de mocks
class ValidadorAgendamentoConsultaMedicoAtivoTest {

    @InjectMocks // Diz em qual mock injetar o repositorio, classe que será testada com mock
    private ValidadorAgendamentoConsultaMedicoAtivo medicoAtivo;

    @Mock // Simulando banco de dados
    private MedicoRepository repository;

    @Test
    @DisplayName("Should throw Validacao exception when the doctor isn't active")
    void validar_medico_ativo1() {
        DadosAgendamentoConsulta agendamentoConsulta = new DadosAgendamentoConsulta(2L, 3L, LocalDateTime.now().plusMinutes(20), Especialidade.CARDIOLOGIA);

        var validacaoException = assertThrows(ValidacaoException.class,() -> medicoAtivo.validar(agendamentoConsulta));
        assertThat(validacaoException.getMessage()).isEqualTo("O medico deve estar ativo para uma consulta ser agendada");
    }

    @Test
    @DisplayName("Shouldn't throw Validacao exception when the doctor is active")
    void validar_medico_ativo2(){
        DadosAgendamentoConsulta agendamentoConsulta = new DadosAgendamentoConsulta(2L, 3L, LocalDateTime.now().plusMinutes(20), Especialidade.CARDIOLOGIA);
        when(repository.findAtivoById(agendamentoConsulta.idMedico())).thenReturn(true);
        assertDoesNotThrow(() -> medicoAtivo.validar(agendamentoConsulta));
    }

    @Test
    @DisplayName("Shouldn't throw Validacao exception when DadosAgendamentoConsulta is null")
    void validar_medico_ativo3(){
        assertDoesNotThrow(() -> medicoAtivo.validar(null));
    }

}