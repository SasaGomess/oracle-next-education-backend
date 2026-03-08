package med.voll.api.controllers;
import med.voll.api.domain.consulta.AgendaDeConsultas;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.consulta.DadosDetalhamentoConsulta;
import med.voll.api.domain.medico.Especialidade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
//Injetando o mockMvc
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJson;

    @Autowired
    private JacksonTester<DadosDetalhamentoConsulta> dadosDetalhamentoConsultaJson;

    // Fazendo um mock de uma dependencia para não ser injetada de verdade
    @MockitoBean
    private AgendaDeConsultas agendaDeConsultas;

    @BeforeEach
    public void  setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Should return http code 400 when invalid information")
    //Para o spring considerar o usuário logado
    @WithMockUser
    void agendar_cenario1() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/consultas"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Should return http code 200 when all information is valid")
    @WithMockUser
    void agendar_cenario2() throws Exception {

        var dadosDetalhamento = new DadosDetalhamentoConsulta(null,1L, 2L, LocalDateTime.now().plusDays(3));

        // Estou dizendo que não importa o parametro que for passado null ou o objeto quero que ele retorne dadosDetalhementoConsulta
        when(agendaDeConsultas.agendar(any()))
                .thenReturn(dadosDetalhamento);

        MockHttpServletResponse response = mockMvc.perform(post("/consultas")
                        .contentType(MediaType.APPLICATION_JSON)
                //JSON CRIADO PELO JACKSON TESTER pela minha classe dto
                        .content(dadosAgendamentoConsultaJson.write(
                                new DadosAgendamentoConsulta(1L, 2L, LocalDateTime.now().plusDays(3), Especialidade.CARDIOLOGIA)
                        ).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = dadosDetalhamentoConsultaJson.write(
                dadosDetalhamento
        ).getJson();


        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

}