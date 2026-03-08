package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ValidadorAgendamentoConsultaPacienteAtivo implements IValidadorAgendamentoConsulta {
    private PacienteRepository repository;

    public ValidadorAgendamentoConsultaPacienteAtivo(PacienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validar(DadosAgendamentoConsulta agendamentoConsulta){
        var pacienteAtivo = repository.findAtivoById(agendamentoConsulta.idPaciente());

        if(!pacienteAtivo) {
            throw new ValidacaoException("O paciente deve estar ativo para agendar uma consulta");
        }
    }
}
