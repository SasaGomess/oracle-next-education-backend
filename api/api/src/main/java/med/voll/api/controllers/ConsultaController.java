package med.voll.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {
    private final AgendaDeConsultas agendaDeConsultas;
    private final CancelamentoDeConsultas cancelamentoDeConsultas;

    public ConsultaController(AgendaDeConsultas agendaDeConsultas, CancelamentoDeConsultas cancelamentoDeConsultas) {
        this.agendaDeConsultas = agendaDeConsultas;
        this.cancelamentoDeConsultas = cancelamentoDeConsultas;
    }

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados){
        var dto = agendaDeConsultas.agendar(dados);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    public ResponseEntity<Void> cancelarConsulta(@RequestBody @Valid DadosCancelaConsulta dados){
        cancelamentoDeConsultas.cancelar(dados);
        return ResponseEntity.noContent().build();
    }

}
