package med.voll.api.domain.consulta;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosCancelaConsulta(
        @NotNull
        Long id,
        @NotBlank
        @JsonAlias({"motivo_cancelamento", "motivoCancelamento"})
        String motivoCancelamento,

        LocalDateTime data
) {
}
