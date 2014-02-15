package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PlanoDeCurso {

	private Map<Integer, Periodo> periodos;
	private CatalogoDeDisciplinas catalogo;

	public PlanoDeCurso(CatalogoDeDisciplinas catalogo) {
		this.catalogo = catalogo;
		periodos = new HashMap<Integer, Periodo>();
		inicializaMapPeriodos();
	}

	private void inicializaMapPeriodos() {
		for (int i = 0; i < 15; i++) {
			periodos.put(i, new Periodo());
		}
	}

	public void alocar(int semestre, Disciplina disciplina)
			throws ErroDeAlocacaoException {
		if (!disciplina.getDependencias().isEmpty()) {
			for (Disciplina dependencia : disciplina.getDependencias()) {
				if (!getDisciplinasAlocadas().contains(dependencia)) {
					throw new ErroDeAlocacaoException(
							"Pre requisitos da disciplina não cumpridos");
				}
			}
		}
		if ((periodos.get(semestre).getTotalCreditos() + disciplina
				.getCreditos()) > 28) {
			throw new ErroDeAlocacaoException(
					"Periodo terá mais de 28 créditos");
		}
		for (int i : periodos.keySet()) {
			if (periodos.get(i).getDisciplinas().contains(disciplina)) {
				throw new ErroDeAlocacaoException("Disciplina duplicada");
			}
		}
		periodos.get(semestre).alocar(disciplina);
	}

	public void desalocar(Disciplina disciplina) {
		for (int i : periodos.keySet()) {
			Periodo periodo = periodos.get(i);
			if (periodo.getDisciplinas().contains(disciplina)) {
				periodo.desalocar(disciplina);
				removerDependencias(disciplina);
			}
		}
	}

	private void removerDependencias(Disciplina disciplina) {
		for (int i : periodos.keySet()) {
			Periodo periodo = periodos.get(i);
			List<Disciplina> dependencias = new ArrayList<Disciplina>();
			for (Disciplina disc : periodo.getDisciplinas()) {
				if (disc.getDependencias().contains(disciplina)) {
					dependencias.add(disc);
				}
			}
			for (Disciplina dependencia : dependencias) {
				periodo.desalocar(dependencia);
			}
		}
	}

	public Periodo getPeriodo(int i) {
		return periodos.get(i);
	}

	public Set<Disciplina> getDisciplinasAlocadas() {
		Set<Disciplina> disciplinasAlocadas = new HashSet<Disciplina>();
		;
		for (int i : periodos.keySet()) {
			Periodo periodo = periodos.get(i);
			for (Disciplina disciplina : periodo.getDisciplinas()) {
				disciplinasAlocadas.add(disciplina);
			}
		}
		return disciplinasAlocadas;
	}

	public Set<Disciplina> getDisciplinasNaoAlocadas() {
		Set<Disciplina> disciplinasNaoAlocadas = new HashSet<Disciplina>();
		disciplinasNaoAlocadas.addAll(catalogo.getDisciplinas());
		disciplinasNaoAlocadas.removeAll(getDisciplinasAlocadas());
		return disciplinasNaoAlocadas;
	}

	public static PlanoDeCurso getPlanoInicial(
			CatalogoDeDisciplinas catalogoNovo) throws ErroDeAlocacaoException {
		PlanoDeCurso planoInicial = new PlanoDeCurso(catalogoNovo);
		for (Disciplina disciplina : catalogoNovo.getDisciplinas()) {
			if (disciplina.getPeriodo() == 1) {
				planoInicial.alocar(1, disciplina);
			}
		}
		return planoInicial;
	}

}
