package med.voll.api.medico;

import jakarta.persistence.Embeddable;
import lombok.*;
import med.voll.api.endereco.DadosEndereco;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Endereco {
    private String logradouro;
    private String bairro;
    private String cep;
    private String numero;
    private String complemento;
    private String cidade;
    private String uf;


    public Endereco(DadosEndereco endereco) {
        this.bairro = endereco.bairro();
        this.cep = endereco.cep();
        this.logradouro = endereco.lougradouro();
        this.cidade = endereco.cidade();
        this.numero = endereco.numero();
        this.uf = endereco.uf();
        this.complemento = endereco.complemento();
    }
}
