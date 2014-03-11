package models;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

import play.libs.*;
import com.fasterxml.jackson.databind.JsonNode;

public class PeriodoTest extends test.TestBase {

    Periodo p1;
    Periodo p2;

    @Before
    public void setUp() throws ErroDeAlocacaoException {
        p1 = new Periodo(1);
        p1.alocarDisciplina(disciplina("Programação I"));
        p1.alocarDisciplina(disciplina("Leitura e Prod. de Textos"));
        p1.alocarDisciplina(disciplina("Cálculo I"));
        p1.alocarDisciplina(disciplina("Álgebra Vetorial"));
        p1.alocarDisciplina(disciplina("Int. à Computação"));
        p1.alocarDisciplina(disciplina("Lab. de Programação I"));

        p2 = new Periodo(2);
        p2.alocarDisciplina(disciplina("Programação II"));
        p2.alocarDisciplina(disciplina("Lab. de Programação II"));
        p2.alocarDisciplina(disciplina("Matemática Discreta"));
        p2.alocarDisciplina(disciplina("Teoria dos Grafos"));
        p2.alocarDisciplina(disciplina("Fund. de Física Clássica"));
        p2.alocarDisciplina(disciplina("Cálculo II"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void semestreInvalido() {
        new Periodo(0);
    }

    @Test
    public void retornarTotalCreditos() {
        assertEquals(0, new Periodo(1).getTotalCreditos());
        assertEquals(24, p1.getTotalCreditos());
        assertEquals(22, p2.getTotalCreditos());
    }

    @Test
    public void alocarDisciplina() throws ErroDeAlocacaoException {
        p2.alocarDisciplina(disciplina("Futsal"));
        assertTrue(p2.disciplinas.contains(disciplina("Futsal")));
    }

    @Test(expected = ErroDeAlocacaoException.class)
    public void alocarDisciplinaErro() throws ErroDeAlocacaoException {
        p1.alocarDisciplina(disciplina("Futsal"));
        p1.alocarDisciplina(disciplina("Teoria dos Grafos"));
        p1.alocarDisciplina(disciplina("Gerência da Informação"));
    }

    @Test
    public void desalocarDisciplina() throws ErroDeAlocacaoException {
        p2.desalocarDisciplina(disciplina("Programação II"));
        p2.desalocarDisciplina(disciplina("Lab. de Programação II"));
        assertFalse(p2.disciplinas.contains(disciplina("Programação II")));
        assertFalse(p2.disciplinas.contains(disciplina("Lab. de Programação II")));
    }

    @Test
    public void serializarCorretamente() {
        JsonNode node = Json.toJson(p2);
        assertEquals(2, node.get("semestre").numberValue());
        assertTrue(node.get("disciplinas").isArray());
        assertEquals(22, node.get("totalCreditos").numberValue());
    }
}
