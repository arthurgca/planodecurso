package models;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

import play.libs.*;

import com.fasterxml.jackson.databind.JsonNode;

public class PlanoJsonTest {

    JsonNode plano;

    @Before
    public void setUp() {
        Disciplina d0 = new Disciplina("Disciplina 0", 4, "MyString");
        Disciplina d1 = new Disciplina("Disciplina 1", 4, "MyString");
        Disciplina d2 = new Disciplina(
          "Disciplina 2", 4, "MyString", new Disciplina[] {d0});

        Curriculo c1 = new Curriculo.Builder("Curriculo 1")
            .maxPeriodos(2)
            .minCreditosPeriodo(4)
            .maxCreditosPeriodo(8)
            .disciplina(d0)
            .disciplina(d1)
            .disciplina(d2)
            .build();

        Grade g1 = new Grade("Grade 1", 2);
        g1.programar(d1, 1);
        g1.programar(d2, 2);

        plano = new PlanoJson().toJson(new Plano(c1, g1));
    }

    @Test
    public void id() {
        assertNotNull(plano.get("id"));
    }

    @Test
    public void periodos() {
        assertTrue(plano.get("periodos").isArray());
        assertEquals(2, plano.get("periodos").size());

        JsonNode periodo = plano.get("periodos").elements().next();

        assertNotNull(periodo.get("id"));
        assertEquals(1, periodo.get("semestre").numberValue());
        assertEquals("1º Período", periodo.get("nome").textValue());
        assertEquals(4, periodo.get("totalCreditos").numberValue());
    }

    @Test
    public void disciplinas() {
        JsonNode periodo = plano.get("periodos").elements().next();

        assertTrue(periodo.get("disciplinas").isArray());
        assertEquals(1, periodo.get("disciplinas").size());

        JsonNode disciplina = periodo.get("disciplinas").elements().next();

        assertNotNull(disciplina.get("id"));
        assertEquals("Disciplina 1", disciplina.get("nome").textValue());
        assertEquals(4, disciplina.get("creditos").numberValue());
        assertEquals("MyString", disciplina.get("categoria").textValue());
    }

    @Test
    public void requisitos() {
        Iterator<JsonNode> periodos = plano.get("periodos").elements();

        periodos.next();

        JsonNode disciplina = periodos.next().get("disciplinas")
            .elements().next();

        assertTrue(disciplina.get("requisitos").isArray());
        assertEquals(1, disciplina.get("requisitos").size());

        Iterator<JsonNode> requisitos = disciplina.get("requisitos").elements();

        JsonNode requisito = requisitos.next();

        assertFalse(requisito.get("isSatisfeito").booleanValue());
        assertEquals("Disciplina 0", requisito.get("nome").textValue());
        assertEquals(4, requisito.get("creditos").numberValue());
        assertEquals("MyString", requisito.get("categoria").textValue());
        assertNull(requisito.get("requisitos"));
    }

}
