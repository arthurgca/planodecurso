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
        Disciplina d3 = new Disciplina("Disciplina 3", 4, "MyString");

        Curriculo c1 = new Curriculo.Builder("Curriculo 1")
            .maxPeriodos(3)
            .minCreditosPeriodo(4)
            .maxCreditosPeriodo(8)
            .disciplina(d0)
            .disciplina(d1)
            .disciplina(d2)
            .disciplina(d3)
            .build();

        Grade g1 = new Grade("Grade 1", c1);
        g1.programar(d1, 1);
        g1.programar(d2, 2);
        g1.programar(d3, 3);

        Plano p1 = new Plano(c1, g1);
        p1.setPeriodoAtual(2);

        plano = new PlanoJson().toJson(p1);
    }

    @Test
    public void id() {
        assertNotNull(plano.get("id"));
    }

    @Test
    public void periodoAtual() {
        assertEquals(2, plano.get("periodoAtual").numberValue());
    }

    @Test
    public void periodos() {
        assertTrue(plano.get("periodos").isArray());
        assertEquals(3, plano.get("periodos").size());

        Iterator<JsonNode> periodos = plano.get("periodos").elements();

        JsonNode periodo = periodos.next();

        assertNotNull(periodo.get("id"));
        assertEquals(1, periodo.get("semestre").numberValue());
        assertEquals("1º Período", periodo.get("nome").textValue());
        assertEquals(4, periodo.get("totalCreditos").numberValue());

        assertTrue(periodo.get("isPassado").booleanValue());
        assertFalse(periodo.get("isAtual").booleanValue());
        assertFalse(periodo.get("isFuturo").booleanValue());

        periodo = periodos.next();

        assertFalse(periodo.get("isPassado").booleanValue());
        assertTrue(periodo.get("isAtual").booleanValue());
        assertFalse(periodo.get("isFuturo").booleanValue());

        periodo = periodos.next();

        assertFalse(periodo.get("isPassado").booleanValue());
        assertFalse(periodo.get("isAtual").booleanValue());
        assertTrue(periodo.get("isFuturo").booleanValue());
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
    public void ofertadas() {
        JsonNode periodo = plano.get("periodos").elements().next();

        assertTrue(periodo.get("ofertadas").isArray());
        assertEquals(1, periodo.get("ofertadas").size());

        JsonNode ofertada = periodo.get("ofertadas").elements().next();

        assertNotNull(ofertada.get("id"));
        assertEquals("Disciplina 0", ofertada.get("nome").textValue());
        assertEquals(4, ofertada.get("creditos").numberValue());
        assertEquals("MyString", ofertada.get("categoria").textValue());
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
