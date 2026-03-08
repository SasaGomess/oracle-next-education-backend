package med.voll.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.stream.Stream;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    private PacienteRepository repository;

    public PacienteController(PacienteRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroPaciente cadastroPaciente, UriComponentsBuilder uriComponentsBuilder){
        Paciente paciente = new Paciente(cadastroPaciente);
        Paciente pacienteSalvo = repository.save(paciente);

        URI uri = uriComponentsBuilder
                .buildAndExpand(pacienteSalvo)
                .expand("pacientes/{id}")
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    @Transactional
    public ResponseEntity<Page<DadosListagemPaciente>> listar(@PageableDefault(size = 10, sort = "nome") Pageable pageable){
        Page<DadosListagemPaciente> listagemPaciente= repository.findByAtivoTrue(pageable).map(DadosListagemPaciente::new);
        return ResponseEntity.ok(listagemPaciente);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoPaciente dadosAtualizacaoPaciente){
        Paciente pacienteEncontrado = repository.getReferenceById(dadosAtualizacaoPaciente.id());

        if(pacienteEncontrado != null) {
            pacienteEncontrado.atualizarPaciente(dadosAtualizacaoPaciente);
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id){
        Paciente pacienteEncontrado = repository.getReferenceById(id);
        pacienteEncontrado.excluir();
        return ResponseEntity.noContent().build();
    }


}
