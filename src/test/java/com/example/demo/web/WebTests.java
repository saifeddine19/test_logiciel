package com.example.demo.web;

import com.example.demo.data.Voiture;
import com.example.demo.service.Echantillon;
import com.example.demo.service.StatistiqueImpl;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WebTests {
    

    @MockBean
    StatistiqueImpl statistiqueImpl;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void TestGetStatistiques() throws Exception {
        doNothing().when(statistiqueImpl).ajouter(new Voiture("Mercedes", "20000"));
        doNothing().when(statistiqueImpl).ajouter(new Voiture("Ferrari", "5000"));
        
        when(statistiqueImpl.prixMoyen()).thenReturn(new Echantillon(2, 25000));
        
        mockMvc.perform(get("/statistique")) 
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.nombreDeVoitures").value("2"))
               .andExpect(jsonPath("$.prixMoyen").value("25000")) /
               .andReturn();
    }

    @Test
    public void TestGetStatistiquesErreur() throws Exception {
        when(statistiqueImpl.prixMoyen()).thenThrow(new ArithmeticException());
        
        mockMvc.perform(get("/statistique")) 
               .andDo(print())
               .andExpect(status().isInternalServerError())
               .andReturn();
    }

    @Test
    public void TestPostVoiture() throws Exception { 

        doNothing().when(statistiqueImpl).ajouter(new Voiture("f", "100")); 

        String jsonContent = """
        {"marque":"f","prix":100}
        """;

        
        mockMvc.perform(post("/voiture")
               .contentType(MediaType.APPLICATION_JSON)
               .content(jsonContent))
               .andDo(print())
               .andExpect(status().isOk())
               .andReturn();
    }

    @Test
    public void TestPostVoitureErreur() throws Exception {
        
        doThrow(new RuntimeException("Base de données en panne"))
            .when(statistiqueImpl).ajouter(any(Voiture.class)); 

        String jsonContent = """
        {"marque":"f","prix":100}
        """;
        
        mockMvc.perform(post("/voiture")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andReturn();
    }
}