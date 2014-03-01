package models;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

import play.libs.*;
import com.fasterxml.jackson.databind.JsonNode;

public class PeriodoTest {

    private Periodo periodo;
    private Disciplina disciplina;

    @Before
    public void setUp() {
        periodo = new Periodo(2);
        disciplina = new Disciplina(1, "Teste", 4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void naoDevePermitirSemestreZero() {
        new Periodo(0);
    }

    @Test
    public void deveAlocarDisciplina() {
        periodo.alocar(disciplina);
        assertTrue(periodo.disciplinas.contains( new Disciplina(1, "Teste", 4)));
    }

    @Test
    public void deveDesalocarDisciplina() {
        periodo.alocar(disciplina);
        assertTrue(periodo.disciplinas.contains(disciplina));
        periodo.desalocar(disciplina);
        assertFalse(periodo.disciplinas.contains(disciplina));
    }

    @Test
    public void deveSerializarCorretamente() {
        periodo.alocar(disciplina);

        JsonNode node = Json.toJson(periodo);
        assertEquals(2, node.get("semestre").intValue());

        Iterator<JsonNode> disciplinas = node.get("disciplinas").elements();
        assertEquals("Teste", disciplinas.next().get("nome").textValue());
        assertFalse(disciplinas.hasNext());
    }
}
