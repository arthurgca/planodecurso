package models;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

import play.libs.*;

import com.fasterxml.jackson.databind.JsonNode;

public class PlanoDeCursoTest extends test.TestBase {

    Curriculo c1;

    Grade g1;

    PlanoDeCurso p1;

    Disciplina d1;

    Disciplina d2;

    Disciplina d3;

    @Before
    public void setUp() {
        carregarTestData();

        c1 = Curriculo.find.all().get(0);
        g1 = Grade.find.all().get(0);

        d1 = c1.getDisciplina("Disciplina Introdutória I");
        d2 = c1.getDisciplina("Disciplina Introdutória II");
        d3 = c1.getDisciplina("Disciplina Avançada I");

        p1 = new PlanoDeCurso(c1, g1);
        p1.save();
    }

    @Test
    public void construtor() {
        assertNotEquals(g1, p1.grade);
    }

    @Test
    public void getPeriodos() {
        assertEquals(4, p1.getPeriodos().size());
    }

    @Test
    public void programar() throws ErroValidacaoException {
        assertFalse(p1.getDisciplinas(1).contains(d1));
        p1.programar(d1, 1);
        assertTrue(p1.getDisciplinas(1).contains(d1));
    }

    @Test(expected = ErroValidacaoException.class)
    public void programarErroMaximoCreditos() throws ErroValidacaoException {
        p1.programar(c1.getDisciplina("Disciplina Eletiva Introdutória"), 4);
    }

    @Test(expected = ErroValidacaoException.class)
    public void programarErroPreRequisitosInsatisfeitos() throws ErroValidacaoException {
        p1.programar(c1.getDisciplina("Disciplina Eletiva Avançada"), 1);
    }

    @Test
    public void desprogramar() {
        assertTrue(p1.getDisciplinas(1).contains(d2));
        p1.desprogramar(d2, 1);
        assertFalse(p1.getDisciplinas(1).contains(d2));
    }

    @Test
    public void mover() throws ErroValidacaoException {
        assertTrue(p1.getDisciplinas(2).contains(d3));
        assertFalse(p1.getDisciplinas(3).contains(d3));
        p1.mover(d3, 2, 3);
        assertFalse(p1.getDisciplinas(2).contains(d3));
        assertTrue(p1.getDisciplinas(3).contains(d3));
    }

    @Test(expected = ErroValidacaoException.class)
    public void moverErroMaximoCreditos() throws ErroValidacaoException {
        p1.mover(d2, 1, 4);
    }

    @Test
    public void moverPreRequisitosInsatisfeitos() throws ErroValidacaoException {
        p1.mover(c1.getDisciplina("Disciplina Avançada II"), 4, 1);
    }

    @Test
    public void toJson() {
        JsonNode node = Json.toJson(p1);
        assertTrue(node.get("periodos").isArray());
    }
}
