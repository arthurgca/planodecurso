package models;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

import play.libs.*;

import com.fasterxml.jackson.databind.JsonNode;

public class GradeTest {

    Grade g1;

    Disciplina d1;
    Disciplina d2;

    @Before
    public void setUp() {
        d1 = new Disciplina("d1", 4, "MyString");
        d2 = new Disciplina("d2", 4, "MyString", new Disciplina[]{d1});

        Curriculo c1 = new Curriculo.Builder("MyString")
            .maxPeriodos(3)
            .minCreditosPeriodo(4)
            .maxCreditosPeriodo(8)
            .disciplina(d1)
            .disciplina(d2)
            .build();

        g1 = new Grade("MyString", c1);
    }

    @Test
    public void construtor(){
        assertEquals("MyString", g1.getNome());
        assertEquals(3, g1.getPeriodos().size());
    }

    @Test
    public void getMaxPeriodos() {
        assertEquals(3, g1.getMaxPeriodos());
    }

    @Test
    public void getPeriodo() {
        assertEquals(1, g1.getPeriodo(1).getSemestre());
        assertEquals(2, g1.getPeriodo(2).getSemestre());
        assertEquals(3, g1.getPeriodo(3).getSemestre());
    }

    @Test
    public void programar() {
        assertTrue(g1.getDisciplinas(1).isEmpty());
        g1.programar(d1, 1);
        assertTrue(g1.getDisciplinas(1).contains(d1));
    }

    @Test
    public void desprogramar() {
        g1.programar(d1, 1);
        assertTrue(g1.getDisciplinas(1).contains(d1));
        g1.desprogramar(d1, 1);
        assertFalse(g1.getDisciplinas(1).contains(d1));
    }

    @Test
    public void desprogramarRecursivamente() {
        Disciplina d3 = new Disciplina("d3", 4, "MyString", new Disciplina[]{d2});
        g1.programar(d1, 1);
        g1.programar(d2, 2);
        g1.programar(d3, 3);

        g1.desprogramarRecursivamente(d1, 1);

        assertFalse(g1.getDisciplinas(1).contains(d1));
        assertFalse(g1.getDisciplinas(2).contains(d2));
        assertFalse(g1.getDisciplinas(3).contains(d3));
    }

    @Test
    public void toJson() {
        g1.programar(d1, 1);
        g1.programar(d2, 2);

        JsonNode node = Json.toJson(g1);

        assertEquals("MyString", g1.getNome());

        assertTrue(node.get("periodos").isArray());

        assertEquals(3, node.get("maxPeriodos").numberValue());
    }

    @Test
    public void copiar() {
        g1.programar(d1, 2);
        g1.programar(d2, 3);

        Grade g2 = Grade.copiar("MyString2", g1);

        assertEquals("MyString2", g2.getNome());
        assertEquals(3, g2.getPeriodos().size());

        assertTrue(g2.getDisciplinas(1).isEmpty());
        assertTrue(g2.getDisciplinas(2).contains(d1));
        assertTrue(g2.getDisciplinas(3).contains(d2));
    }

}
