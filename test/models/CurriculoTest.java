package models;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

import play.libs.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

import com.fasterxml.jackson.databind.JsonNode;

public class CurriculoTest {

    Curriculo c1;

    Disciplina d1;
    Disciplina d2;

    @Before
    public void setUp() {
        d1 = new Disciplina("d1", 4, "MyString1");

        d2 = new Disciplina("d2", 4, "MyString2");

        c1 = new Curriculo.Builder("MyString")
            .maxPeriodos(3)
            .minCreditosPeriodo(4)
            .maxCreditosPeriodo(8)
            .disciplina(d1)
            .disciplina(d2)
            .build();
    }

    @Test
    public void construtor() {
        assertEquals("MyString", c1.nome);
        assertEquals(3, c1.maxPeriodos);
        assertEquals(8, c1.maxCreditosPeriodo);
        assertEquals(2, c1.disciplinas.size());
    }

    @Test
    public void getDisciplinasString() {
        assertEquals(d1, c1.getDisciplina("d1"));
        assertEquals(d2, c1.getDisciplina("d2"));
        assertNull(c1.getDisciplina("d9"));
    }
}
