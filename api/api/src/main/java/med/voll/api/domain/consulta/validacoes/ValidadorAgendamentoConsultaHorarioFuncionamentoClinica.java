package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;

@Service
public class ValidadorAgendamentoConsultaHorarioFuncionamentoClinica implements IValidadorAgendamentoConsulta {

    @Override
    public void validar(DadosAgendamentoConsulta agendamentoConsulta){
        var data = agendamentoConsulta.data();

        var domingo = data.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var antesDaAberturaDaClinica = data.getHour() < 7;
        var depoisDoFechamentoDaClinica = data.getHour() > 18;

        if (domingo || antesDaAberturaDaClinica || depoisDoFechamentoDaClinica){
            throw new ValidacaoException("Consulta fora do horário de funcionamento da clinica");
        }
    }
}
