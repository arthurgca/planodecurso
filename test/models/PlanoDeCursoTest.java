package models;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.jdom2.JDOMException;
import org.junit.Before;
import org.junit.Test;

public class PlanoDeCursoTest {

	private PlanoDeCurso plano;

	Disciplina d1; // Programação I
	Disciplina d2; // Leitura e Prod. de Textos
	Disciplina d3; // Calculo I
	Disciplina d4; // Álgebra Vetorial
	Disciplina d5; // Int. à Computação 
	Disciplina d6; // Lab. de programação I
	Disciplina d7; // Programação II

	private CatalogoDeDisciplinas catalogo;
	
	@Before
	public void setUp() throws JDOMException, IOException {
		FileReader disciplinasXML = new FileReader("test/support/disciplinas_testing.xml");
		catalogo = new CatalogoDeDisciplinas(disciplinasXML);
		
		d1 = catalogo.get(1);
		d2 = catalogo.get(2);
		d3 = catalogo.get(3);
		d4 = catalogo.get(4);
		d5 = catalogo.get(5);
		d6 = catalogo.get(6);
		d7 = catalogo.get(7);
		
		plano = new PlanoDeCurso(catalogo);
	}

	@Test
	public void deveRetornarPeriodos() throws ErroDeAlocacaoException {
		assertEquals(15, plano.getPeriodos().size());
	}
	
	@Test
	public void deveRetornarPeriodo() throws ErroDeAlocacaoException {
		plano = PlanoDeCurso.getPlanoInicial(catalogo);
		assertTrue(plano.getPeriodo(1).getDisciplinas().contains(d1));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void naoDeveRetornarPeriodoZero() throws ErroDeAlocacaoException {
		plano = PlanoDeCurso.getPlanoInicial(catalogo);
		plano.getPeriodo(0);
	}	
	
	@Test
	public void deveAlocarDisciplina() throws ErroDeAlocacaoException {
		assertFalse(plano.getPeriodo(1).getDisciplinas().contains(d1));
		plano.alocar(1, d1);
		assertTrue(plano.getPeriodo(1).getDisciplinas().contains(d1));
	}
	
	@Test(expected = ErroDeAlocacaoException.class)
	public void naoDeveAlocarDisciplinaRepetida() throws ErroDeAlocacaoException {
		plano.alocar(1, d1);
		plano.alocar(2, d1);
	}

	@Test(expected = ErroDeAlocacaoException.class)
	public void naoDeveAlocarDisciplinasEmPeriodosCom28Creditos() throws ErroDeAlocacaoException {
		plano.alocar(3, new Disciplina(998, "teste1", 20, 2, 200));
		plano.alocar(3, new Disciplina(999, "teste2", 20, 2, 200));
	}
	
	@Test(expected = ErroDeAlocacaoException.class)
	public void naoDeveAlocarDisciplinasComRequisitosNaoSatisfeitos() throws ErroDeAlocacaoException {
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
	public void deveDesalocarDisciplinasDependentesAoDesalocarDisciplina() throws ErroDeAlocacaoException {
		plano.alocar(1, d1);
		plano.alocar(1, d5);
		plano.alocar(1, d6);
		plano.alocar(2, d7);
		
		assertTrue(plano.getPeriodo(2).getDisciplinas().contains(d7));
		plano.desalocar(d1);
		assertFalse(plano.getPeriodo(2).getDisciplinas().contains(d7));
	}
	
	@Test
	public void deveRetornarDisciplinasAlocadas() throws ErroDeAlocacaoException {
		assertTrue(plano.getDisciplinasAlocadas().isEmpty());
		
		plano.alocar(1, d1);
		
		assertTrue(plano.getDisciplinasAlocadas().contains(d1));
	}
	
	@Test
	public void deveRetornarDisciplinasNaoAlocadas() throws ErroDeAlocacaoException {
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
		PlanoDeCurso plano = PlanoDeCurso.getPlanoInicial(catalogo);
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

}
