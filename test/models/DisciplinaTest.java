package models;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DisciplinaTest {

	Disciplina d1 = new Disciplina(1, "Programação I", 4, 1, 4);
	Disciplina d2 = new Disciplina(5, "Int. à Computacação", 4, 1, 2);
	Disciplina d3 = new Disciplina(6, "Lab. de Programação I", 4, 1, 4);
	Disciplina d4 = new Disciplina(7, "Programação II", 4, 2, 8, d1, d2, d3);

	@Test
	public void deveRetornarId() {
		assertEquals(1, d1.getId());
		assertEquals(7, d4.getId());
	}

	@Test
	public void deveRetornarNome() {
		assertEquals("Programação I", d1.getNome());
	}

	@Test
	public void deveRetornarCreditos() {
		assertEquals(4, d1.getCreditos());
	}

	@Test
	public void deveRetornarPeriodo() {
		assertEquals(1, d1.getPeriodo());
		assertEquals(2, d4.getPeriodo());
	}

	@Test
	public void deveRetornarDificuldade() {
		assertEquals(4, d1.getDificuldade());
		assertEquals(8, d4.getDificuldade());
	}
	
	@Test
	public void deveRetornarDependencias() {
		assertTrue(d4.getDependencias().contains(d1));
		assertTrue(d4.getDependencias().contains(d2));
		assertTrue(d4.getDependencias().contains(d3));
	}
	
	@Test
	public void deveCompararIgualdadeUsandId() {
		assertThat(d1).isEqualTo(new Disciplina(1, "Programação I", 8, 1, 4));
		assertThat(d1)
				.isNotEqualTo(new Disciplina(2, "Programação I", 4, 1, 4));
	}
}