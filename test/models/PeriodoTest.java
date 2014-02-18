package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import play.libs.Json;

import com.fasterxml.jackson.databind.JsonNode;

public class PeriodoTest {

	private Periodo periodo;
	private Disciplina disciplina;

	@Before
	public void setUp() {
		periodo = new Periodo(2);
		disciplina = new Disciplina(1, "Teste", 4, 1, 4);
	}

	@Test(expected = IllegalArgumentException.class)
	public void naoDevePermitirSemestreZero() {
		new Periodo(0);
	}

	@Test
	public void deveRetornarSemestre() {
		assertEquals(2, periodo.getSemestre());
	}

	@Test
	public void deveAlocarDisciplina() {
		periodo.alocar(disciplina);
		assertTrue(periodo.getDisciplinas().contains(
				new Disciplina(1, "Teste", 4, 1, 4)));
	}

	@Test
	public void deveDesalocarDisciplina() {
		periodo.alocar(disciplina);
		assertTrue(periodo.getDisciplinas().contains(disciplina));
		periodo.desalocar(disciplina);
		assertFalse(periodo.getDisciplinas().contains(disciplina));
	}

	@Test
	public void deveRetornarDisciplinas() {
		assertTrue(periodo.getDisciplinas().isEmpty());
		periodo.alocar(disciplina);
		assertTrue(periodo.getDisciplinas().contains(disciplina));
	}

	@Test
	public void deveRetornarTotalCreditos() {
		assertEquals(periodo.getTotalCreditos(), 0);
		periodo.alocar(disciplina);
		assertEquals(periodo.getTotalCreditos(), 4);
	}

	@Test
	public void deveRetornarDificuldade() {
		periodo.alocar(disciplina);
		assertEquals(periodo.getTotalDificuldade(), 4);
	}

	@Test
	public void deveSerializarCorretamente() {
		periodo.alocar(disciplina);

		JsonNode node = Json.toJson(periodo);
		assertEquals(2, node.get("semestre").intValue());
		assertEquals(4, node.get("totalCreditos").intValue());
		assertEquals(4, node.get("totalDificuldade").intValue());

		Iterator<JsonNode> disciplinas = node.get("disciplinas").elements();
		assertEquals("Teste", disciplinas.next().get("nome").textValue());
		assertFalse(disciplinas.hasNext());
	}
}
