package med.voll.api.controllers;

import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.endereco.Endereco;
import med.voll.api.domain.medico.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    private JacksonTester<DadosCadastroMedico> dadosCadastroMedicoJson;

    @Autowired
    private JacksonTester<DetalhamentoMedico> dadosDetalhamentoMedicoJson;

    @MockitoBean
    private MedicoRepository repository;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Should return http code 400 when request body isn't sent")
    @WithMockUser
    void cadastrar_cenario1() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/medicos"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @DisplayName("Should return http code 400 when request body is not valid")
    @WithMockUser
    void cadastrar_cenario2() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                               dadosCadastroMedicoJson.write(
                                       new DadosCadastroMedico(null, null, "435", "119483344", Especialidade.CARDIOLOGIA, new DadosEndereco("", "", "", "", "", "", ""))
                               ).getJson()
                        ))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Should return http code 201 when request body is valid")
    @WithMockUser
    void cadastrar_cenario3() throws Exception {
        var cadastroMedico = new DadosCadastroMedico("Julia", "julia@gmail.com", "435546", "1194833445", Especialidade.CARDIOLOGIA, new DadosEndereco("rua das figueiras", "vila das amoras", "03454534", "São Paulo", "SP", "casa", "17"));
        var medicoSimulado = new Medico(1L, "Julia", "julia@gmail.com", "435546", "1194833445", Especialidade.CARDIOLOGIA, true, new Endereco("rua das figueiras", "vila das amoras", "03454534", "17", "casa", "São Paulo", "SP"));

        when(repository.save(any())).thenReturn(medicoSimulado);

        MockHttpServletResponse response = mockMvc.perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                dadosCadastroMedicoJson.write(cadastroMedico).getJson()
                        ))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var expectedJson = dadosDetalhamentoMedicoJson.write(
                new DetalhamentoMedico(medicoSimulado)
        ).getJson();

        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    @DisplayName("Should return the correct uri")
    @WithMockUser
    void cadastrar_cenario4() throws Exception {
        var cadastroMedico = new DadosCadastroMedico("Julia", "julia@gmail.com", "435546", "1194833445", Especialidade.CARDIOLOGIA, new DadosEndereco("rua das figueiras", "vila das amoras", "03454534", "São Paulo", "SP", "casa", "17"));
        var medicoSimulado = new Medico(1L, "Julia", "julia@gmail.com", "435546", "1194833445", Especialidade.CARDIOLOGIA, true, new Endereco("rua das figueiras", "vila das amoras", "03454534", "17", "casa", "São Paulo", "SP"));

        when(repository.save(any())).thenReturn(medicoSimulado);

        // AQUI é enviando a requisição não possui uri formada
        MockHttpServletResponse response = mockMvc.perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                dadosCadastroMedicoJson.write(cadastroMedico).getJson()
                        ))
                .andReturn()
                .getResponse();

        // depois que eu pego a resposta
        String uri = response.getHeader("Location");

        assertThat(uri).isEqualTo("http://localhost/medicos/1");
    }

}