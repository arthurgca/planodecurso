package models;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

import play.libs.*;

import com.fasterxml.jackson.databind.JsonNode;

public class PlanoJsonTest {

    JsonNode plano;

    @Before
    public void setUp() throws Exception {
        Disciplina d0 = new Disciplina("Disciplina 0", 4);
        Disciplina d1 = new Disciplina("Disciplina 1", 4);
        Disciplina d2 = new Disciplina("Disciplina 2", 4);
        d2.setRequisitos(new HashSet<Disciplina>(Arrays.asList(new Disciplina[]{
                        d0
                    })));
        Disciplina d3 = new Disciplina("Disciplina 3", 4);

        Curriculo c1 = new Curriculo(new HashSet<Disciplina>(Arrays.asList(new Disciplina[]{
                        d0, d1, d2, d3
                    })));
        c1.setMaxPeriodos(4);
        c1.setMinCreditosPeriodo(4);
        c1.setMaxPeriodos(8);

        Grade g1 = new Grade(4);
        g1.programar(d1, g1.getPeriodo(1));
        g1.programar(d2, g1.getPeriodo(2));
        g1.programar(d3, g1.getPeriodo(3));

        Plano p1 = new Plano(c1, g1);
        p1.setPeriodoAtual(g1.getPeriodo(2));

        plano = new PlanoJson().toJson(p1);
    }

    @Test
    public void id() {
        assertNotNull(plano.get("id"));
    }

    @Test
    public void periodoAtual() {
        assertEquals(2, plano.get("periodoAtual").get("semestre").numberValue());
    }

    @Test
    public void periodos() {
        assertTrue(plano.get("periodos").isArray());
        assertEquals(4, plano.get("periodos").size());

        Iterator<JsonNode> periodos = plano.get("periodos").elements();

        JsonNode periodo = periodos.next();

        assertNotNull(periodo.get("id"));
        assertEquals(1, periodo.get("semestre").numberValue());
        assertEquals("1º Período", periodo.get("nome").textValue());
        assertEquals(4, periodo.get("totalCreditos").numberValue());

        assertTrue(periodo.get("isPassado").booleanValue());
        assertFalse(periodo.get("isAtual").booleanValue());
        assertFalse(periodo.get("isFuturo").booleanValue());

        assertNull(periodo.get("erroPoliticaDeCreditos").textValue());

        periodo = periodos.next();

        assertFalse(periodo.get("isPassado").booleanValue());
        assertTrue(periodo.get("isAtual").booleanValue());
        assertFalse(periodo.get("isFuturo").booleanValue());

        assertNull(periodo.get("erroPoliticaDeCreditos").textValue());

        periodo = periodos.next();

        assertFalse(periodo.get("isPassado").booleanValue());
        assertFalse(periodo.get("isAtual").booleanValue());
        assertTrue(periodo.get("isFuturo").booleanValue());

        assertNull(periodo.get("erroPoliticaDeCreditos").textValue());

        periodo = periodos.next();

        assertNotNull(periodo.get("erroPoliticaDeCreditos").textValue());
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

        assertNull(requisito.get("requisitos"));
    }

}
