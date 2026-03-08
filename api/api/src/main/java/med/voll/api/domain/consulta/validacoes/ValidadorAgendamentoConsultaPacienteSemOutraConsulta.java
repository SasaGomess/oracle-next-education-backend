package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Service;

@Service
public class ValidadorAgendamentoConsultaPacienteSemOutraConsulta implements IValidadorAgendamentoConsulta {
    private ConsultaRepository repository;

    public ValidadorAgendamentoConsultaPacienteSemOutraConsulta(ConsultaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validar(DadosAgendamentoConsulta agendamentoConsulta){
        var horarioAbertura = agendamentoConsulta.data().withHour(7);
        var horarioFechamento = agendamentoConsulta.data().withHour(18);

        var pacienteComConsulta = repository.existsByPacienteIdAndDataBetween(agendamentoConsulta.idPaciente(), horarioAbertura, horarioFechamento);
        
        if(pacienteComConsulta) {
            throw new ValidacaoException("Paciente já possui consulta marcada neste dia");
        }
    }
}
