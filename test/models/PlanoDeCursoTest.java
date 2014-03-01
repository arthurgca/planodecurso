package models;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

import play.libs.*;

import com.fasterxml.jackson.databind.JsonNode;

public class PlanoDeCursoTest extends test.TestBase {

    PlanoDeCurso plano0;
    PlanoDeCurso plano1;
    PlanoDeCurso plano2;

    @Before
    public void setUp() throws ErroDeAlocacaoException {
        plano0 = new PlanoDeCurso();

        plano1 = new PlanoDeCurso();
        plano1.alocarDisciplina(1, disciplina("Programação I"));
        plano1.alocarDisciplina(1, disciplina("Leitura e Prod. de Textos"));
        plano1.alocarDisciplina(1, disciplina("Cálculo I"));
        plano1.alocarDisciplina(1, disciplina("Álgebra Vetorial"));
        plano1.alocarDisciplina(1, disciplina("Int. à Computação"));
        plano1.alocarDisciplina(1, disciplina("Lab. de Programação I"));

        plano2 = new PlanoDeCurso();
        plano2.alocarDisciplina(1, disciplina("Programação I"));
        plano2.alocarDisciplina(1, disciplina("Leitura e Prod. de Textos"));
        plano2.alocarDisciplina(1, disciplina("Cálculo I"));
        plano2.alocarDisciplina(1, disciplina("Álgebra Vetorial"));
        plano2.alocarDisciplina(1, disciplina("Int. à Computação"));
        plano2.alocarDisciplina(1, disciplina("Lab. de Programação I"));
        plano2.alocarDisciplina(2, disciplina("Programação II"));
        plano2.alocarDisciplina(2, disciplina("Lab. de Programação II"));
        plano2.alocarDisciplina(2, disciplina("Matemática Discreta"));
        plano2.alocarDisciplina(2, disciplina("Teoria dos Grafos"));
        plano2.alocarDisciplina(2, disciplina("Fund. de Física Clássica"));
        plano2.alocarDisciplina(2, disciplina("Cálculo II"));
    }

    @Test
    public void retornarDisciplinas() {
        assertEquals(0, plano0.getDisciplinas().size());
        assertEquals(6, plano1.getDisciplinas().size());
        assertEquals(12, plano2.getDisciplinas().size());
    }

    @Test
    public void retornarDisciplinasSemestre() {
        assertEquals(0, plano0.getDisciplinas(1).size());

        assertEquals(6, plano1.getDisciplinas(1).size());
        assertEquals(0, plano1.getDisciplinas(2).size());

        assertEquals(6, plano2.getDisciplinas(1).size());
        assertEquals(6, plano2.getDisciplinas(2).size());
        assertEquals(0, plano2.getDisciplinas(3).size());
    }

    @Test
    public void retornarTotalCreditos() {
        assertEquals(0, plano0.getTotalCreditos(1));

        assertEquals(24, plano1.getTotalCreditos(1));
        assertEquals(0, plano1.getTotalCreditos(2));

        assertEquals(24, plano2.getTotalCreditos(1));
        assertEquals(22, plano2.getTotalCreditos(2));
        assertEquals(0, plano2.getTotalCreditos(3));
    }

    @Test
    public void alocarDisciplina() throws ErroDeAlocacaoException {
        plano0.alocarDisciplina(1, disciplina("Programação I"));
        assertTrue(plano0.getDisciplinas(1).contains(disciplina("Programação I")));

        plano1.alocarDisciplina(2, disciplina("Programação II"));
        assertTrue(plano1.getDisciplinas(2).contains(disciplina("Programação II")));

        plano2.alocarDisciplina(3, disciplina("Estrutura de Dados"));
        assertTrue(plano2.getDisciplinas(3).contains(disciplina("Estrutura de Dados")));
    }

    @Test(expected = ErroDeAlocacaoException.class)
    public void alocarDisciplinaMaximoCreditos() throws ErroDeAlocacaoException {
        plano2.alocarDisciplina(2, disciplina("Gerência da Informação"));
        plano2.alocarDisciplina(2, disciplina("Metodologia Científica"));
    }

    @Test(expected = ErroDeAlocacaoException.class)
    public void alocarDisciplinaPreRequisitosInsatisfeitos() throws ErroDeAlocacaoException {
        plano2.alocarDisciplina(3, disciplina("Projeto em Computação II"));
    }

    @Test(expected = ErroDeAlocacaoException.class)
    public void alocarDisciplinaRepetida() throws ErroDeAlocacaoException {
        plano0.alocarDisciplina(1, disciplina("Programação I"));
        plano0.alocarDisciplina(2, disciplina("Programação I"));
    }

    @Test
    public void desalocarDisciplina() {
        plano1.desalocarDisciplina(disciplina("Programação I"));
        assertFalse(plano1.getDisciplinas().contains(disciplina("Programação I")));

        plano2.desalocarDisciplina(disciplina("Programação II"));
        assertFalse(plano2.getDisciplinas().contains(disciplina("Programação II")));
    }

    @Test
    public void desalocarDisciplinaRecursivamente() throws ErroDeAlocacaoException {
        PlanoDeCurso plano = new PlanoDeCurso();

        plano.alocarDisciplina(1, disciplina("Cálculo I"));
        plano.alocarDisciplina(1, disciplina("Álgebra Vetorial"));
        plano.alocarDisciplina(2, disciplina("Fund. de Física Clássica"));
        plano.alocarDisciplina(2, disciplina("Cálculo II"));
        plano.alocarDisciplina(3, disciplina("Fund. de Física Moderna"));
        plano.alocarDisciplina(3, disciplina("Gerência da Informação"));

        plano.desalocarDisciplina(disciplina("Cálculo I"));

        assertFalse(plano.getDisciplinas().contains(disciplina("Cálculo I")));
        assertFalse(plano.getDisciplinas().contains(disciplina("Fund. de Física Clássica")));
        assertFalse(plano.getDisciplinas().contains(disciplina("Cálculo II")));
        assertFalse(plano.getDisciplinas().contains(disciplina("Fund. de Física Moderna")));

        assertTrue(plano.getDisciplinas().contains(disciplina("Gerência da Informação")));
    }

    @Test
    public void serializaCorretamente() {
        JsonNode node = Json.toJson(PlanoDeCurso.getPlanoInicial());
        assertTrue(node.get("disciplinas").isArray());
        assertTrue(node.get("alocacoes").isArray());
    }
}
