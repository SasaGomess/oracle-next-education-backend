package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class ValidadorAgendamentoConsultaMedicoAtivo implements IValidadorAgendamentoConsulta {
    private MedicoRepository repository;

    public ValidadorAgendamentoConsultaMedicoAtivo(MedicoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validar(DadosAgendamentoConsulta agendamentoConsulta){
        //Medico opcional
        if (agendamentoConsulta == null) return;

        Boolean medicoAtivo = repository.findAtivoById(agendamentoConsulta.idMedico());

        if(!medicoAtivo) {
            throw new ValidacaoException("O medico deve estar ativo para uma consulta ser agendada");
        }
    }
}
