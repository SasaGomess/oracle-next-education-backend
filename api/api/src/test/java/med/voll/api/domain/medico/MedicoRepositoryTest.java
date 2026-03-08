package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.paciente.DadosCadastroPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository repository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    @DisplayName("Should return null when the doctor registered isn't available on the date")
    void escolheMedicoAleatorioLivreNaDataCenario1() {
        //given ou arrange: cadastro informações
        var proximaSegundaAs10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);


        var paciente = cadastrarPaciente("Julia Almeida", "julia@gmail.com", "043834283-91");
        var medico = cadastrarMedico("Marcia Julia", "marcia@voll.med", "345678", Especialidade.CARDIOLOGIA);

        cadastrarConsulta(medico, paciente, proximaSegundaAs10);

        //when ou act: Ação a ser executada
        var medicoLivre = repository.escolheMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        // then ou assert: E o resultado esperado
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Should return Medico when the doctor is available on the date")
    void escolheMedicoAleatorioLivreNaDataCenario2() {
        var proximaSegundaAs10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);

        var medico = cadastrarMedico("Marcia Julia", "marcia@voll.med", "345678", Especialidade.CARDIOLOGIA);

        var medicoLivre = repository.escolheMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        Assertions.assertEquals(medicoLivre, medico);
    }

    @Test
    @DisplayName("Should return null when the doctor is available on the date but isn't active")
    void escolheMedicoAleatorioLivreNaDataCenario3() {
        var proximaSegundaAs10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);

        var medico = cadastrarMedico("Marcia Julia", "marcia@voll.med", "345678", Especialidade.CARDIOLOGIA);

        medico.excluir();

        var medicoLivreMasInativo = repository.escolheMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        Assertions.assertNull(medicoLivreMasInativo);
    }

    @Test
    @DisplayName("Should return null when no doctors is available on specialty")
    void escolheMedicoAleatorioLivreNaDataCenario4() {
        var proximaSegundaAs10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);

        var nenhumMedicoNaEspecialidade = repository.escolheMedicoAleatorioLivreNaData(Especialidade.CARDIOLOGIA, proximaSegundaAs10);

        Assertions.assertNull(nenhumMedicoNaEspecialidade);
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime proximaSegundaAs10) {
        testEntityManager.persist(new Consulta(medico, paciente, proximaSegundaAs10));
    }

    private Medico cadastrarMedico(String nome, String email, String crm, Especialidade especialidade){
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        Medico medicoCadastrado = testEntityManager.persist(medico);
        return medicoCadastrado;
    }

    private Paciente cadastrarPaciente(String nome, String email, String cpf){
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        Paciente pacienteCadastrado = testEntityManager.persist(paciente);
        return pacienteCadastrado;
    }

    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
        return new DadosCadastroPaciente(
                nome,
                email,
                "1188888888",
                cpf,
                dadosEndereco()
        );
    }

    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, Especialidade especialidade) {
        return new DadosCadastroMedico(
                nome,
                email,
                crm,
                "1189438543",
                especialidade,
                dadosEndereco()
        );
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua x",
                "bairro",
                "00000000",
                "Cidade",
                "SP",
                null,
                null
        );
    }
}