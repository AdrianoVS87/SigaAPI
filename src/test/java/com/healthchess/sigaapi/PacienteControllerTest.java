package com.healthchess.sigaapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.healthchess.sigaapi.controller.PacienteController;
import com.healthchess.sigaapi.model.Paciente;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.util.Date;

public class PacienteControllerTest extends SigaApiApplicationTests{
    private MockMvc mockMvc;

    @Autowired
    private PacienteController pacienteController;

    @Before
    public void setUp(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(pacienteController).build();
    }

    @Test
    public void criarUsuarioComDadosCorretos_RetornarStatusCode200() throws Exception {
        Paciente paciente = new Paciente(null,"Adriano Santos",new Date(),"019.115.630-25");

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(paciente);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/paciente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
            )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void criarUsuarioComDadosIncorretos_RetornarStatusCode400() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/paciente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("")
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void criarUsuarioComNomeAcimaDe60Digitos_RetornarStatusCode400() throws Exception {
        Paciente paciente = new Paciente(null,"1234567890123456789012345678901234567890123456789012345678901234567890",new Date(),"019.115.630-25");

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(paciente);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/paciente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void criarUsuarioComNomeNulo_RetornarStatusCode400() throws Exception {
        Paciente paciente = new Paciente(null,null ,new Date(),"");

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(paciente);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/paciente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void criarUsuarioComNomeVazio_RetornarStatusCode400() throws Exception {
        Paciente paciente = new Paciente(null,"" ,new Date(),"");

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(paciente);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/paciente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    public void buscarTodosUsuarios_RetornarStatusCode200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/paciente"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void buscarUsuarioPorId_RetornarStatusCode200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/paciente/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test(expected = NestedServletException.class)
    public void buscarUsuarioPorIdInexistente_RetornarStatusCode500() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/paciente/10"))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError());
    }

    @Test
    public void atualizarUsuarioComDadosCorretos_RetornarStatusCode200() throws Exception {
        Paciente paciente = new Paciente(1,"Joao Santos",new Date(),"01911563025");

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(paciente);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/paciente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void atualizarUsuarioComDadosIncorretos_RetornarStatusCode400() throws Exception {
        Paciente paciente = new Paciente(1,"",new Date(),"");

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(paciente);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/paciente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}