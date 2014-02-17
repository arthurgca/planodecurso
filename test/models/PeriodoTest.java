package models;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PeriodoTest {

	private Periodo periodo;
	private Disciplina disciplina = new Disciplina(1, "Teste", 4, 1, 4);

	@Before
	public void setUp() {
		periodo = new Periodo(2);
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
		assertTrue(periodo.getDisciplinas().contains(new Disciplina(1, "Teste", 4, 1, 4)));
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
	
	@Test(expected=UnsupportedOperationException.class)
	public void deveRetornarDisciplinasComoListaImutavel() {
		periodo.alocar(disciplina);
		List<Disciplina> disciplinas = periodo.getDisciplinas();
		disciplinas.add(new Disciplina(2, "Teste2", 4, 4, 4));
	}
	
	@Test
	public void deveRetornarTotalCreditos() {
		assertEquals(periodo.getTotalCreditos(), 0);
		periodo.alocar(disciplina);
		assertEquals(periodo.getTotalCreditos(), 4);
	}
	
	@Test
	public void deveRetornarDificuldadeSemestre() {
		periodo.alocar(disciplina);
		assertEquals(periodo.getTotalDificuldade(), 4);
	}

}
