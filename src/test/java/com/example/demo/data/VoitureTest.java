package com.example.demo.data;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class VoitureTest {

    @Test
    void creerVoiture(){
        assertEquals(1,1);
    }

    @Test
    void testConstructeur(){
        Voiture v = new Voiture("Audi", 45000);

        assertEquals("Audi", v.getMarque(), "La marque doit être Audi");

        assertEquals(45000, v.getPrix(), "Le prix doit être 45000");
    }

    @Test
    void testSetters() {
        Voiture v = new Voiture();
        v.setMarque("Toyota");
        v.setPrix(15000);
        v.setId(5);

        assertEquals("Toyota", v.getMarque());
        assertEquals(15000, v.getPrix());
        assertEquals(5, v.getId());
    }

    
    @Test
    void testToString() {
        Voiture v = new Voiture("Tesla", 60000);
        v.setId(1);
        String attendu = "Car{marque='Tesla', prix=60000, id=1}";
        assertEquals(attendu, v.toString());
    }


}
