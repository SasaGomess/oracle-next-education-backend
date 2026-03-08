package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.IValidadorAgendamentoConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final List<IValidadorAgendamentoConsulta> validadores;

    public AgendaDeConsultas(ConsultaRepository consultaRepository, MedicoRepository medicoRepository, PacienteRepository pacienteRepository, List<IValidadorAgendamentoConsulta> validadores) {
        this.consultaRepository = consultaRepository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.validadores = validadores;
    }

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dadosAgendamento){
        if(!pacienteRepository.existsById(dadosAgendamento.idPaciente())) throw new ValidacaoException("Id do paciente inválido");

        if(dadosAgendamento.idMedico() != null && !medicoRepository.existsById(dadosAgendamento.idMedico())) throw new ValidacaoException("Id do médico inválido");

        var paciente = pacienteRepository.getReferenceById(dadosAgendamento.idPaciente());
        var medico = escolherMedico(dadosAgendamento);

        if(medico == null) throw new ValidacaoException("Não possuem médicos livres nessa data");
        validadores.forEach(validador -> validador.validar(dadosAgendamento));

        var consulta = new Consulta(medico, paciente, dadosAgendamento.data());
        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta.getId(), medico.getId(), paciente.getId(), consulta.getData());
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dadosAgendamento) {
        if(dadosAgendamento.idMedico() == null) {
            if(dadosAgendamento.especialidade() == null) throw new ValidacaoException("Especialidade é obrigatória, quando médico não é escolhido");
            return medicoRepository.escolheMedicoAleatorioLivreNaData(dadosAgendamento.especialidade(), dadosAgendamento.data());
        } else {
            return medicoRepository.getReferenceById(dadosAgendamento.idMedico());
        }
    }
}
