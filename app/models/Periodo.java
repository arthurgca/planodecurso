package models;

import java.util.ArrayList;
import java.util.List;

public class Periodo {
	private int semestre;
	private List<Disciplina> disciplinas;
	
	public Periodo() {}
	
	public Periodo(int semestre) {
		if (semestre < 1) {
			throw new IllegalArgumentException("o semestre deve ser >= 1");
		}
		
		this.semestre = semestre;
		this.disciplinas = new ArrayList<Disciplina>();
	}

	public int getSemestre() {
		return semestre;
	}

	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}
	
	public void alocar(Disciplina disciplina) {
		disciplinas.add(disciplina);
	}

	public void desalocar(Disciplina disciplina) {
		disciplinas.remove(disciplina);
	}

	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
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
