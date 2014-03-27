package models;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

import play.libs.*;

import com.fasterxml.jackson.databind.JsonNode;

public class PlanoTest {

    Disciplina d1;
    Disciplina d2;
    Disciplina d3;
    Disciplina d4;
    Disciplina d5;
    Disciplina d6;
    Disciplina d7;

    Curriculo c1;

    Grade g1;

    Plano p1;

    @Before
    public void setUp() {
        d1 = new Disciplina("Disciplina 1", 4, "MyString");
        d2 = new Disciplina("Disciplina 2", 4, "MyString");
        d3 = new Disciplina("Disciplina 3", 4, "MyString");
        d4 = new Disciplina("Disciplina 4", 4, "MyString");
        d5 = new Disciplina("Disciplina 5", 4, "MyString");
        d6 = new Disciplina("Disciplina 6", 4, "MyString", new Disciplina[]{d5});
        d7 = new Disciplina("Disciplina 7", 4, "MyString");

        c1 = new Curriculo.Builder("Curriculo 1")
            .maxPeriodos(4)
            .minCreditosPeriodo(4)
            .maxCreditosPeriodo(8)
            .disciplina(d1)
            .disciplina(d2)
            .disciplina(d3)
            .disciplina(d4)
            .disciplina(d5)
            .disciplina(d6)
            .disciplina(d7)
            .build();

        g1 = new Grade("Grade 1", c1);
        g1.programar(d1, 1);
        g1.programar(d2, 1);
        g1.programar(d3, 2);

        p1 = new Plano(c1, g1);
    }

    @Test
    public void construtor() {
        assertNotEquals(g1, p1.getGrade());
    }

    @Test
    public void getPeriodos() {
        assertEquals(4, p1.getPeriodos().size());
    }

    @Test
    public void programar() throws ErroValidacaoException {
        assertFalse(p1.getDisciplinas(2).contains(d4));
        p1.programar(d4, 2);
        assertTrue(p1.getDisciplinas(2).contains(d4));
    }

    @Test(expected = ErroValidacaoException.class)
    public void programarErroMaxCreditos() throws ErroValidacaoException {
        p1.programar(d4, 1);
    }

    @Test(expected = ErroValidacaoException.class)
    public void programarErroPreRequisitosInsatisfeitos() throws ErroValidacaoException {
        p1.programar(d6, 2);
    }

    @Test
    public void desprogramar() throws ErroValidacaoException {
        assertTrue(p1.getDisciplinas(1).contains(d2));
        p1.desprogramar(d2, 1);
        assertFalse(p1.getDisciplinas(1).contains(d2));
    }

    @Test(expected = ErroValidacaoException.class)
    public void desprogramarErroMinCreditos() throws ErroValidacaoException {
        p1.desprogramar(d3, 2);
    }

    @Test
    public void mover() throws ErroValidacaoException {
        assertTrue(p1.getDisciplinas(1).contains(d2));
        assertFalse(p1.getDisciplinas(2).contains(d2));
        p1.mover(d2, 1, 2);
        assertFalse(p1.getDisciplinas(1).contains(d2));
        assertTrue(p1.getDisciplinas(2).contains(d2));
    }

    @Test(expected = ErroValidacaoException.class)
    public void moverErroMinCreditos() throws ErroValidacaoException {
        p1.mover(d3, 2, 3);
    }

    @Test(expected = ErroValidacaoException.class)
    public void moverErroMaxCreditos() throws ErroValidacaoException {
        p1.mover(d3, 2, 1);
    }

    @Test
    public void moverPreRequisitosInsatisfeitos() throws ErroValidacaoException {
        p1.programar(d5, 3);
        p1.programar(d6, 4);
        p1.programar(d7, 4);
        p1.mover(d6, 4, 2);
    }

}
