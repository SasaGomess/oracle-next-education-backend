package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacaoException;
import org.springframework.stereotype.Service;

@Service
public class CancelamentoDeConsultas {
    private ConsultaRepository consultaRepository;

    public CancelamentoDeConsultas(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public void cancelar(DadosCancelaConsulta dados) {
        if(!consultaRepository.existsById(dados.id())) throw new ValidacaoException("Consulta inválida ou não existente");

        Consulta consulta = consultaRepository.cancelarConsultaSeAntesDe24h(dados.data(), dados.id());

        consulta.cancelar();
    }
}
