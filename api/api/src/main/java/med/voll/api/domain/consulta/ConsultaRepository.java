package med.voll.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    @Query("select c from Consulta c Where c.id = :id AND TIMESTAMPDIFF(HOUR, :data, c.data) >= 24")
    Consulta cancelarConsultaSeAntesDe24h(LocalDateTime data, Long id);

    boolean existsByMedicoIdAndData(Long id, LocalDateTime data);

    boolean existsByPacienteIdAndDataBetween(Long pacienteId, LocalDateTime horarioAbertura, LocalDateTime horarioFechamento);
}
