package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.endereco.Endereco;

@Table(name = "pacientes")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;

    @Embedded
    private Endereco endereco;

    private Boolean ativo;

    public Paciente(DadosCadastroPaciente cadastroPaciente) {
        this.nome = cadastroPaciente.nome();
        this.email = cadastroPaciente.email();
        this.telefone = cadastroPaciente.telefone();
        this.cpf = cadastroPaciente.cpf();
        this.ativo = true;
        this.endereco = new Endereco(cadastroPaciente.endereco());
    }

    public void atualizarPaciente(DadosAtualizacaoPaciente dadosAtualizacao){
        if (dadosAtualizacao.nome() != null) {
            this.nome = dadosAtualizacao.nome();
        }
        if (dadosAtualizacao.telefone() != null) {
            this.telefone = dadosAtualizacao.telefone();
        }
        if (dadosAtualizacao.endereco() != null) {
            this.endereco.atualizarInformacoes(dadosAtualizacao.endereco());
        }
    }

    public void excluir(){
        this.ativo = false;
    }

}
