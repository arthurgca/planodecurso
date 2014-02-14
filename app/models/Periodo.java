package models;

import java.util.ArrayList;
import java.util.List;

import java.util.Collections;

public class Periodo {
	private List<Disciplina> disciplinas;

	public Periodo() {
		disciplinas = new ArrayList<Disciplina>();
	}

	public void alocar(Disciplina disciplina) {
		disciplinas.add(disciplina);
	}

	public void desalocar(Disciplina disciplina) {
		disciplinas.remove(disciplina);
	}

	public List<Disciplina> getDisciplinas() {
		return Collections.unmodifiableList(disciplinas);
	}

	public int getTotalCreditos() {
		int totalCreditos = 0;
		for (Disciplina disciplina : disciplinas) {
			totalCreditos += disciplina.getCreditos();
		}
		return totalCreditos;
	}

	public int getTotalDificuldade() {
		int totalDificuldade = 0;
		for (Disciplina disciplina : disciplinas) {
			totalDificuldade += disciplina.getDificuldade();
		}
		return totalDificuldade;
	}
}
