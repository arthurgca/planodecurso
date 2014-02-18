package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;
import org.junit.Before;
import org.junit.Test;

import play.libs.Json;
import play.test.WithApplication;

import com.fasterxml.jackson.databind.JsonNode;

public class PlanoDeCursoTest extends WithApplication {

	private PlanoDeCurso plano;

	Disciplina d1; // Programação I
	Disciplina d2; // Leitura e Prod. de Textos
	Disciplina d3; // Calculo I
	Disciplina d4; // Álgebra Vetorial
	Disciplina d5; // Int. à Computação
	Disciplina d6; // Lab. de programação I
	Disciplina d7; // Programação II

	@Before
	public void setUp() throws JDOMException, IOException {
		PlanoDeCurso.registraDisciplina(1, "Programação I", 4, 1, 4);
		PlanoDeCurso
				.registraDisciplina(2, "Leitura e Prod. de Textos", 4, 1, 2);
		PlanoDeCurso.registraDisciplina(3, "Cálculo I", 4, 1, 7);
		PlanoDeCurso.registraDisciplina(4, "Algebra Vetorial", 4, 1, 3);
		PlanoDeCurso.registraDisciplina(5, "Int. à Computação", 4, 1, 5);
		PlanoDeCurso.registraDisciplina(6, "Lab. de Programação I", 4, 1, 4);

		d1 = PlanoDeCurso.getDisciplina(1);
		d2 = PlanoDeCurso.getDisciplina(2);
		d3 = PlanoDeCurso.getDisciplina(3);
		d4 = PlanoDeCurso.getDisciplina(4);
		d5 = PlanoDeCurso.getDisciplina(5);
		d6 = PlanoDeCurso.getDisciplina(6);
		d7 = PlanoDeCurso.getDisciplina(7);

		List<Disciplina> requisitos = new ArrayList<Disciplina>();
		requisitos.add(d1);
		requisitos.add(d5);
		requisitos.add(d6);

		PlanoDeCurso.registraDisciplina(7, "Programação II", 4, 2, 5,
				requisitos);

		plano = new PlanoDeCurso();
	}

	@Test
	public void deveRetornarPeriodos() throws ErroDeAlocacaoException {
		assertEquals(15, plano.getPeriodos().size());
	}

	@Test
	public void deveRetornarPeriodo() throws ErroDeAlocacaoException {
		plano = PlanoDeCurso.getPlanoInicial();
		assertTrue(plano.getPeriodo(1).getDisciplinas().contains(d1));
	}

	@Test(expected = IllegalArgumentException.class)
	public void naoDeveRetornarPeriodoZero() throws ErroDeAlocacaoException {
		plano = PlanoDeCurso.getPlanoInicial();
		plano.getPeriodo(0);
	}

	@Test
	public void deveAlocarDisciplina() throws ErroDeAlocacaoException {
		assertFalse(plano.getPeriodo(1).getDisciplinas().contains(d1));
		plano.alocar(1, d1);
		assertTrue(plano.getPeriodo(1).getDisciplinas().contains(d1));
	}

	@Test(expected = ErroDeAlocacaoException.class)
	public void naoDeveAlocarDisciplinaRepetida()
			throws ErroDeAlocacaoException {
		plano.alocar(1, d1);
		plano.alocar(2, d1);
	}

	@Test(expected = ErroDeAlocacaoException.class)
	public void naoDeveAlocarDisciplinasEmPeriodosCom28Creditos()
			throws ErroDeAlocacaoException {
		plano.alocar(3, new Disciplina(998, "teste1", 20, 2, 200));
		plano.alocar(3, new Disciplina(999, "teste2", 20, 2, 200));
	}

	@Test(expected = ErroDeAlocacaoException.class)
	public void naoDeveAlocarDisciplinasComRequisitosNaoSatisfeitos()
			throws ErroDeAlocacaoException {
		plano.alocar(1, d1);
		plano.alocar(2, d7);
	}

	@Test
	public void deveDesalocarDisciplina() throws ErroDeAlocacaoException {
		plano.alocar(1, d1);
		assertTrue(plano.getPeriodo(1).getDisciplinas().contains(d1));
		plano.desalocar(d1);
		assertFalse(plano.getPeriodo(1).getDisciplinas().contains(d1));
	}

	@Test
	public void deveDesalocarDisciplinasDependentesAoDesalocarDisciplina()
			throws ErroDeAlocacaoException {
		plano.alocar(1, d1);
		plano.alocar(1, d5);
		plano.alocar(1, d6);
		plano.alocar(2, d7);

		assertTrue(plano.getPeriodo(2).getDisciplinas().contains(d7));
		plano.desalocar(d1);
		assertFalse(plano.getPeriodo(2).getDisciplinas().contains(d7));
	}

	@Test
	public void deveRetornarDisciplinasAlocadas()
			throws ErroDeAlocacaoException {
		assertTrue(plano.getDisciplinasAlocadas().isEmpty());

		plano.alocar(1, d1);

		assertTrue(plano.getDisciplinasAlocadas().contains(d1));
	}

	@Test
	public void deveRetornarDisciplinasNaoAlocadas()
			throws ErroDeAlocacaoException {
		assertTrue(plano.getDisciplinasNaoAlocadas().contains(d1));
		assertTrue(plano.getDisciplinasNaoAlocadas().contains(d2));
		assertTrue(plano.getDisciplinasNaoAlocadas().contains(d3));
		assertTrue(plano.getDisciplinasNaoAlocadas().contains(d4));

		plano.alocar(1, d1);

		assertFalse(plano.getDisciplinasNaoAlocadas().contains(d1));
		assertTrue(plano.getDisciplinasNaoAlocadas().contains(d2));
		assertTrue(plano.getDisciplinasNaoAlocadas().contains(d3));
		assertTrue(plano.getDisciplinasNaoAlocadas().contains(d4));
	}

	@Test
	public void deveRetornarPlanoInicial() throws ErroDeAlocacaoException {
		PlanoDeCurso plano = PlanoDeCurso.getPlanoInicial();
		List<Disciplina> p1 = plano.getPeriodo(1).getDisciplinas();
		assertTrue((p1).contains(d1));
		assertTrue((p1).contains(d2));
		assertTrue((p1).contains(d3));
		assertTrue((p1).contains(d4));
		assertTrue((p1).contains(d5));
		assertTrue((p1).contains(d6));
		assertFalse((p1).contains(d7));
		assertTrue(plano.getPeriodo(2).getDisciplinas().isEmpty());
	}

	@Test
	public void deveSerializarCorretamente() throws ErroDeAlocacaoException {
		JsonNode node = Json.toJson(PlanoDeCurso.getPlanoInicial());
		assertTrue(node.get("disciplinasAlocadas").isArray());
		assertTrue(node.get("disciplinasNaoAlocadas").isArray());
		assertTrue(node.get("periodos").isArray());
	}
}
