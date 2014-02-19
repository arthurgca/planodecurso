package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Collections;

public class PlanoDeCurso {

	private static Map<Integer, Disciplina> disciplinas = new HashMap<Integer, Disciplina>();

	private Map<Integer, Periodo> periodos;

	/**
	 * Construtor de um Plano De Curso
	 */
	public PlanoDeCurso() {
		periodos = new HashMap<Integer, Periodo>();
		inicializaMapPeriodos();
	}

	private void inicializaMapPeriodos() {
		for (int i = 0; i < 15; i++) {
			periodos.put(i + 1, new Periodo(i + 1));
		}
	}

	/** Aloca disciplina no semestre desejado com o id da disciplina
	 * @param semestre
	 * @param disciplina
	 * @throws ErroDeAlocacaoException
	 */
	public void alocar(int semestre, int disciplina)
			throws ErroDeAlocacaoException {
		alocar(semestre, getDisciplina(disciplina));
	}

	/** Aloca disciplina no semestre desejado com um objeto Disciplina
	 * @param semestre
	 * @param disciplina
	 * @throws ErroDeAlocacaoException
	 */
	public void alocar(int semestre, Disciplina disciplina)
			throws ErroDeAlocacaoException {
		if (!disciplina.getDependencias().isEmpty()) {
			for (Disciplina dependencia : disciplina.getDependencias()) {
				if (!getDisciplinasAlocadas().contains(dependencia)) {
					throw new ErroDeAlocacaoException(
							"Pré-requisitos da disciplina não foram satisfeitos.");
				}
			}
		}
		if ((periodos.get(semestre).getTotalCreditos() + disciplina
				.getCreditos()) > 28) {
			throw new ErroDeAlocacaoException(
					"Período deve ter menos de 28 créditos.");
		}
		for (int i : periodos.keySet()) {
			if (periodos.get(i).getDisciplinas().contains(disciplina)) {
				throw new ErroDeAlocacaoException("Disciplina já alocada.");
			}
		}
		periodos.get(semestre).alocar(disciplina);
	}

	/** Desaloca disciplina baseado no id da mesma
	 * @param disciplina
	 */
	public void desalocar(int disciplina) {
		desalocar(getDisciplina(disciplina));
	}

	/** Desaloca disciplina recebendo como parametro a disciplina
	 * @param disciplina
	 */
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
				desalocar(dependencia);
			}
		}
	}

	/**
	 * @param semestre
	 * @return Retorna periodo baseado no numero do semestre
	 */
	public Periodo getPeriodo(int semestre) {
		if (semestre < 1) {
			throw new IllegalArgumentException("o semestre deve ser >= 1");
		}
		return periodos.get(semestre);
	}

	/** 
	 * @return Retorna Set de disciplinas alocadas
	 */
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

	/** 
	 * @return Retorna Set de disciplinas nao alocadas
	 */
	public Set<Disciplina> getDisciplinasNaoAlocadas() {
		Set<Disciplina> disciplinasNaoAlocadas = new HashSet<Disciplina>();
		disciplinasNaoAlocadas.addAll(getDisciplinas());
		disciplinasNaoAlocadas.removeAll(getDisciplinasAlocadas());
		return disciplinasNaoAlocadas;
	}

	/**
	 * @return Retorna lista de periodos
	 */
	public List<Periodo> getPeriodos() {
		return Collections.unmodifiableList(new ArrayList<Periodo>(periodos
				.values()));
	}

	/**
	 * @return Retorna PlanoDeCurso com as disciplinas do primeiro periodo alocadas
	 * @throws ErroDeAlocacaoException
	 */
	public static PlanoDeCurso getPlanoInicial() throws ErroDeAlocacaoException {
		PlanoDeCurso planoInicial = new PlanoDeCurso();
		for (Disciplina disciplina : getDisciplinas()) {
			if (disciplina.getPeriodo() == 1) {
				planoInicial.alocar(1, disciplina);
			}
		}
		return planoInicial;
	}

	/** 
	 * @param i
	 * @return Retorna disciplina
	 */
	public static Disciplina getDisciplina(int i) {
		return disciplinas.get(i);
	}

	/**
	 * @return Retorna colecao imutavel de disciplinas
	 */
	public static Collection<Disciplina> getDisciplinas() {
		return Collections.unmodifiableCollection(disciplinas.values());
	}

	/** Registra disciplina sem dependencias
	 * @param id
	 * @param nome
	 * @param creditos
	 * @param periodo
	 * @param dificuldade
	 */
	public static void registraDisciplina(int id, String nome, int creditos,
			int periodo, int dificuldade) {
		disciplinas.put(id, new Disciplina(id, nome, creditos, periodo,
				dificuldade));
	}

	/** Registra disciplina com dependencias
	 * @param id
	 * @param nome
	 * @param creditos
	 * @param periodo
	 * @param dificuldade
	 * @param dependencias
	 */
	public static void registraDisciplina(int id, String nome, int creditos,
			int periodo, int dificuldade, List<Disciplina> dependencias) {
		disciplinas.put(id, new Disciplina(id, nome, creditos, periodo,
				dificuldade, dependencias));
	}
}
