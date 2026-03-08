package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(
        @NotBlank(message = "O nome não deve estar em branco")
        String nome,

        @NotBlank(message = "O email não deve estar em branco")
        @Email
        String email,

        @Pattern(regexp = "\\d{4,6}", message = "O crm deve ser preenchido corretamente")
        @NotBlank(message = "O crm não deve estar em branco")
        String crm,

        @NotBlank(message = "O telefone deve ser preenchido corretamente, e não deve estar em branco")
        String telefone,

        @NotNull(message = "A especialidade não pode estar em branco")
        Especialidade especialidade,

        @NotNull(message = "O endereço deve ser preenchido")
        @Valid
        DadosEndereco endereco) {
}
