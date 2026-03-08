package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Service;

@Service
public class ValidadorAgendamentoConsultaMedicoComOutraConsulta implements IValidadorAgendamentoConsulta {
    private ConsultaRepository repository;

    public ValidadorAgendamentoConsultaMedicoComOutraConsulta(ConsultaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validar(DadosAgendamentoConsulta agendamentoConsulta){
        var medicoPossuiOutraConsultaNoMesmoHorario = repository.existsByMedicoIdAndData(agendamentoConsulta.idMedico(), agendamentoConsulta.data());
        if (medicoPossuiOutraConsultaNoMesmoHorario){
            throw new ValidacaoException("O médico já possui uma consulta agendada neste horário");
        }
    }
}
