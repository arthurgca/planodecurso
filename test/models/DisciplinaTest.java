package models;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

import play.libs.*;

import com.fasterxml.jackson.databind.JsonNode;

public class DisciplinaTest {

    Disciplina d1;
    Disciplina d2;

    @Before
    public void setUp() {
        d1 = new Disciplina("d1", 4, "MyString");
        d2 = new Disciplina("d2", 4, "MyString", new Disciplina[]{d1});
    }

    @Test
    public void construtor() {
        assertEquals("d1", d1.getNome());
        assertEquals(4, d1.getCreditos());
        assertEquals("MyString", d1.getCategoria());
        assertTrue(d1.getRequisitos().isEmpty());

        assertEquals("d2", d2.getNome());
        assertEquals(4, d2.getCreditos());
        assertEquals("MyString", d2.getCategoria());
        assertTrue(d2.getRequisitos().contains(d1));
    }

    @Test
    public void getRequisitosInsatisfeitos() {
        Set<Disciplina> disciplinas = new HashSet<Disciplina>();
        assertTrue(d1.getRequisitosInsatisfeitos(disciplinas).isEmpty());
        assertTrue(d2.getRequisitosInsatisfeitos(disciplinas).contains(d1));
        disciplinas.add(d1);
        assertFalse(d2.getRequisitosInsatisfeitos(disciplinas).contains(d1));
    }

}
