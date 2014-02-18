package models;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

import org.jdom2.JDOMException;
import org.junit.Before;
import org.junit.Test;

public class CatalogoDeDisciplinasTest {

	private CatalogoDeDisciplinas catalogo;

	@Before
	public void setUp() throws JDOMException, IOException {
		FileReader disciplinasXML = new FileReader(
				"test/support/disciplinas_testing.xml");
		catalogo = new CatalogoDeDisciplinas(disciplinasXML);
	}

	@Test
	public void deveImportarDisciplinasXML() {
		assertEquals(catalogo.get(6), new Disciplina(6,
				"Lab. de Programação I", 4, 1, 4));
		assertEquals(catalogo.get(7), new Disciplina(7, "Programação II", 4, 2,
				8, new Disciplina(1, "Programação I", 4, 1, 4), new Disciplina(
						5, "Int. à Computacação", 4, 1, 5), new Disciplina(6,
						"Lab. de Programação I", 4, 1, 4)));
		assertEquals(true, catalogo.get(1).getDependencias().isEmpty());
		assertEquals(false, catalogo.get(7).getDependencias().isEmpty());

	}

	@Test
	public void deveRetornarDisciplinaPorId() {
		assertThat(catalogo.get(1)).isInstanceOf(Disciplina.class);
		assertThat(catalogo.get(7)).isInstanceOf(Disciplina.class);
	}

	@Test(expected = IllegalArgumentException.class)
	public void deveLancarExcecaoQuandoIdDaDisciplinaNaoExiste() {
		catalogo.get(9999);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void deveRetornarDisciplinasComoListaImutavel() {
		Collection<Disciplina> disciplinas = catalogo.getDisciplinas();
		disciplinas.add(new Disciplina(2, "Teste", 4, 4, 4));
	}
}
