package models;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

import play.libs.*;
import com.fasterxml.jackson.databind.JsonNode;

public class PeriodoTest extends test.TestBase {

    Periodo p1;
    Periodo p2;

    Disciplina d1;
    Disciplina d2;

    @Before
    public void setUp() {
        p1 = new Periodo(2);

        d1 = new Disciplina("d1", 4, "MyString");
        d2 = new Disciplina("d2", 4, "MyString");
        p2 = new Periodo(3, new Disciplina[] {d1, d2});
    }

    @Test
    public void construtor() {
        assertEquals(2, p1.semestre);
        assertTrue(p1.disciplinas.isEmpty());

        assertEquals(3, p2.semestre);
        assertEquals(2, p2.disciplinas.size());
    }

    @Test
    public void getNome() {
        assertEquals("2º Período", p1.getNome());
        assertEquals("3º Período", p2.getNome());
    }

    @Test
    public void getTotalCreditos() {
        assertEquals(0, p1.getTotalCreditos());
        assertEquals(8, p2.getTotalCreditos());
    }

    @Test
    public void programar() {
        assertFalse(p1.disciplinas.contains(d1));
        p1.programar(d1);
        assertTrue(p1.disciplinas.contains(d1));
    }

    @Test
    public void desprogramar() {
        assertTrue(p2.disciplinas.contains(d1));
        p2.desprogramar(d1);
        assertFalse(p2.disciplinas.contains(d1));
    }

    @Test
    public void toJson() {
        JsonNode node = Json.toJson(p2);
        assertEquals(3, node.get("semestre").numberValue());

        assertEquals("3º Período", node.get("nome").textValue());

        assertEquals(8, node.get("totalCreditos").numberValue());

        assertTrue(node.get("disciplinas").isArray());
    }
}
